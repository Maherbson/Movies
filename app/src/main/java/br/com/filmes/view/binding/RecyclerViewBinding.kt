package br.com.filmes.view.binding

import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import br.com.filmes.viewmodel.MovieViewModel

class RecyclerViewBinding {

    object BindingAdapters {
        var pastVisibleItems: Int = 0
        var visibleItemCount: Int = 0
        var totalItemCount: Int = 0

        @BindingAdapter("viewModel")
        @JvmStatic
        fun bind(recyclerView: RecyclerView, viewModel: MovieViewModel) {
            val linearLayoutManager = GridLayoutManager(recyclerView.context, viewModel.spanCount)
            recyclerView.layoutManager = linearLayoutManager
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    visibleItemCount = linearLayoutManager.childCount
                    totalItemCount = linearLayoutManager.itemCount
                    pastVisibleItems = linearLayoutManager.findFirstVisibleItemPosition()

                    if (dy > 0) {
                        if (viewModel.loadMore()) {
                            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                                viewModel.page++
                                if (viewModel.lMovieSearch.isEmpty()) {
                                    viewModel.callMovie()
                                } else {
                                    viewModel.searchMovie()
                                }
                                viewModel.loadMore = false
                            }
                        }
                    }
                }
            })

            recyclerView.adapter = viewModel.adapter
            recyclerView.setHasFixedSize(true)
            viewModel.lMovie().observe(recyclerView.context as AppCompatActivity, Observer { movie ->
                if (movie != null) {
                    recyclerView.setItemViewCacheSize(movie.size + 1)
                    viewModel.adapter.setListMovie(movie)
                } else {
                    viewModel.adapter.setListMovie(mutableListOf())
                }
            })
        }

    }
}