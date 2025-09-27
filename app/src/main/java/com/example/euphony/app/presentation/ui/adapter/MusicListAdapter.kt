package com.example.euphony.app.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.euphony.R
import com.example.euphony.app.presentation.model.MusicItem
import com.example.euphony.databinding.ViewMusicItemBinding

class MusicListAdapter(
    private val onItemClick: (MusicItem) -> Unit
) : ListAdapter<MusicItem, MusicListAdapter.MusicViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val binding = ViewMusicItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MusicViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class MusicViewHolder(
        private val binding: ViewMusicItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = getItem(position)
                    onItemClick(item)
                }
            }
        }

        fun bind(item: MusicItem) {
            binding.apply {
                tvSongTitle.text = item.musicName
                tvArtistTitle.text = item.artistName

                val textColor = if (item.isPlaying) {
                    ContextCompat.getColor(binding.root.context,R.color.green_500)
                } else {
                    ContextCompat.getColor(binding.root.context,R.color.white)
                }
                tvSongTitle.setTextColor(textColor)

                lottieAnimationView.isVisible = item.isPlaying


                Glide.with(itemView.context)
                    .load(item.artwork30Url)
                    .placeholder(R.drawable.bg_music_placeholder)
                    .error(R.drawable.bg_music_placeholder)
                    .fallback(R.drawable.bg_music_placeholder)
                    .into(ivSong)

            }
        }

    }

    companion object DiffCallback : DiffUtil.ItemCallback<MusicItem>() {
        override fun areItemsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        override fun areContentsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
            return oldItem == newItem
        }
    }
}