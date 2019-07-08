package s.sin.githubrepositories.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("url")
fun loadUrlImageView(view: ImageView, url: String) {
    Glide.with(view.context).load(url).into(view)
}
