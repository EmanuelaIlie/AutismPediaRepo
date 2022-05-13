package com.example.autismpedia.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.autismpedia.databinding.ListItemGameBinding
import com.example.autismpedia.models.Game

class GameAdapter(val clickListener: GameListener) :
    ListAdapter<Game, GameAdapter.ViewHolder>(GameDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemGameBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Game, clickListener: GameListener) {
            binding.game = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemGameBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class GameDiffCallback : DiffUtil.ItemCallback<Game>() {

    override fun areItemsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Game, newItem: Game): Boolean {
        return oldItem == newItem
    }
}

class GameListener(val clickListener: (gameId: String) -> Unit) {
    fun onClick(game: Game) = clickListener(game.id.toString())
}