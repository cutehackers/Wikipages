package app.junhyounglee.wikipages.extension

import android.widget.Toast
import androidx.fragment.app.Fragment
import app.junhyounglee.wikipages.AndroidApp
import app.junhyounglee.wikipages.ViewModelFactory

/**
 * Extensions for Fragment class
 */

fun Fragment.getViewModelFactory(): ViewModelFactory {
  val context = requireContext().applicationContext as AndroidApp
  return ViewModelFactory(context, this)
}

fun Fragment.toast(text: String, isShort: Boolean = true) {
  context?.applicationContext?.also {
    Toast.makeText(it, text, if (isShort) Toast.LENGTH_SHORT else Toast.LENGTH_LONG).show()
  }
}
