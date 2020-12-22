package app.junhyounglee.libcore.net.internal

import app.junhyounglee.libcore.net.*

/**
 * HttpTask 구현
 */
class HttpTaskImpl internal constructor(
    val client: HttpClient,
    val request: HttpRequest
): HttpTask {
    override fun execute(): HttpResponse {
        TODO("Not yet implemented")
    }

    override fun enqueue(callback: HttpCallback) {
        TODO("Not yet implemented")
    }

    override fun cancel() {
        TODO("Not yet implemented")
    }
}
