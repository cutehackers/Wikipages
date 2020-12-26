package app.junhyounglee.wikipages.data.remote.internal

import app.junhyounglee.libcore.net.HttpClient
import app.junhyounglee.libcore.net.HttpRequest
import app.junhyounglee.libcore.net.HttpResponse
import app.junhyounglee.wikipages.common.LogAware
import app.junhyounglee.wikipages.common.d
import app.junhyounglee.wikipages.common.e
import app.junhyounglee.wikipages.data.remote.WikiApiService
import app.junhyounglee.wikipages.domain.model.WikiPage
import app.junhyounglee.wikipages.domain.model.WikiSummary
import java.io.IOException

/**
 * Wiki api 구현
 */
class WikiApiServiceImpl internal constructor(
    private val httpClient: HttpClient,
    private val baseUrl: String
) : WikiApiService, LogAware {

  /**
   * 요약 정보 검색
   */
  override fun searchSummary(query: String): WikiSummary {
    d("Search> http-get: wiki summary started...")

    val request = HttpRequest.builder()
        .get()
        .url("${buildUrl("/api/rest_v1/page/summary")}/$query")
        .build()

    var response: HttpResponse? = null
    return try {
      response = httpClient.newTask(request).execute()

      val contents = response.string()
      d("Search> search wiki summary finished: $contents")

      when (response.code) {
        200 -> {
          WikiSummary.from(contents)
        }
        301, 302 -> {
          // TODO redirect 대응하기
          WikiSummary.EMPTY
        }
        404 -> {
          WikiSummary.EMPTY
        }
        else -> throw IOException()
      }

    } catch (e: Exception) {
      e("Search> error while requesting wiki summary...", e)
      throw e

    } finally {
      response?.close()
    }
  }

  /**
   * 연관 검색 페이지
   */
  override fun searchPages(query: String): List<WikiPage> {
    val request = HttpRequest.builder()
        .get()
        .url("${buildUrl("/api/rest_v1/page/related")}/$query")
        .build()

    var response: HttpResponse? = null
    return try {
      response = httpClient.newTask(request).execute()

      val contents = response.string()
      d("Search> search wiki pages finished: $contents")

      when (response.code) {
        200 -> {
          WikiPage.fromArray(contents)
        }
        404 -> {
          emptyList()
        }
        else -> throw IOException()
      }
    } catch (e: Exception) {
      e("Search> error while requesting wiki pages...", e)
      throw e

    } finally {
      response?.close()
    }
  }

  private fun buildUrl(path: String) = "$baseUrl$path"


  class Builder {
    private var httpClient: HttpClient? = null
    private var baseUrl: String? = null

    fun httpClient(httpClient: HttpClient) = apply {
      this.httpClient = httpClient
    }

    fun baseUrl(baseUrl: String) = apply {
      this.baseUrl = baseUrl
    }

    fun build() = WikiApiServiceImpl(
        httpClient = checkNotNull(httpClient) { "HttpClient must not be a null object" },
        baseUrl = checkNotNull(baseUrl) { "BaseUrl must not be a null object" }
    )
  }


  companion object {
    fun builder() = Builder()
  }
}