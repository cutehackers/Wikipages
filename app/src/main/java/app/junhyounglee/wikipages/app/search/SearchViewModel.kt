package app.junhyounglee.wikipages.app.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import app.junhyounglee.wikipages.common.SingleLiveData
import app.junhyounglee.wikipages.domain.Result
import app.junhyounglee.wikipages.domain.model.SearchParameter
import app.junhyounglee.wikipages.domain.model.WikiContents
import app.junhyounglee.wikipages.domain.usecase.SearchUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

/**
 * 검색화면 뷰모델
 */
class SearchViewModel(
    private val searchUseCase: SearchUseCase
) : ViewModel() {

  private var searchQuery: String = ""

  private var searchJob: Job? = null

  private val _searchContents = SingleLiveData<Result<WikiContents>>()
  val searchContents: LiveData<Result<WikiContents>> = _searchContents

  val isLoading: LiveData<Boolean> = _searchContents.map {
    it == Result.Loading
  }

  /**
   * 검색 쿼리 변경에 위키 검색
   */
  fun search(query: String) {
    // 1자 이상의 검색어만 허용하도록 한다
    val newQuery = query.trim()
    //if (newQuery != searchQuery && newQuery.length > 1) {
    if (newQuery.length > 1) {
      searchQuery = newQuery
      Log.d("Search>", "query changed: $newQuery")

      // 검색
      doSearch(searchQuery)
    }
  }

  /**
   * 위키 검색 로직 시작
   */
  private fun doSearch(searchQuery: String) {
    searchJob?.cancel()

    if (searchQuery.isEmpty()) {
      clearSearchQuery()
      return
    }

    searchJob = viewModelScope.launch {
      // 사용자가 검색어를 빠르게 타이핑 할 수 있으므로 잠시 검색루틴을 멈춘고, 해당 작업이 취소되기를 대기하여 API 호출을
      // 효율적으로 호출한다.
      delay(500)

      searchUseCase(SearchParameter(searchQuery))
          .onStart { Result.Loading }
          .collect { result: Result<WikiContents> ->
            if (result is Result.Error) {
              Log.e("Search>", "error while searching with: $searchQuery", result.exception)
            } else if (result is Result.Success) {
              _searchContents.value = result
              Log.d("Search>", "search: $result")
            }
          }
    }
  }

  /**
   * 쿼리 스트링을 초기화 한다.
   */
  fun clearSearchQuery() {
    searchQuery = ""
    //_wiki.value = Result.Success(EmptySearchContents)
  }

}
