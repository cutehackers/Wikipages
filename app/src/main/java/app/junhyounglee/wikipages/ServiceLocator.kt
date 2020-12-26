package app.junhyounglee.wikipages

import android.content.Context
import app.junhyounglee.libcore.net.HttpClient
import app.junhyounglee.wikipages.data.WikiDataSource
import app.junhyounglee.wikipages.data.WikiRepository
import app.junhyounglee.wikipages.data.remote.WikiApiService
import app.junhyounglee.wikipages.data.remote.WikiRemoteDataSource
import app.junhyounglee.wikipages.data.remote.internal.WikiApiServiceImpl
import app.junhyounglee.wikipages.domain.usecase.SearchUseCase
import kotlinx.coroutines.Dispatchers

/**
 * Dependency Graph
 */
object ServiceLocator {

  fun provideWikiRepository(context: Context): WikiDataSource =
      WikiRepository(provideWikiRemoteDataSource(context))

  private fun provideWikiRemoteDataSource(context: Context): WikiRemoteDataSource =
      WikiRemoteDataSource(provideWikiApiService(context))

  private fun provideWikiApiService(context: Context): WikiApiService = WikiApiServiceImpl.builder()
      .httpClient(provideHttpClient())
      .baseUrl(context.getString(R.string.wikipedia_base_url))
      .build()

  fun provideHttpClient() = HttpClient.builder().build()

  fun provideSearchUseCase(wikiDataSource: WikiDataSource) =
      SearchUseCase(Dispatchers.IO, wikiDataSource)
}
