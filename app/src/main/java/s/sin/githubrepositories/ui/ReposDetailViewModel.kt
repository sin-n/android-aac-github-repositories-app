package s.sin.githubrepositories.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import s.sin.githubrepositories.data.DataRepository
import s.sin.githubrepositories.data.GithubRepository

class ReposDetailViewModel(private val dataRepository: DataRepository, reposId: Int): ViewModel() {

    private val item: GithubRepository = dataRepository.getGithubRepos(reposId)!!

    val title: LiveData<String>
        get() = MutableLiveData<String>().apply { value = item.name }

    val name: LiveData<String>
        get() = MutableLiveData<String>().apply { value = item.owner.login }

    val avatarUrl: LiveData<String>
        get() = MutableLiveData<String>().apply { value = item.owner.avatarUrl }

    val url: LiveData<String>
        get() = MutableLiveData<String>().apply { value = item.htmlUrl }

    val licenseName: LiveData<String>
        get() = MutableLiveData<String>().apply { value = item.license?.name }

    val licenseUrl: LiveData<String>
        get() = MutableLiveData<String>().apply { value = item.license?.url }

    val star = MutableLiveData<Boolean>().apply { value = dataRepository.existStar(item.id) }

    fun addStar() {
        dataRepository.createStar(item.id)
    }

    fun removeStar() {
        dataRepository.deleteStar(item.id)
    }

    fun update() {
        star.postValue(dataRepository.existStar(item.id))
    }

}