package br.com.filmes.model.entity

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Parcelable
import android.text.TextUtils
import com.android.databinding.library.baseAdapters.BR
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Movie(@SerializedName("id") private var mId: Int,
            @SerializedName("title") private var mTitle: String,
            @SerializedName("overview") private var mOverview: String,
            @SerializedName("poster_path") private var mPosterPath: String?,
            @SerializedName("backdrop_path") private var mBackdropPath: String?,
            @SerializedName("release_date") private var mReleaseDate: String,
            @SerializedName("genre_ids") private var mLGenresId: List<Int>,
            private var mLGenre: MutableList<Genre> = ArrayList()) : BaseObservable(), Parcelable {


    var id: Int
        @Bindable get() = mId
        set(value) {
            mId = value
            notifyPropertyChanged(BR.id)
        }

    var title: String
        @Bindable get() = mTitle
        set(value) {
            mTitle = value
            notifyPropertyChanged(BR.title)
        }
    var overview: String
        @Bindable get() = mOverview
        set(value) {
            mOverview = value
            notifyPropertyChanged(BR.overview)
        }


    var posterPath: String?
        @Bindable get() = mPosterPath
        set(value) {
            mPosterPath = value
            notifyPropertyChanged(BR.posterPath)
        }

    var backdropPath: String?
        @Bindable get() = mBackdropPath
        set(value) {
            mBackdropPath = value
            notifyPropertyChanged(BR.backdropPath)
        }

    var releaseDate: String
        @Bindable get() = mReleaseDate
        set(value) {
            mReleaseDate = value
            notifyPropertyChanged(BR.releaseDate)
        }

    var lGenresId: List<Int>
        @Bindable get() = mLGenresId
        set(value) {
            mLGenresId = value
            notifyPropertyChanged(BR.lGenresId)
        }

    var lGenre: MutableList<Genre>
        @Bindable get() = mLGenre
        set(value) {
            mLGenre = value
            notifyPropertyChanged(BR.lGenre)
        }

    fun getGenre(): String {
        if (lGenre.isEmpty()) {
            return "Sem gênero."
        }
        return TextUtils.join(", ", lGenre)
    }
}