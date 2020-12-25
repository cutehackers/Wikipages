package app.junhyounglee.libcore.net

import org.junit.Assert.*
import org.junit.Test


class HttpRequestTest {

    @Test
    fun testDefaultMethodType() {
        val request = HttpRequest.builder().build()
        assertEquals(HttpMethod.GET.value, request.method.value)
    }

    @Test
    fun testGetMethodType() {
        val request = HttpRequest.builder()
            .get()
            .build()
        assertEquals(HttpMethod.GET.value, request.method.value)
    }

    @Test
    fun testPostMethodType() {
        val request = HttpRequest.builder()
            .post()
            .build()
        assertEquals(HttpMethod.POST.value, request.method.value)
    }

    @Test
    fun testPutMethodType() {
        val request = HttpRequest.builder()
            .put()
            .build()
        assertEquals(HttpMethod.PUT.value, request.method.value)
    }

    @Test
    fun testDeleteMethodType() {
        val request = HttpRequest.builder()
            .delete()
            .build()
        assertEquals(HttpMethod.DELETE.value, request.method.value)
    }
}