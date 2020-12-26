package app.junhyounglee.wikipages.data

import app.junhyounglee.wikipages.data.remote.WikiRemoteDataSource
import app.junhyounglee.wikipages.domain.model.WikiPage
import app.junhyounglee.wikipages.domain.model.WikiSummary
import kotlinx.coroutines.flow.Flow

/**
 * 위키 데이터 제공자
 */
class WikiRepository(
    private val wikiRemoteDataSource: WikiRemoteDataSource
) : WikiDataSource {

  /**
   * 요약 정보 검색
   */
  override fun getSearchSummary(query: String): Flow<WikiSummary> =
      wikiRemoteDataSource.getSearchSummary(query)

  /**
   * 연관 검색 페이지
   */
  override fun getSearchPages(query: String): Flow<List<WikiPage>> =
      wikiRemoteDataSource.getSearchPages(query)

}