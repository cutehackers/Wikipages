package app.junhyounglee.libcore.net

import java.io.BufferedReader

/**
 * HTTP 응답
 *
 * val response = HttpResponse.builder().build()
 * response.string()
 */
class HttpResponse internal constructor(
    val request: HttpRequest,
    val headers: Map<String, List<String>>,
    val code: Int,
    val message: String,
    val source: BufferedReader
) {

  fun close() {
    source.close()
  }

  fun string(): String = buildString {
    val reader = source
    var line: String?
    while (reader.readLine().also { line = it } != null) {
      append(line).append("\n")
    }
  }


  //------------------------------------------------------------------------------------------------
  // inner class: Builder
  class Builder {
    private var request: HttpRequest? = null
    private var headers: Map<String, List<String>> = emptyMap()
    private var code: Int = -1
    private var message: String? = null
    private var source: BufferedReader? = null

    fun request(request: HttpRequest) = apply {
      this.request = request
    }

    fun headers(headers: Map<String, List<String>>) = apply {
      this.headers = headers
    }

    fun code(code: Int) = apply {
      this.code = code
    }

    fun message(message: String) = apply {
      this.message = message
    }

    fun source(source: BufferedReader) = apply {
      this.source = source
    }

    fun build() = HttpResponse(
        request = checkNotNull(request) { "http request must not be a null object!" },
        headers = headers,
        code = code,
        message = checkNotNull(message) { "message must not be a null object!" },
        source = checkNotNull(source) { "source stream must not be a null object!" }
    )
  }


  companion object {
    @JvmStatic
    fun builder() = Builder()
  }
}