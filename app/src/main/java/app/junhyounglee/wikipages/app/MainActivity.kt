package app.junhyounglee.wikipages.app

import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import app.junhyounglee.wikipages.R

/**
 * [공통 요구사항]
 *  • 개발 툴로는 Android Studio를 사용한다.
 *  • gradle 프로젝트로 구성한다.
 *  • 최소 minsdkversion은 14로한다.
 *  • 오픈소스 등을 사용하지 않고 Android에서 제공하는 API만을 사용하여 구현한다.
 *  • 주어진 디자인 가이드 화면대로 화면 레이아웃을 작성한다.
 *  • proguard를 적용하고, apk 파일(1.0.0 버전)도 패키징하여 프로젝트 소스와 함께 별도로 제출한다.
 *
 * [1 단계]
 * Android 앱 개발을 할 때 서버로부터 데이터를 불러와서 화면에 표시하거나, 사용자가 수정한 내용을 서버에 저장하는 등, 해당
 * 서비스 및 도메인 서버와의 통신은 거의 필수적으로 필요한 작업이다. 이렇게, 앱 개발 시 꼭 필요한 서버와의 통신 모듈을 공통
 * 라이브러리 형태로 제공하여 사용할 수 있도록 구현한다.
 *
 * 요구 사항
 *  • HttpURLConnection, HttpsURLConnection 기반으로 구현한다. (O)
 *  • http 방식이든 https 방식이든, 모두 호출될 수 있도록 작성한다. (O)
 *  • 요청 메서드 타입은 GET, POST, PUT, DELETE를 지원하도록 한다.
 *  • Request Header 값을 설정할 수 있어야 한다. (O)
 *  • Response Data를 어떤 형식의 타입으로도 요청할 수 있어야 한다.
 *  • Request Body 전달 시 어떤 형식으로든 서버에 전달, 요청할 수 있어야 한다.
 *  • API 접속 timeout 시간을 설정할 수 있도록 작성한다. (O)
 *  • jar로 패키징될 수 있도록 gradle 빌드 스크립트도 작성한다. (O)
 *  • 위의 요구 사항에 대해서 테스트 케이스를 작성한다. (~)
 *
 * [2 단계]
 * 1 단계에서 작성한 공통 통신 모듈을 사용하여, 아래 주어진 API를 사용하여 아래 요구 사항을 구현한다.
 *  - 검색 상세 페이지: https://en.wikipedia.org/api/rest_v1/page/html/{검색어} (O)
 *  - 요약 정보 API: https://en.wikipedia.org/api/rest_v1/page/summary/{검색어} (O)
 *  - 연관 검색 API: https://en.wikipedia.org/api/rest_v1/page/related/{검색어} (O)
 *
 * 요구 사항
 *  • SwipeRefreshLayout을 사용하여 ListView를 새로 고침하는 pull to refresh ListView 커스텀 뷰를 구현한다. (O)
 *  • 검색어를 입력 후 검색을 시도하면 “검색 결과 Activity”를 구성한다. (O)
 *  • “요약 정보 API” 를 이용하여 가져온 데이터를 이용하여 ListView의 header view 를 구성한다. (O)
 *  • “연관 검색 API” 를 이용하여 가져온 데이터를 이용하여 ListView의 각 항목을 구성한다. (O)
 *  • ListView의 header view를 클릭하면 “검색 상세 페이지” URL을 이용하여 WebView를 내장한 Activity를 새롭게 띄워 해당 웹 페이지를 표시한다. (O)
 *  • ListView의 각 항목을 클릭하면 해당 검색어를 이용한 새로운 “검색 결과 Activity”를 띄운다. (O)
 */
class MainActivity : AppCompatActivity() {

  private lateinit var appBarConfiguration: AppBarConfiguration

  private val navController by lazy {
    (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    appBarConfiguration = AppBarConfiguration(navController.graph)
    findViewById<Toolbar>(R.id.toolbar)
        .setupWithNavController(navController, appBarConfiguration)
  }

  internal fun updateToolbarTitle(title: String) {
    findViewById<Toolbar>(R.id.toolbar).title = title
  }
}