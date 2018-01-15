package pl.kitek.rvwithcardview

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation

fun dp2px(dp: Int): Float = dp * Resources.getSystem().displayMetrics.density

fun isLollipop() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

fun ViewGroup.inflate(layoutId: Int, attachToRoot: Boolean = false): View =
        LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

fun ImageView.loadImage(url: String, transformation: Transformation? = null, callback: Callback? = null) {
    if (url.isEmpty()) return
    val picasso = Picasso
            .with(context)
            .load(url)
            .config(Bitmap.Config.RGB_565)
    transformation?.let { picasso.transform(it) }
    picasso.into(this, callback)
}
