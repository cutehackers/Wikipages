package app.junhyounglee.wikipages.app.search

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import app.junhyounglee.wikipages.R
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

  private val viewModel: SearchViewModel by viewModels { getViewModelFactory() }

  private lateinit var binding: FragmentSearchBinding

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
      setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String): Boolean {
          hideSoftInput(this@apply)
          return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
          viewModel.onSearchQueryChanged(newText)
          return true
        }
      })
      requestFocus()
    }

    viewModel.searchContents.observe(viewLifecycleOwner) {
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

    viewModel.isLoading.observe(viewLifecycleOwner) {
      setLoadingIndicator(it)
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
