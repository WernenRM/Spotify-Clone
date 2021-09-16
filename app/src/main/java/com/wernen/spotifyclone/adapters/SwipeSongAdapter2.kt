package com.wernen.spotifyclone.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wernen.spotifyclone.data.entities.Song
import com.wernen.spotifyclone.databinding.SwipeItemBinding

class SwipeSongAdapter2( val data: ArrayList<Song> = arrayListOf()) :
    RecyclerView.Adapter<SwipeSongAdapter2.SwipeSongViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SwipeSongViewHolder {

        val binding = SwipeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return SwipeSongViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: SwipeSongViewHolder, position: Int) {

        val song = data[position]
        holder.bind(song)

        holder.itemView.setOnClickListener{
            onItemClickListener?.invoke(song)
        }
    }

    override fun getItemCount(): Int {
        return data.count()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addall(data: ArrayList<Song>) {

        this.data.addAll(data)

        notifyDataSetChanged()
    }
    var onItemClickListener: ((Song) -> Unit)? = null

    fun setItemClickListener(listener: (Song) -> Unit) {
        onItemClickListener = listener
    }

    class SwipeSongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = SwipeItemBinding.bind(itemView)

        fun bind(song: Song) {

            val text = "${song.title} - ${song.subtitle}"
            binding.tvPrimary.text = text

        }
    }
}