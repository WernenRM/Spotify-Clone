package com.wernen.spotifyclone.data.entities

import com.google.firebase.firestore.FirebaseFirestore
import com.wernen.spotifyclone.Constants.SONG_COLLECTION

class MusicDatabase {

    private val firestore = FirebaseFirestore.getInstance()
    private val songCollection = firestore.collection(SONG_COLLECTION)
}