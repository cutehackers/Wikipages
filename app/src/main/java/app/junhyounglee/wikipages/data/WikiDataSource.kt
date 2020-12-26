package app.junhyounglee.wikipages.data

import app.junhyounglee.wikipages.domain.model.WikiPage
import app.junhyounglee.wikipages.domain.model.WikiSummary
import kotlinx.coroutines.flow.Flow

interface WikiDataSource {
  /**
   * 요약 정보 검색
   */
  fun getSearchSummary(query: String): Flow<WikiSummary>

  /**
   * 연관 검색 페이지
   */
  fun getSearchPages(query: String): Flow<List<WikiPage>>
}
