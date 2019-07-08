package s.sin.githubrepositories.data

class DataRepository {

    private var _reposList: HashMap<String, GithubRepository> = HashMap()
    private var _starList: HashMap<String, GithubRepository> = HashMap()

    fun setGithubRepos(list: List<GithubRepository>?) {
        _reposList.clear()
        if (!list.isNullOrEmpty()) {
            list.forEach {
                _reposList[it.id.toString()] = it
            }
        }
        // starしたRepositoryも追加しておく
        _starList.forEach { (k, v) -> _reposList[k] = v }
    }

    fun getGithubRepos(id: Int): GithubRepository? {
        return _reposList[id.toString()]
    }

    fun createStar(id: Int) {
        val repos = getGithubRepos(id)!!
        _starList[id.toString()] = repos
    }

    fun deleteStar(id: Int) {
        _starList.remove(id.toString())
    }

    fun readStar(id: Int): GithubRepository? {
        return _starList[id.toString()]
    }

    fun existStar(id: Int): Boolean {
        return readStar(id) != null
    }

    fun readStars(): List<GithubRepository> {
        val list = ArrayList<GithubRepository>()
        _starList.forEach{(k, v) -> list.add(v)}
        return list
    }
}