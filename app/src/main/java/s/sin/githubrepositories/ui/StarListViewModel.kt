package s.sin.githubrepositories.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import s.sin.githubrepositories.data.DataRepository
import s.sin.githubrepositories.data.GithubRepository

class StarListViewModel(private val dataRepository: DataRepository) : ViewModel() {

    private val _items = MutableLiveData<List<GithubRepository>>().apply { value =  dataRepository.readStars() }
    val items: LiveData<List<GithubRepository>>
        get() = _items

    private val _eventDelete = MutableLiveData<Event<Int>>()
    val eventDelete: LiveData<Event<Int>>
        get() = _eventDelete


    fun updateList() {
        _items.postValue(dataRepository.readStars())
    }

    fun deleteStar(id: Int) {
        dataRepository.deleteStar(id)
        updateList()
        _eventDelete.postValue(Event(id))
    }
}