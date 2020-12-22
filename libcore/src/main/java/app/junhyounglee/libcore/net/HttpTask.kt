package app.junhyounglee.libcore.net

import java.io.IOException

interface HttpTask {
  /**
   * 블러킹콜 형태로 태스크를 실행한다.
   */
  @Throws(IOException::class)
  fun execute(): HttpResponse

  /**
   * 태스크를 스케줄링하고 실패나 성공시 callback을 호출하여 종료한다.
   */
  fun enqueue(callback: HttpCallback)

  /**
   * 태스크를 취소한다.
   */
  fun cancel()

  /**
   * 태스트 생성을 위한 팩터리 인터페이스
   */
  fun interface Factory {
    fun newTask(request: HttpRequest): HttpTask
  }
}
