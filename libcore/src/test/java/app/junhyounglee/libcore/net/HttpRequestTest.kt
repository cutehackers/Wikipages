package app.junhyounglee.libcore.net

import org.junit.Assert.*
import org.junit.Test


class HttpRequestTest {

    @Test
    fun testDefaultMethodType() {
        val request = HttpRequest.builder()
            .url(SAMPLE_URL)
            .build()
        assertEquals(HttpMethod.GET.value, request.method.value)
    }

    @Test
    fun testGetMethodType() {
        val request = HttpRequest.builder()
            .get()
            .url(SAMPLE_URL)
            .build()
        assertEquals(HttpMethod.GET.value, request.method.value)
    }

    @Test
    fun testPostMethodType() {
        val request = HttpRequest.builder()
            .post()
            .url(SAMPLE_URL)
            .build()
        assertEquals(HttpMethod.POST.value, request.method.value)
    }

    @Test
    fun testPutMethodType() {
        val request = HttpRequest.builder()
            .put()
            .url(SAMPLE_URL)
            .build()
        assertEquals(HttpMethod.PUT.value, request.method.value)
    }

    @Test
    fun testDeleteMethodType() {
        val request = HttpRequest.builder()
            .delete()
            .url(SAMPLE_URL)
            .build()
        assertEquals(HttpMethod.DELETE.value, request.method.value)
    }


    companion object {
        private const val SAMPLE_URL = "https://www.google.com"
    }
}