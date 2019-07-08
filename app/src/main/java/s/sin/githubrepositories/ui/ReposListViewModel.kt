package s.sin.githubrepositories.ui

import android.opengl.Visibility
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import s.sin.githubrepositories.data.DataRepository
import s.sin.githubrepositories.data.GithubApiService
import s.sin.githubrepositories.data.GithubRepository

class ReposListViewModel(private val apiService: GithubApiService, private val dataRepository: DataRepository) : ViewModel() {

    // Repository List
    private val _items = MutableLiveData<List<GithubRepository>>()
    val items: LiveData<List<GithubRepository>>
        get() = _items

    // Loading
    private val _loading = MutableLiveData<Boolean>().apply { value = false }
    val loading: LiveData<Boolean>
        get() = _loading

    private val _emptyVisible = MutableLiveData<Int>().apply { value = View.VISIBLE }
    val emptyVisible: LiveData<Int>
        get() = _emptyVisible

    private val _progressVisible = MutableLiveData<Int>().apply { value = View.GONE }
    val progressVisible: LiveData<Int>
        get() = _progressVisible

    // Input Text
    val inputText = MutableLiveData<String>().apply { value = "" }

    // Error
    private val _errorMsg = MutableLiveData<String>()
    val errorMsg: LiveData<String>
        get() = _errorMsg

    // InputKeywordEvent
    private val _eventInputKeyword = MutableLiveData<Event<String?>>()
    val eventInputKeyword: LiveData<Event<String?>>
        get() = _eventInputKeyword

    private var _eventCount = 0

    fun search(keyword: String) {
        loadingPost(true)
        clearList()
        // Retrofit & Coroutines による非同期通信処理
        GlobalScope.launch {
            try {
                val response = apiService.searchRepositories(keyword, "starts", "desc")
                if (response.isSuccessful) {
                    val items = response.body()?.items
                    _items.postValue(items)
                    var visible = View.VISIBLE
                    if (items != null && items.isNotEmpty()) {
                        visible = View.GONE
                    }
                    _emptyVisible.postValue(visible)
                    dataRepository.setGithubRepos(items)
                } else {
                    val msg = response.errorBody()?.string()
                    onSearchError(msg)
                }
            } catch (e: Exception) {
                val msg = e.toString()
                onSearchError(msg)
            }
            loadingPost(false)
        }
    }

    private fun loadingPost(flag: Boolean) {
        _loading.postValue(flag)
        var visible = if (flag) View.VISIBLE else View.GONE
        _progressVisible.postValue(visible)
    }

    private fun onSearchError(msg: String?) {
        errorLog("Search Error: $msg")
        _errorMsg.postValue(msg)
        clearList()
    }

    fun clearList() {
        _items.postValue(emptyList())
    }

    private fun log(msg: String) {
        Log.d("RLVM", msg)
    }

    private fun errorLog(msg: String) {
        Log.e("RLVM", msg)
    }

    fun searchTest() {
        search("kotlin")
    }

    fun showInputKeyword() {
        log("showInputKeyword")

//        val keyword = inputText.value
        val keyword = (_eventCount++).toString()
        _eventInputKeyword.postValue(Event(keyword))
    }
}