package br.com.filmes.util

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.net.ConnectivityManager

class NetworkUtil {

    companion object {
        fun checkInternet(context: Context): LiveData<Boolean> {
            val check =  MutableLiveData<Boolean>().apply {
                value = true
            }

            val connectManager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            check.value = (connectManager.activeNetworkInfo != null
                    && connectManager.activeNetworkInfo.isConnectedOrConnecting)
            return check
        }
    }

}