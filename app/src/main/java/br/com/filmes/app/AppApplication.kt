package br.com.filmes.app

import android.app.Application
import br.com.filmes.api.Request

class AppApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        Request.REQUEST.getInstance(applicationContext)
    }

}