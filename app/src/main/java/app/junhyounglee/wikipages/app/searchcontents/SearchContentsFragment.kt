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
import app.junhyounglee.wikipages.app.MainActivity
import app.junhyounglee.wikipages.app.search.SearchViewModel
import app.junhyounglee.wikipages.common.LogAware
import app.junhyounglee.wikipages.common.d
import app.junhyounglee.wikipages.common.e
import app.junhyounglee.wikipages.databinding.FragmentSearchContentsBinding
import app.junhyounglee.wikipages.databinding.WikiSummaryLayoutBinding
import app.junhyounglee.wikipages.domain.Result
import app.junhyounglee.wikipages.domain.model.WikiContents
import app.junhyounglee.wikipages.domain.model.WikiPage
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
    updateToolbarTitle(args.contents.query)
    addSummaryView()

    binding.pages.adapter = SearchContentsAdapter(requireContext(), args.contents.pages).also {
      adapter = it
    }
    binding.pages.setOnItemClickListener { parent, view, position, id ->

      onWikiPageItemClick(adapter.getItem(position - 1) as WikiPage)
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

    // 새로운 화면에서 검색 결과
    viewModel.newSearchContents.observe(viewLifecycleOwner) {
      binding.refresher.isRefreshing = false

      when (it) {
        is Result.Success<WikiContents> -> {
          navigateToSelfScreen(it.data)
        }
        is Result.Error -> {
          toast("Error while searching from new screen ...")
          e("SearchContents> failed to search contents!", it.exception)
        }
      }
    }

//    viewModel.isSearching.observe(viewLifecycleOwner) {
//      binding.refresher.isRefreshing = it
//    }
  }

  private fun updateToolbarTitle(title: String) {
    if (activity is MainActivity) {
      (activity as MainActivity).updateToolbarTitle(title)
    }
  }

  private fun addSummaryView() {
    WikiSummaryLayoutBinding.inflate(layoutInflater, null, false).apply {
      val summary = args.contents.summary
      title.text = summary.title
      contents.text = fromHtml(summary.texts)

      binding.pages.addHeaderView(root)

      // events
      root.setOnClickListener { navigateToWikiPage() }
    }
  }

  private fun fromHtml(texts: String): Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(texts, Html.FROM_HTML_MODE_LEGACY)
  } else {
    Html.fromHtml(texts)
  }

  /**
   * 위키 검색어 웹페이지 화면으로 이동
   */
  private fun navigateToWikiPage() {
    val query = args.contents.query
    if (query.isNotEmpty()) {
      val url = "${getString(R.string.wikipedia_base_url)}/rest_v1/page/html/$query"
      val direction = SearchContentsFragmentDirections.actionSearchContentsFragmentToWikiPageFragment(url)
      findNavController().navigate(direction)
    }
  }

  /**
   * 위키 검색어 페이지 아이템 클릭
   */
  private fun onWikiPageItemClick(page: WikiPage) {
    if (!binding.refresher.isRefreshing) {
      d("navigateToSelfScreen> searching: ${page.title}")
      viewModel.searchFromNewScreen(page.title)
      binding.refresher.isRefreshing = true
    }
  }

  /**
   * 새로운 화면에서 검색 결과를 보여준다
   */
  private fun navigateToSelfScreen(contents: WikiContents) {
    val direction = SearchContentsFragmentDirections.actionSearchContentsFragmentSelf(contents)
    findNavController().navigate(direction)
  }
}
