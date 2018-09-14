package br.com.filmes.model.interfaces.commons

import br.com.filmes.model.entity.Movie

interface MovieResponse {
    fun getMovie(lMovie: List<Movie>?)
}