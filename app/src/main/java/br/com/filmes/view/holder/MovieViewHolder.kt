package br.com.filmes.view.holder

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import br.com.filmes.databinding.CardviewMovieBinding
import br.com.filmes.model.interfaces.commons.ClickListener
import br.com.filmes.model.entity.Movie

class MovieViewHolder(itemView: CardviewMovieBinding): RecyclerView.ViewHolder(itemView.root) {
    val card: CardviewMovieBinding = DataBindingUtil.bind(itemView.root)!!
    fun bind(movie: Movie, listener: ClickListener) {
        card.movie = movie
        card.listener = listener
        card.executePendingBindings()
    }

}