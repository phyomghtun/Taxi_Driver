package apk.fortaxi.com.extension

import android.widget.ImageView
import apk.fortaxi.com.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

fun ImageView.loadUrl(url: String?) =
    if(url != null){
        Glide.with(this.context.applicationContext)
            .load(url)
            .circleCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }else{
        Glide.with(this.context.applicationContext)
            .load(R.drawable.user)
            .circleCrop()
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(this)
    }