package br.com.filmes.view.binding

import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ProgressBar
import br.com.filmes.viewmodel.MovieViewModel

class ProgressBarBinding {

    companion object {

        @BindingAdapter("visible")
        @JvmStatic
        fun bindVisible(view: ProgressBar, viewModel: MovieViewModel) {
            viewModel.progressBar.observe(view.context as AppCompatActivity, Observer {
                visible-> view.visibility = if (visible!!) View.VISIBLE else View.GONE
            })
        }
    }

}