package app.junhyounglee.wikipages

import android.app.Application
import app.junhyounglee.libcore.net.HttpClient
import app.junhyounglee.wikipages.data.WikiDataSource

class AndroidApp : Application() {

  internal val wikiDataSource: WikiDataSource by lazy {
    ServiceLocator.provideWikiRepository(this)
  }

  internal val httpClient: HttpClient = ServiceLocator.provideHttpClient()
}
