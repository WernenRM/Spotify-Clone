package com.wernen.spotifyclone.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.wernen.spotifyclone.R
import com.wernen.spotifyclone.adapters.FavoriteAdapter
import com.wernen.spotifyclone.adapters.SongAdapter
import com.wernen.spotifyclone.adapters.SwipeSongAdapter
import com.wernen.spotifyclone.exoplayer.MusicServiceConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMusicServiceConnection(
        @ApplicationContext context: Context
    ) = MusicServiceConnection(context)

    @Singleton
    @Provides
    fun provideSwipeSongAdapter2() = SwipeSongAdapter()

    @Singleton
    @Provides
    fun provideFavoriteAdapter() = FavoriteAdapter()

    @Singleton
    @Provides
    fun provideSongAdapter2() = SongAdapter()

    @Singleton
    @Provides
    fun provideGlideInstance(
        @ApplicationContext context: Context
    ) = Glide.with(context).setDefaultRequestOptions(
        RequestOptions()
            .placeholder(R.drawable.ic_baseline_image)
            .error(R.drawable.ic_baseline_image)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
    )
}