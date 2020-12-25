package app.junhyounglee.libcore.net

import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

/**
 *
 */
fun interface StreamConverter {
  fun convert(input: InputStream): String
}

class StringStreamConverter : StreamConverter {
  override fun convert(input: InputStream): String {
    return input.use {
      buildString {
        val reader = BufferedReader(InputStreamReader(it, Charset.forName("UTF-8")))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
          append(line).append("\n")
        }
      }
    }
  }
}