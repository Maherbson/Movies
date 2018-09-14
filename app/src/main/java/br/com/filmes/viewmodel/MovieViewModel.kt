package br.com.filmes.viewmodel

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.res.Configuration
import android.util.Log
import br.com.filmes.api.request.MovieRequest
import br.com.filmes.model.entity.Movie
import br.com.filmes.model.interfaces.commons.ClickListener
import br.com.filmes.view.adapter.MovieAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class MovieViewModel(val listener: ClickListener) : ViewModel() {

    private var language: String = Locale.getDefault().language
    private var regionCountry: String = Locale.getDefault().country
    private var movieRequest: MovieRequest = MovieRequest()

    private var lMovieTemp = mutableListOf<Movie>()
    var lMovieSearch = mutableListOf<Movie>()

    private var lMutableMovie = MutableLiveData<List<Movie>>()
    var refreshing = MutableLiveData<Boolean>()
    var progressBar = MutableLiveData<Boolean>().apply {
        value = true
    }

    var orientation: Int = 1
    var page: Int = 1

    var loadMore: Boolean = true
    var query: String? = ""

    var adapter = MovieAdapter(listener)

    private val layoutError = MutableLiveData<Boolean>().apply {
        value = false
    }

    fun checkListMovieIsEmpty() {
        if (lMovieSearch.size > 0) {
            lMutableMovie.value = lMovieSearch
        } else {
            if (lMovieTemp.size > 0) {
                lMutableMovie.value = lMovieTemp
            } else {
                callMovie()
            }
        }
    }

    fun refresh() {
        lMovieTemp.clear()
        clearListMovieSearch()
        callMovie()
    }

    fun callMovie() {
        progressBar.value = true
        layoutError.value = false
        movieRequest.getMovies(page, language, regionCountry)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ movie ->
                    if (movie != null) {
                        lMovieTemp.add(movie)
                    }
                }, { e ->
                    e.printStackTrace()
                    loadMore = true
                    refreshing.value = false
                    progressBar.value = false
                    layoutError.value = true
                }, {
                    lMutableMovie.value = lMovieTemp
                    loadMore = true
                    refreshing.value = false
                    progressBar.value = false
                    layoutError.value = false
                })
    }

    fun searchMovie() {
        if (query!!.isNotEmpty()) {
            movieRequest.getMovies(page, language, regionCountry, query!!).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ movie ->
                        if (movie != null) {
                            lMovieSearch.add(movie)
                        }
                    }, { e ->
                        e.printStackTrace()
                        loadMore = true
                        refreshing.value = false
                    }, {
                        lMutableMovie.value = lMovieSearch
                        loadMore = true
                        refreshing.value = false
                    })
        } else {
            clearListMovieSearch()
            lMutableMovie.value = lMovieTemp
        }
    }

    fun clearListMovieSearch() {
        if (lMovieSearch.isNotEmpty()) {
            lMovieSearch.clear()
            this.page = 1
        }
    }

    var spanCount: Int = 3
        get() {
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                return 3
            }
            return 5
        }

    fun lMovie(): LiveData<List<Movie>> = lMutableMovie
    fun loadMore(): Boolean = loadMore
    fun showLayoutError(): LiveData<Boolean> = layoutError
}