package com.wernen.spotifyclone.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.wernen.spotifyclone.R
import com.wernen.spotifyclone.adapters.FavoriteAdapter
import com.wernen.spotifyclone.adapters.SongAdapter
import com.wernen.spotifyclone.data.entities.Song
import com.wernen.spotifyclone.databinding.FragmentFavoriteBinding
import com.wernen.spotifyclone.databinding.FragmentSongBinding
import com.wernen.spotifyclone.ui.viewModel.FavoriteSongsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private val binding: FragmentFavoriteBinding by lazy {

        FragmentFavoriteBinding.inflate(layoutInflater)
    }

    private lateinit var favoriteSongsViewModel: FavoriteSongsViewModel

    @Inject
    lateinit var songAdapter: FavoriteAdapter

    private val listFavorite = arrayListOf<Song>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteSongsViewModel = ViewModelProvider(requireActivity()).get(FavoriteSongsViewModel::class.java)

        binding.rvAllSongs.adapter = FavoriteAdapter(favoriteSongsViewModel.favoriteSongs)

    }
}