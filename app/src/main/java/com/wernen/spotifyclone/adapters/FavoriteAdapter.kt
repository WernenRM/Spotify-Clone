package com.wernen.spotifyclone.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wernen.spotifyclone.data.entities.Song
import com.wernen.spotifyclone.databinding.ItemListFavoriteBinding
import com.wernen.spotifyclone.databinding.ListItemBinding

class FavoriteAdapter(var data: ArrayList<Song> = arrayListOf()) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {

        val binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FavoriteViewHolder(binding.root)
    }



    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {

        val song = data[position]
        holder.bind(song)

        holder.itemView.setOnClickListener{
            onItemClickListener?.invoke(song)
        }
    }



    override fun getItemCount(): Int {
        return data.count()
    }

    fun clearAll(data: ArrayList<Song>){
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

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        //        private val glide: RequestManager
        val binding = ItemListFavoriteBinding.bind(itemView)

        fun bind(song: Song) {

            binding.tvPrimary.text =  song.title
            binding.tvSecondary.text = song.subtitle
//            glide!!.load(song.imageUrl).into(binding.ivItemImage)
            Glide.with(itemView.context)
                .load(song.imageUrl).into(binding.ivItemImage)

        }
    }
}