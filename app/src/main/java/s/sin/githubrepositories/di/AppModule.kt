package s.sin.githubrepositories.di

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import s.sin.githubrepositories.data.DataRepository
import s.sin.githubrepositories.data.GithubApiService
import s.sin.githubrepositories.data.GithubRepository
import s.sin.githubrepositories.ui.*

/**
 * DI: Koin
 *
 */

val appModule = module {
    single { DataRepository() }
    viewModel { TopViewModel() }
    viewModel { ReposListViewModel(get(), get()) }
    viewModel { StarListViewModel(get()) }
    viewModel { InputKeywordViewModel() }
    viewModel { (id: Int) -> ReposDetailViewModel(get(), id) }
}

val networkModule = module {
    single {
        Retrofit
            .Builder()
            .client(
                OkHttpClient.Builder().addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                ).build()
            )
            .baseUrl("https://api.github.com/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setFieldNamingPolicy(
                        FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES
                    ).serializeNulls().create()))
            .build()
            .create(GithubApiService::class.java)
    }
}
