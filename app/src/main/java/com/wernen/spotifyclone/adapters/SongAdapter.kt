package com.wernen.spotifyclone.adapters


import androidx.recyclerview.widget.AsyncListDiffer
import com.bumptech.glide.RequestManager
import com.wernen.spotifyclone.R
import com.wernen.spotifyclone.databinding.ListItemBinding
import javax.inject.Inject

class SongAdapter @Inject constructor(
    private val glide: RequestManager
) : BaseSongAdapter(R.layout.list_item) {

    private var _binding: ListItemBinding? = null
    private val binding: ListItemBinding get() = _binding!!
    override val differ = AsyncListDiffer(this, diffCallback)

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs[position]
        holder.itemView.apply {
            binding.tvPrimary.text = song.title
            binding.tvSecondary.text = song.subtitle
            glide.load(song.imageUrl).into(binding.ivItemImage)

            setOnClickListener {
                onItemClickListener?.let { click ->
                    click(song)
                }
            }
        }
    }

}