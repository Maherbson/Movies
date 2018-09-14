package br.com.filmes.model.entity

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcelable
import com.android.databinding.library.baseAdapters.BR
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Genre(@SerializedName("id") private var mId: Int,
            @SerializedName("name") private var mName: String) : BaseObservable(), Parcelable {

    var id: Int
        @Bindable get() = mId
        set(value) {
            mId = value
            notifyPropertyChanged(BR.id)
        }

    var name: String
        @Bindable get() = mName
        set(value) {
            mName = value
            notifyPropertyChanged(BR.name)
        }

    override fun toString(): String {
        return name
    }

}