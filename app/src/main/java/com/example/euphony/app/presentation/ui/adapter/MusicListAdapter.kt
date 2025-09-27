package com.example.euphony.app.presentation.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
//                tvDuration.text = formatDuration(item.duration)

                // Load album art using Glide or Picasso

                Glide.with(itemView.context)
                    .load(item.artwork30Url)
//                    .placeholder(R.drawable.ic_music_note)
//                    .error(R.drawable.ic_music_note)
                    .into(ivSong)

            }
        }

        private fun formatDuration(duration: Long): String {
            val minutes = (duration / 1000) / 60
            val seconds = (duration / 1000) % 60
            return String.format("%d:%02d", minutes, seconds)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<MusicItem>() {
        override fun areItemsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
            return oldItem.musicId == newItem.musicId
        }

        override fun areContentsTheSame(oldItem: MusicItem, newItem: MusicItem): Boolean {
            return oldItem == newItem
        }
    }
}