
package com.wernen.spotifyclone.ui.fragments
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.wernen.spotifyclone.R
import com.wernen.spotifyclone.adapters.SongAdapter2
import com.wernen.spotifyclone.adapters.SwipeSongAdapter
import com.wernen.spotifyclone.databinding.FragmentHomeBinding
import com.wernen.spotifyclone.others.Status
import com.wernen.spotifyclone.ui.viewModel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

//    private var _binding: FragmentHomeBinding? = null
//    private val binding: FragmentHomeBinding get() = _binding!!

//    private val binding: FragmentHomeBinding by lazy {
//
//        FragmentHomeBinding.inflate(layoutInflater)
//    }

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var songAdapter: SongAdapter2

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        binding.rvAllSongs.adapter = songAdapter
        setupRecyclerView()
        subscribeToObservers()

        songAdapter.setItemClickListener {
            mainViewModel.playOrToggleSong(it)
            findNavController().navigate(R.id.action_homeFragment_to_songFragment)
        }
    }

    private fun setupRecyclerView() = binding.rvAllSongs.apply {
        adapter = songAdapter
    }

    private fun subscribeToObservers() {
        Log.d("debug 2", "teste")
        mainViewModel.mediaItems.observe(viewLifecycleOwner) { result ->
            Log.d("debug 1", "$result")
            when(result.status) {

                Status.SUCCESS -> {
                    binding.allSongsProgressBar.isVisible = false
                    result.data?.let { songs ->
                        songAdapter.addall(songs.toCollection(ArrayList()))
                        Log.d("debug 1", "$songs")
                    }
                }
                Status.ERROR -> Unit
                Status.LOADING -> binding.allSongsProgressBar.isVisible = true
            }
        }
    }
}
