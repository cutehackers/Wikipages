package app.junhyounglee.wikipages.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Wiki 검색에 대한 결과
 *  summary
 *  pages
 */
@Parcelize
data class WikiContents(
    val query: String,
    val summary: WikiSummary,
    val pages: List<WikiPage>
) : Parcelable