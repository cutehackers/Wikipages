package app.junhyounglee.wikipages.app.searchcontents

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import app.junhyounglee.wikipages.R
import app.junhyounglee.wikipages.domain.model.WikiPage

/**
 * 위키 검새 결과 어댑터
 *  - 서머리
 *  - 페이지
 */
class SearchContentsAdapter(
    context: Context,
    items: List<WikiPage>
) : ArrayAdapter<WikiPage>(context, -1, items) {

  override fun hasStableIds(): Boolean = true

  override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
    val view = convertView
        ?: LayoutInflater.from(context).inflate(R.layout.wiki_page_item_layout, parent, false)

    // bind view
    val image = view.findViewById<ImageView>(R.id.image)
    val title = view.findViewById<TextView>(R.id.title)
    val contents = view.findViewById<TextView>(R.id.contents)

    val item = getItem(position) as WikiPage
    image.isVisible = item.thumb != null
    title.text = item.title
    contents.text = item.contents

    return view
  }

  fun refresh(items: List<WikiPage>) {
    clear()
    addAll(items)
    notifyDataSetChanged()
  }
}