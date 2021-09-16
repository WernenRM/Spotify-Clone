package com.wernen.spotifyclone.ui

import androidx.appcompat.app.AppCompatActivity
import com.wernen.spotifyclone.R
import com.wernen.spotifyclone.adapters.SwipeSongAdapter
import com.wernen.spotifyclone.data.entities.Song
import com.wernen.spotifyclone.databinding.ActivityMainBinding
import com.wernen.spotifyclone.exoplayer.toSong
import com.wernen.spotifyclone.others.Status
import com.wernen.spotifyclone.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.view.Window
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar
import com.wernen.spotifyclone.adapters.SwipeSongAdapter2
import com.wernen.spotifyclone.exoplayer.isPlaying
import com.wernen.spotifyclone.others.LoadingDialog
import javax.inject.Inject
import kotlin.properties.Delegates

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel

//    @Inject
//    lateinit var swipeSongAdapter: SwipeSongAdapter

    @Inject
    lateinit var swipeSongAdapter: SwipeSongAdapter2

    @Inject
    lateinit var glide: RequestManager

    private var curPlayingSong: Song? = null

    private var playbackState: PlaybackStateCompat? = null

    private var controller: NavController? = null


    val listener = NavController.OnDestinationChangedListener { controller, destination, arguments ->
        when (destination.id){

            R.id.songFragment -> hideBottomBar()
            R.id.homeFragment -> showBottomBar()
            else -> showBottomBar()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        subscribeToObservers()

        binding.vpSong.adapter = swipeSongAdapter

        binding.vpSong.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if(playbackState?.isPlaying == true) {
                    mainViewModel.playOrToggleSong(swipeSongAdapter.data[position])
                } else {
                    curPlayingSong = swipeSongAdapter.data[position]
                }
            }
        })


        binding.ivPlayPause.setOnClickListener {
            curPlayingSong?.let {
                mainViewModel.playOrToggleSong(it, true)
            }
        }



//        binding.flFragmentContainer.findNavController().addOnDestinationChangedListener { _, destination, _ ->
//            when(destination.id) {
//                R.id.songFragment -> hideBottomBar()
//                R.id.homeFragment -> showBottomBar()
//                else -> showBottomBar()
//            }
//        }
    }

    private fun hideBottomBar() {
        binding.ivCurSongImage.isVisible = false
        binding.vpSong.isVisible = false
        binding.ivPlayPause.isVisible = false
    }

    private fun showBottomBar() {
        binding.ivCurSongImage.isVisible = true
        binding.vpSong.isVisible = true
        binding.ivPlayPause.isVisible = true
    }

    private fun switchViewPagerToCurrentSong(song: Song) {
        val newItemIndex = swipeSongAdapter.data.indexOf(song)

        if (newItemIndex != -1) {
            binding.vpSong.currentItem = newItemIndex
            curPlayingSong = song
        }
    }

    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(this) {
            it?.let { result ->
                when (result.status) {
                    Status.SUCCESS -> {
                        result.data?.let { songs ->
//                            swipeSongAdapter.song = songs

                            swipeSongAdapter.addall(songs.toCollection(ArrayList()))
                            if (songs.isNotEmpty()) {
                                glide.load((curPlayingSong ?: songs[0]).imageUrl)
                                    .into(binding.ivCurSongImage)
                            }
                            switchViewPagerToCurrentSong(curPlayingSong ?: return@observe)
                        }
                    }
                    Status.ERROR -> Unit
                    Status.LOADING -> Unit
                }
            }
        }
        mainViewModel.curPlayingSong.observe(this) {
            if (it == null) return@observe

            curPlayingSong = it.toSong()
            glide.load(curPlayingSong?.imageUrl).into(binding.ivCurSongImage)
            switchViewPagerToCurrentSong(curPlayingSong ?: return@observe)
        }
        mainViewModel.playbackState.observe(this) {
            playbackState = it
            binding.ivPlayPause.setImageResource(
                if (playbackState?.isPlaying == true) R.drawable.ic_pause else R.drawable.ic_play
            )
        }
        mainViewModel.isConnected.observe(this) {
            it?.getContentINotHandled()?.let { result ->
                when (result.status) {
                    Status.ERROR -> Snackbar.make(
                        binding.rootLayout,
                        result.message ?: "An unknown error occured",
                        Snackbar.LENGTH_LONG
                    ).show()
                    else -> Unit
                }
            }
        }
        mainViewModel.networkError.observe(this) {
            it?.getContentINotHandled()?.let { result ->
                when (result.status) {
                    Status.ERROR -> Snackbar.make(
                        binding.rootLayout,
                        result.message ?: "An unknown error occured",
                        Snackbar.LENGTH_LONG
                    ).show()
                    else -> Unit
                }
            }
        }
    }
}
