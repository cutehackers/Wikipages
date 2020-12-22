package app.junhyounglee.libcore.net

import java.io.IOException

interface HttpCallback {
  /**
   * HttpTask 실패시 호출된다.
   */
  fun onFailure(task: HttpTask, e: IOException)

  /**
   * HttpTask 성공시 호출된다.
   */
  @Throws(IOException::class)
  fun onSuccess(task: HttpTask, response: HttpResponse)
}
