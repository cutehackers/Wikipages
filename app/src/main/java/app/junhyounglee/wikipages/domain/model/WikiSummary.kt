package app.junhyounglee.wikipages.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONException
import org.json.JSONObject

/**
 * 요약 정보 모델
 */
@Parcelize
data class WikiSummary(
    val title: String,
    val texts: String,
    val thumb: WikiImage? = null
): Parcelable {

  companion object {
    val EMPTY = WikiSummary("", "")

    @Throws(JSONException::class)
    fun from(json: String): WikiSummary {
      val data = JSONObject(json)
      return WikiSummary(
          data.getString("displaytitle"),
          data.getString("extract_html"),
          data.optJSONObject("thumbnail")?.let {
            WikiImage(
                it.getString("source"),
                it.getInt("width"),
                it.getInt("height")
            )
          }
      )
    }
  }

}
