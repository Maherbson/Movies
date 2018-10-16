package br.com.filmes.view.binding

import android.content.res.Resources
import android.databinding.BindingAdapter
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawable
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory
import android.widget.ImageView
import br.com.filmes.R
import br.com.filmes.constants.ConstantsUrl
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class ImageBinding {

    object BindingAdapters {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun loadImage(imageView: ImageView, avatarUrl: String) {
            setImageView(imageView, avatarUrl, 105, 150)
        }

        @BindingAdapter("imageDetail")
        @JvmStatic
        fun imageDetail(imageView: ImageView, avatarUrl: String) {
            setImageView(imageView, avatarUrl, imageView.width, 350)
        }

        fun setImageView(imageView: ImageView, avatarUrl: String, width: Int, height: Int) {
            if (avatarUrl != null && !avatarUrl.equals("")) {
                val path = "${ConstantsUrl.URL_IMAGE_PATH}$avatarUrl"
                Picasso.with(imageView.context)
                        .load(path)
                        .resize(width, height)
                        .into(imageView, object : Callback {
                            override fun onSuccess() {
                                val bitmap: Bitmap = (imageView.drawable as BitmapDrawable).bitmap
                                val imageRoundedBitmap: RoundedBitmapDrawable = RoundedBitmapDrawableFactory.create(Resources.getSystem(), bitmap)
                                imageView.setImageBitmap(bitmap)
                            }

                            override fun onError() {
                                imageView.setImageResource(R.drawable.ic_movies)
                            }

                        })
            } else {
                imageView.setImageResource(R.drawable.ic_movies)
            }
        }
    }
}