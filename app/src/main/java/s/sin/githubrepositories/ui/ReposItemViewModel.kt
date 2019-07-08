package s.sin.githubrepositories.ui

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import s.sin.githubrepositories.data.GithubRepository

class ReposItemViewModel(private val item: GithubRepository, private val deletable: Boolean) {

    val id: LiveData<Int> = MutableLiveData<Int>().apply { value = item.id }

    val name: LiveData<String> = MutableLiveData<String>().apply { value = item.name }

    val avatarUrl: LiveData<String> = MutableLiveData<String>().apply { value = item.owner.avatarUrl }

    val deleteVisible: LiveData<Int> = MutableLiveData<Int>().apply { value = if (deletable) View.VISIBLE else View.GONE }
}