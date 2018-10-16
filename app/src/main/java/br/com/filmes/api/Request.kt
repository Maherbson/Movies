package br.com.filmes.api

import android.content.Context
import br.com.filmes.constants.ConstantsUrl
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

abstract class Request {

    object REQUEST {
        var cacheSize: Long = 20 * 1024 * 1024
        var retrofit: Retrofit? = null

        fun getInstance(context: Context?) {
            val cache = Cache(context?.cacheDir, cacheSize)
            val okHttp = OkHttpClient.Builder()
                    .cache(cache)
                    .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(ConstantsUrl.BASE_URL)
                        .client(okHttp)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build()
            }
        }
    }

}