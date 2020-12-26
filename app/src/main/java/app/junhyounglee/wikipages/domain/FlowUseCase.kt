package app.junhyounglee.wikipages.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in PARAMETER, RESULT>(private val dispatcher: CoroutineDispatcher) {

  @OptIn(ExperimentalCoroutinesApi::class)
  operator fun invoke(parameters: PARAMETER): Flow<Result<RESULT>> = execute(parameters)
      .catch { e -> emit(Result.Error(Exception(e))) }
      .flowOn(dispatcher)

  protected abstract fun execute(parameters: PARAMETER): Flow<Result<RESULT>>
}
