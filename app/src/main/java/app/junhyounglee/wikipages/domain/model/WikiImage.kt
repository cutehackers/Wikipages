package app.junhyounglee.wikipages.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WikiImage(
    val url: String,
    val width: Int,
    val height: Int
) : Parcelable
