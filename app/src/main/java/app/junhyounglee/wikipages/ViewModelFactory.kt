package app.junhyounglee.wikipages

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import app.junhyounglee.libcore.net.HttpClient
import app.junhyounglee.wikipages.app.search.SearchViewModel

/**
 * AndroidApp ViewModel factory class
 */
class ViewModelFactory internal constructor(
    private val app: AndroidApp,
    owner: SavedStateRegistryOwner,
    defaultArgs: Bundle? = null
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

  @Suppress("UNCHECKED_CAST")
  override fun <T : ViewModel> create(
      key: String,
      modelClass: Class<T>,
      handle: SavedStateHandle
  ): T = with(modelClass) {
    when {
      isAssignableFrom(SearchViewModel::class.java) -> {
        SearchViewModel(ServiceLocator.provideSearchUseCase(app.wikiDataSource))
      }

      else ->
        throw IllegalArgumentException("ViewModel class(${modelClass.name}) is not supported from ViewModelFactory.")
    }
  } as T
}
