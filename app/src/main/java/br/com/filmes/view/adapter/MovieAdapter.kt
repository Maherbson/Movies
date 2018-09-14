package br.com.filmes.view.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.filmes.view.holder.MovieViewHolder
import br.com.filmes.R
import br.com.filmes.model.interfaces.commons.ClickListener
import br.com.filmes.model.entity.Movie

class MovieAdapter(val listener: ClickListener) : RecyclerView.Adapter<MovieViewHolder>() {
    var lMovie = mutableListOf<Movie>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.cardview_movie, parent, false))
    }

    override fun getItemCount(): Int {
        return lMovie.size
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(lMovie[position], listener)
    }

    fun setListMovie(lMovie: List<Movie>) {
        this.lMovie.clear()
        this.lMovie.addAll(lMovie)
        notifyDataSetChanged()
    }
}