package app.junhyounglee.wikipages.app.searchcontents

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.junhyounglee.wikipages.R
import app.junhyounglee.wikipages.app.search.SearchViewModel
import app.junhyounglee.wikipages.common.LogAware
import app.junhyounglee.wikipages.common.d
import app.junhyounglee.wikipages.common.e
import app.junhyounglee.wikipages.databinding.FragmentSearchContentsBinding
import app.junhyounglee.wikipages.databinding.WikiSummaryLayoutBinding
import app.junhyounglee.wikipages.domain.Result
import app.junhyounglee.wikipages.domain.model.WikiContents
import app.junhyounglee.wikipages.extension.getViewModelFactory
import app.junhyounglee.wikipages.extension.toast

/**
 * 위키 페이지 검색 결과 화면
 */
class SearchContentsFragment : Fragment(), LogAware {

  private val args: SearchContentsFragmentArgs by navArgs()

  private val viewModel: SearchViewModel by activityViewModels { getViewModelFactory() }

  private lateinit var binding: FragmentSearchContentsBinding
  private lateinit var adapter: SearchContentsAdapter

  override fun onCreateView(
      inflater: LayoutInflater,
      container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View = FragmentSearchContentsBinding.inflate(inflater, container, false).run {
    binding = this
    root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUpView()
  }

  private fun setUpView() {
    addSummaryView()

    binding.pages.adapter = SearchContentsAdapter(requireContext(), args.contents.pages).also {
      adapter = it
    }
    binding.refresher.setOnRefreshListener {
      // 검색 시작
      viewModel.search(args.contents.query)
    }

    // 검색 결과
    viewModel.searchContents.observe(viewLifecycleOwner) {
      d("SearchContents> search events: $it")
      when (it) {
        is Result.Success<WikiContents> -> {
          adapter.refresh(it.data.pages)
          binding.refresher.isRefreshing = false
        }
        is Result.Error -> {
          binding.refresher.isRefreshing = false
          toast("Error while searching...")
          e("SearchContents> failed to search contents!", it.exception)
        }
      }
    }

    viewModel.isLoading.observe(viewLifecycleOwner) {
      binding.refresher.isRefreshing = it
    }
  }

  private fun addSummaryView() {
    WikiSummaryLayoutBinding.inflate(layoutInflater, null, false).apply {
      val summary = args.contents.summary
      title.text = summary.title
      contents.text = fromHtml(summary.texts)

      binding.pages.addHeaderView(root)
    }
  }

  private fun fromHtml(texts: String): Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(texts, Html.FROM_HTML_MODE_LEGACY)
  } else {
    Html.fromHtml(texts)
  }

}
