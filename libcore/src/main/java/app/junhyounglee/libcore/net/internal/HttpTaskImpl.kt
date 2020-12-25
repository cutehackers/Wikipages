package app.junhyounglee.libcore.net.internal

import app.junhyounglee.libcore.net.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.nio.charset.Charset
import java.util.concurrent.atomic.AtomicBoolean

/**
 * HTTP-GET 구현
 */
class HttpTaskImpl internal constructor(
    private val client: HttpClient,
    private val request: HttpRequest
): HttpTask {

    private var connection: HttpURLConnection? = null
    private val running = AtomicBoolean()

    override fun execute(): HttpResponse {
        check(running.compareAndSet(false, true)) { "Already running!" }

        val connection = request.open().also {
            this@HttpTaskImpl.connection = it
            it.connectTimeout = client.connectTimeoutMillis
            it.readTimeout = client.readTimeoutMillis
        }

        var response: HttpResponse? = null
        try {
            response = when (request.method) {
                HttpMethod.GET -> {
                    connection.doOutput = false

                    HttpResponse.builder()
                        .request(request)
                        .headers(connection.headerFields)
                        .code(connection.responseCode)
                        .message(connection.responseMessage)
                        .source(connection.buffer())
                        .build()
                }

                else -> throw UnsupportedOperationException("HTTP method, ${request.method} is not a supported operation.")
            }

        } catch (e: IOException) {
            response?.close()
            connection.disconnect()
            throw  e
        }

        return response
    }

    override fun enqueue(onSuccess: HttpOnSuccess, onFailure: HttpOnFailure?) {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        connection?.disconnect() ?: throw IllegalStateException("HTTP request was not executed yet!")
    }

    override fun isRunning(): Boolean = running.get()

    private fun HttpURLConnection.buffer(): BufferedReader =
        BufferedReader(InputStreamReader(inputStream, Charset.forName("UTF-8")))
}
