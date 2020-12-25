package app.junhyounglee.libcore.net

import java.io.IOException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

/**
 * HTTP 요청
 *
 * val request = HttpRequest.builder()
 *  .get()
 *  .url(URL("http://www.google.com"))
 *  .build()
 *
 * TODO RequestBody 구현
 */
class HttpRequest internal constructor(
    val url: URL,
    val method: HttpMethod,
    val headers: Map<String, String>
) {

  @Throws(IOException::class)
  fun open(): HttpURLConnection {
    val connection = url.openConnection() as HttpURLConnection
    return connection.apply {
      requestMethod = method.value
      addHeaders(connection, headers)
    }
  }

  private fun addHeaders(connection: HttpURLConnection, headers: Map<String, String>) {
    headers.forEach { (key, value) ->
      connection.setRequestProperty(key, value)
    }
  }


  //------------------------------------------------------------------------------------------------
  // inner class: Builder

  class Builder {
    private var url: URL? = null
    private var method: HttpMethod = HttpMethod.GET
    private var headers: Map<String, String> = emptyMap()

    fun url(url: URL) = apply {
      this.url = url
    }

    @Throws(MalformedURLException::class)
    fun url(url: String) = apply {
      this.url = URL(url)
    }

    fun get() = apply {
      method(HttpMethod.GET)
    }

    fun post() = apply {
      method(HttpMethod.POST)
    }

    fun put() = apply {
      method(HttpMethod.GET)
    }

    fun delete() = apply {
      method(HttpMethod.DELETE)
    }

    private fun method(method: HttpMethod) = apply {
      this.method = method
    }

    fun headers(headers: Map<String, String>) = apply {
      this.headers = headers
    }

    fun build() = HttpRequest(
        url = checkNotNull(url) { "url must not be null or empty!" },
        method = method,
        headers = headers
    )
  }


  companion object {
    @JvmStatic
    fun builder() = Builder()
  }
}
