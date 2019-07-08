package s.sin.githubrepositories.ui

class Event<out T>(private val content: T) {

    fun getContent(): T = content
}
