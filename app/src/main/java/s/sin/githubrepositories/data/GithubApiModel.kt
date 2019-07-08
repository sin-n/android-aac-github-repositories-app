package s.sin.githubrepositories.data

data class GithubRepositories constructor(
    val totalCount: Int,
    val items: List<GithubRepository>
)

data class GithubRepository constructor(
    val id: Int,
    val name: String,
    val fullName: String,
    val owner: GithubOwner,
    val htmlUrl: String,
    val license: GithubLicense?
)

data class GithubOwner constructor(
    val login: String,
    val avatarUrl: String
)

data class GithubLicense constructor(
    val name: String,
    val url: String
)