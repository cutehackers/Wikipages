@file:Suppress("NOTHING_TO_INLINE")

package app.junhyounglee.wikipages.common

import android.util.Log

/**
 * class Sample : LogAware {
 *   fun example() {
 *     d("Hello World")
 *   }
 * }
 *
 * Result
 *  "Sample: Hello World"
 */
interface LogAware {
  val logTag: String
    get() = determineLogTag(javaClass)
}

private fun determineLogTag(clazz: Class<*>): String = clazz.simpleName.run {
  if (length > 23) {
    substring(0..23)
  } else {
    this
  }
}

inline fun LogAware.d(message: String, thr: Throwable? = null) {
  Log.d(logTag, message, thr)
}

inline fun LogAware.e(message: String, thr: Throwable? = null) {
  Log.e(logTag, message, thr)
}