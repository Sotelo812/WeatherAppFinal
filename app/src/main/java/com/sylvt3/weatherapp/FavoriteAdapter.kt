package com.sylvt3.weatherapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavoriteAdapter(
    private var favorites: List<FavoriteWeather>,
    private val onDeleteClick: (FavoriteWeather) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val city: TextView = itemView.findViewById(R.id.itemCity)
        val description: TextView = itemView.findViewById(R.id.itemDescription)
        val temperature: TextView = itemView.findViewById(R.id.itemTemperature)
        val deleteButton: Button = itemView.findViewById(R.id.btnDeleteFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_item, parent, false)

        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favorites[position]

        holder.city.text = favorite.city
        holder.description.text = favorite.description
        holder.temperature.text = favorite.temperature

        holder.deleteButton.setOnClickListener {
            onDeleteClick(favorite)
        }
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    fun updateFavorites(newFavorites: List<FavoriteWeather>) {
        favorites = newFavorites
        notifyDataSetChanged()
    }
}