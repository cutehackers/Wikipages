package app.junhyounglee.wikipages.app.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import app.junhyounglee.wikipages.common.LogAware
import app.junhyounglee.wikipages.common.d
import app.junhyounglee.wikipages.common.e
import app.junhyounglee.wikipages.databinding.FragmentSearchBinding
import app.junhyounglee.wikipages.domain.Result
import app.junhyounglee.wikipages.domain.model.WikiContents
import app.junhyounglee.wikipages.extension.getViewModelFactory
import app.junhyounglee.wikipages.extension.toast

/**
 * 기본 검색 화면
 */
class SearchFragment : Fragment(), LogAware {

  private val viewModel: SearchViewModel by activityViewModels { getViewModelFactory() }

  private lateinit var binding: FragmentSearchBinding

  private var hasQueryTextFocus = false

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View = FragmentSearchBinding.inflate(inflater, container, false).run {
    binding = this
    root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setUpView()
  }

  private fun setUpView() {
    // 검색 창
    binding.searchView.apply {
      setOnQueryTextFocusChangeListener { _, hasFocus ->
        hasQueryTextFocus = hasFocus
      }
      setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
          hideSoftInput(this@apply)

          if (viewModel.search(query)) {
            setLoadingIndicator(true)
          }
          return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
          // 검색 시작
//          if (hasQueryTextFocus) {
//            viewModel.search(newText)
//          }
          return true
        }
      })
    }

    // 검색 결과
    viewModel.searchContents.observe(viewLifecycleOwner) {
      setLoadingIndicator(false)

      when (it) {
        is Result.Success<WikiContents> -> {
          navigateToSearchContents(it.data)
        }
        is Result.Error -> {
          toast("Error while searching...")
          e("Search> failed to search contents!", it.exception)
        }
      }
    }
  }

  private fun hideSoftInput(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }

  /**
   * 검색 결과화면으로 이동
   */
  private fun navigateToSearchContents(contents: WikiContents) {
    if (contents.pages.isEmpty()) {
      toast("Not found!")
      return
    }

    d("navigateToSearchContents> content")

    val direction = SearchFragmentDirections.actionSearchFragmentToSearchContentsFragment(contents)
    findNavController().navigate(direction)
  }

  private fun setLoadingIndicator(visible: Boolean) {
    binding.progress.isVisible = visible
  }

}
