package com.wernen.spotifyclone.ui.viewModel

import androidx.lifecycle.ViewModel
import com.wernen.spotifyclone.data.entities.Song
import com.wernen.spotifyclone.exoplayer.MusicServiceConnection
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteSongsViewModel @Inject constructor( musicServiceConnection: MusicServiceConnection) : ViewModel() {

    var favoriteSongs = arrayListOf<Song>()

    fun listSongs(list: Song) {
        favoriteSongs.add (list)
    }

}