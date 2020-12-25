package app.junhyounglee.libcore.net

import app.junhyounglee.libcore.net.internal.HttpTaskImpl
import java.util.concurrent.TimeUnit

/**
 * 서버와 통신을 하기 위한 HTTP 통신 관리 모듈
 *
 * 1) 클라이언트 생성
 *   val client = HttpClient()
 *
 * 2) HTTP 요청 생성 후 실행
 *   val request = HttpRequest.builder()
 *     .get()
 *     .url(URL("http://www.google.com"))
 *     .build()
 *
 *   try (val response: HttpResponse = request.client(client).execute()) {
 *    ...
 *   }
 *
 * 2-1) HTTP 요청 생성 후 실행, 파라메터 값
 *   val request = HttpRequest.builder()
 */
class HttpClient internal constructor(builder: Builder) : HttpTask.Factory {

  @get:JvmName("connectTimeoutMillis")
  val connectTimeoutMillis = builder.connectTimeout

  @get:JvmName("readTimeoutMillis")
  val readTimeoutMillis = builder.readTimeout

  @get:JvmName("writeTimeoutMillis")
  val writeTimeoutMillis = builder.writeTimeout

  constructor() : this(Builder())

  //------------------------------------------------------------------------------------------------
  // implementation: HttpTask.Factory

  override fun newTask(request: HttpRequest): HttpTask = HttpTaskImpl(this, request)


  //------------------------------------------------------------------------------------------------
  // inner class: Builder

  class Builder {
    internal var connectTimeout = 10 * 1000 // 10 seconds
    internal var readTimeout = 10 * 1000    // 10 seconds
    internal var writeTimeout = 10 * 1000   // 10 seconds

    fun connectTimeout(timeout: Long, unit: TimeUnit) = apply {
      connectTimeout = getDuration(timeout, unit)
    }

    fun readTimeout(timeout: Long, unit: TimeUnit) = apply {
      readTimeout = getDuration(timeout, unit)
    }

    fun writeTimeout(timeout: Long, unit: TimeUnit) = apply {
      writeTimeout = getDuration(timeout, unit)
    }

    private fun getDuration(timeout: Long, unit: TimeUnit): Int {
      val millis = unit.toMillis(timeout)
      require(millis <= Integer.MAX_VALUE) { "too large." }
      require(millis != 0L || timeout <= 0L) { "too small." }
      return millis.toInt()
    }

    fun build(): HttpClient = HttpClient(this)
  }


  companion object {
    @JvmStatic
    fun builder() = Builder()
  }
}
