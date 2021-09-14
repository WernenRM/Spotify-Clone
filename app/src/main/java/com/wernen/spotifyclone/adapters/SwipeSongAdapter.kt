package com.wernen.spotifyclone.adapters

import androidx.recyclerview.widget.AsyncListDiffer
import com.wernen.spotifyclone.R
import com.wernen.spotifyclone.databinding.SwipeItemBinding

class SwipeSongAdapter : BaseSongAdapter(R.layout.swipe_item) {

    override val differ = AsyncListDiffer(this, diffCallback)

    private var _binding: SwipeItemBinding? = null
    private val binding: SwipeItemBinding get() = _binding!!

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.itemView.apply {
            val text = "${song.title} - ${song.subtitle}"
            binding.tvPrimary.text = text

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }

}