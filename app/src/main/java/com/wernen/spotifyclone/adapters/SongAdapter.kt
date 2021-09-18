package com.wernen.spotifyclone.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wernen.spotifyclone.data.entities.Song
import com.wernen.spotifyclone.databinding.ListItemBinding

class SongAdapter(var data: ArrayList<Song> = arrayListOf()) :
    RecyclerView.Adapter<SongAdapter.SongViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {

        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SongViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {

        val song = data[position]
        holder.bind(song)

        holder.itemView.setOnClickListener {
            onItemClickListener?.invoke(song)
        }
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    fun clearAll(data: ArrayList<Song>) {
        this.data.clear()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addall(data: ArrayList<Song>) {

        clearAll(data)
        this.data.addAll(data)

        notifyDataSetChanged()
    }

    var onItemClickListener: ((Song) -> Unit)? = null

    fun setItemClickListener(listener: (Song) -> Unit) {
        onItemClickListener = listener
    }

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ListItemBinding.bind(itemView)

        fun bind(song: Song) {

            binding.tvPrimary.text = song.title
            binding.tvSecondary.text = song.subtitle
            Glide.with(itemView.context)
                .load(song.imageUrl).into(binding.ivItemImage)

        }
    }
}