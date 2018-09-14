package br.com.filmes.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import br.com.filmes.model.interfaces.commons.ClickListener

class MovieViewModelFactory(private val listener: ClickListener): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(listener) as T
    }

}