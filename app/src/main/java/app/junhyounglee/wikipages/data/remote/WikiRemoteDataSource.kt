package app.junhyounglee.wikipages.data.remote

import app.junhyounglee.wikipages.data.WikiDataSource
import app.junhyounglee.wikipages.domain.model.WikiPage
import app.junhyounglee.wikipages.domain.model.WikiSummary
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WikiRemoteDataSource(
    private val apiService: WikiApiService
) : WikiDataSource {

  /**
   * 요약 정보 검색
   */
  override fun getSearchSummary(query: String): Flow<WikiSummary> = flow {
    emit(apiService.searchSummary(query))
  }

  /**
   * 연관 검색 페이지
   */
  override fun getSearchPages(query: String): Flow<List<WikiPage>> = flow {
    emit(apiService.searchPages(query))
  }
}
