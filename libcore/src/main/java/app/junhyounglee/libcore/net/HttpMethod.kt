package app.junhyounglee.libcore.net

sealed class HttpMethod(val value: String) {
  object GET : HttpMethod("GET")
  object POST : HttpMethod("POST")
  object PUT : HttpMethod("PUT")
  object DELETE : HttpMethod("DELETE")
}
