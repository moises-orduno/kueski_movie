package com.example.mazeapi.ui.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mazeapi.R
import com.example.mazeapi.databinding.FragmentMoviesBinding
import com.example.mazeapi.network.models.MovieResult
import com.squareup.picasso.Picasso

class MovieViewAdapter(
    private val values: MutableList<MovieResult>,
    private val itemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MovieViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentMoviesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]

        // Set movie information
        holder.showNameView.text = item.title
        holder.airingView.text = item.releaseDate
        holder.genresView.text = item.genreIds.joinToString()
        holder.overviewView.text = item.overview
        holder.popularityView.text = item.popularity.toString()
        holder.languageView.text = item.originalLanguage
        holder.voteAverageView.text = item.voteAverage.toString()
        holder.statusView.text = if (item.adult == true) "Adult" else "Non-Adult"

        // Load poster image using Picasso
        val posterUrl = POSTER_URL + item.posterPath
        Picasso.get()
            .load(posterUrl)
            .placeholder(R.drawable.ic_error_image_generic)
            .error(R.drawable.ic_error_image_generic)
            .into(holder.posterImageView)

        // Set the item click listener
        holder.buttonSaveFavorite.setOnClickListener {
            itemClickListener.onSaveMovie(values[position])
        }
    }

    fun addMovies(newMovies: List<MovieResult>) {
        val startPosition = values.size
        values.addAll(newMovies)
        notifyItemRangeInserted(startPosition, newMovies.size)
    }

    fun cleaData() {
        values.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentMoviesBinding) : RecyclerView.ViewHolder(binding.root) {
        val showNameView: TextView = binding.itemShowName
        val airingView: TextView = binding.itemReleaseDate
        val posterImageView: ImageView = binding.itemPoster
        val genresView: TextView = binding.itemGenres
        val overviewView: TextView = binding.itemOverview
        val popularityView: TextView = binding.itemPopularity
        val languageView: TextView = binding.itemLanguages
        val voteAverageView: TextView = binding.itemVoteAverage
        val statusView: TextView = binding.itemStatus
        val buttonSaveFavorite: Button = binding.buttonSaveFavorite

        override fun toString(): String {
            return super.toString() + " '" + airingView.text + "'"
        }
    }

    interface OnItemClickListener {
        fun onSaveMovie(itemId: MovieResult?)
    }

    companion object {
        const val POSTER_URL = "https://image.tmdb.org/t/p/w500"
    }
}