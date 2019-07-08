package s.sin.githubrepositories.ui

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

/**
 * Kotlin Custom 拡張
 *
 */

/**
 * 値が変化したら通知するLiveData
 */
fun <T> LiveData<T>.observeChanged(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
    observe(lifecycleOwner, object : Observer<T> {
        private var a: T? = null
        override fun onChanged(t: T?) {
            if (a != t) {
                a = t
                observer.onChanged(t)
            }
        }
    })
}