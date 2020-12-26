package app.junhyounglee.wikipages.data.remote

import app.junhyounglee.wikipages.domain.model.WikiPage
import app.junhyounglee.wikipages.domain.model.WikiSummary

/**
 * Wiki 검색 API 구현
 */
interface WikiApiService {

  /**
   * 요약 정보 검색
   */
  fun searchSummary(query: String): WikiSummary

  /**
   * 연관 검색 페이지
   */
  fun searchPages(query: String): List<WikiPage>
}