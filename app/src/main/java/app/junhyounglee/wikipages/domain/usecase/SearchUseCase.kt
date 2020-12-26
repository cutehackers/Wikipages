package app.junhyounglee.wikipages.domain.usecase

import app.junhyounglee.wikipages.data.WikiDataSource
import app.junhyounglee.wikipages.domain.FlowUseCase
import app.junhyounglee.wikipages.domain.Result
import app.junhyounglee.wikipages.domain.model.SearchParameter
import app.junhyounglee.wikipages.domain.model.WikiContents
import app.junhyounglee.wikipages.domain.model.WikiPage
import app.junhyounglee.wikipages.domain.model.WikiSummary
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

/**
 * 위키 검색 UseCase
 */
class SearchUseCase(
    dispatcher: CoroutineDispatcher,
    private val repository: WikiDataSource
) : FlowUseCase<SearchParameter, WikiContents>(dispatcher) {

  override fun execute(parameters: SearchParameter): Flow<Result<WikiContents>> {
    val query = parameters.query

    return repository.run {
      getSearchSummary(query).zip(getSearchPages(query)) { summary: WikiSummary, pages: List<WikiPage> ->
        Result.Success(WikiContents(query, summary, pages))
      }
    }
  }

}