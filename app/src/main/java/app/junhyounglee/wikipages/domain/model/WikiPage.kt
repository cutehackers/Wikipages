package app.junhyounglee.wikipages.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONException
import org.json.JSONObject

/**
 * 연관 검색 페이지 모델
 */
@Parcelize
data class WikiPage(
    val title: String,
    val contents: String,
    val thumb: WikiImage? = null
) : Parcelable {

  companion object {
    @Throws(JSONException::class)
    fun fromArray(json: String): List<WikiPage> {
      val data = JSONObject(json).getJSONArray("pages")
      val pages = mutableListOf<WikiPage>()

      for (i in 0 until data.length()) {
        val page = data.getJSONObject(i)
        pages.add(WikiPage(
            page.getString("title"),
            page.getString("extract"),
            page.optJSONObject("thumbnail")?.let {
              WikiImage(
                  it.getString("source"),
                  it.getInt("width"),
                  it.getInt("height")
              )
            }
        ))
      }

      return pages
    }
  }

}