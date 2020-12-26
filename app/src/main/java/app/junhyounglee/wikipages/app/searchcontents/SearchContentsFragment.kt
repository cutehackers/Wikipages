package app.junhyounglee.wikipages.app.searchcontents

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.junhyounglee.wikipages.R
import app.junhyounglee.wikipages.databinding.FragmentSearchContentsBinding

/**
 * 위키 페이지 검색 결과 화면
 */
class SearchContentsFragment : Fragment() {

  private val args: SearchContentsFragmentArgs by navArgs()

  private lateinit var binding: FragmentSearchContentsBinding

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
  ): View = FragmentSearchContentsBinding.inflate(inflater, container, false).run {
    binding = this
    button.text = args.contents.summary.title
    root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }
}