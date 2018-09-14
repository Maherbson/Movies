package br.com.filmes.api.request

import android.util.Log
import br.com.filmes.api.Request
import br.com.filmes.constants.ConstantsUrl
import br.com.filmes.model.interfaces.endpoints.MovieEndpoint
import br.com.filmes.model.entity.Genre
import br.com.filmes.model.entity.GenreResult
import br.com.filmes.model.entity.Movie
import br.com.filmes.model.entity.MovieResult
import io.reactivex.Observable
import io.reactivex.functions.BiFunction

class MovieRequest : Request() {

    var lGenre: Observable<List<Genre>>? = null

    fun getMovies(page: Int, language: String, regionCountry: String, query: String = ""): Observable<Movie> {
        Log.i("PAGE", page.toString())
        val lMovie = getMovie(page, language, regionCountry, query)
        if (lGenre == null) {
            lGenre = getGenre(language, regionCountry)
        }

        return getObjectMovie(lMovie, lGenre)
    }

    private fun getMovie(page: Int, language: String, regionCountry: String, query: String): Observable<List<Movie>> {
        val request: MovieEndpoint.GetMovie = Request.REQUEST.retrofit!!.create(MovieEndpoint.GetMovie::class.java)
        val call: Observable<MovieResult>

        call = if (query != "") {
            request.getMovie(ConstantsUrl.API_KEY_AUTH,
                    "$language-$regionCountry", query, page, false)
        } else {
            request.getMovie(ConstantsUrl.API_KEY_AUTH,
                    "$language-$regionCountry", page, regionCountry)
        }

        return call.flatMap { movieResult -> Observable.just(movieResult.results) }
    }

    private fun getObjectMovie(lMovie: Observable<List<Movie>>, lGenre: Observable<List<Genre>>?): Observable<Movie> {
        return lMovie.flatMap { movieList ->
            Observable.fromIterable(movieList)
                    .flatMap { movie ->
                        Observable.zip(
                                Observable.just(movie),
                                Observable.fromIterable(movie.lGenresId)
                                        .flatMap { idGenre ->
                                            idGenre
                                            Observable.concat(getGenreById(idGenre, lGenre!!),
                                                    lGenre.flatMap { listGenre ->
                                                        Observable.fromIterable(listGenre)
                                                                .filter { genre ->
                                                                    genre.id == idGenre
                                                                }
                                                    }).take(1)
                                        }
                                        .toList()
                                        .toObservable(),
                                BiFunction<Movie, List<Genre>, Movie> { movie, genre ->
                                    if (movie.lGenre == null) {
                                        movie.lGenre = ArrayList()
                                    }
                                    movie.lGenre.addAll(genre)
                                    movie
                                }
                        )
                    }
        }
    }

    private fun getGenre(language: String, regionCountry: String): Observable<List<Genre>> {
        val requestGenre: MovieEndpoint.GenreMovie = Request.REQUEST.retrofit!!.create(MovieEndpoint.GenreMovie::class.java)
        val callGenre: Observable<GenreResult> = requestGenre.getGenreMovie(ConstantsUrl.API_KEY_AUTH,
                "$language-$regionCountry")
        return callGenre.flatMap { genreResult -> Observable.just(genreResult.results) }
    }

    private fun getGenreById(idGenre: Int, lGenre: Observable<List<Genre>>): Observable<Genre> {
        return lGenre.flatMap { listGenre ->
            Observable.fromIterable(listGenre)
                    .filter { genre ->
                        genre.id == idGenre
                    }
                    .flatMap { genre ->
                        Observable.just(genre)
                    }
        }
    }

}