package app.junhyounglee.wikipages.app.wikipage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import app.junhyounglee.wikipages.databinding.FragmentWikiPageBinding

/**
 * 위키 검색어 웹페이지 화면
 */
class WikiPageFragment : Fragment() {

  private val args: WikiPageFragmentArgs by navArgs()

  private var webView: WebView? = null

  private var isWebViewAvailable = false


  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    return FragmentWikiPageBinding.inflate(inflater, container, false).root.apply {
      webViewClient = WebViewClient()

      @SuppressLint("SetJavaScriptEnabled")
      settings.javaScriptEnabled = true

      this@WikiPageFragment.webView = this
      this@WikiPageFragment.isWebViewAvailable = true

      loadUrl(args.url)
    }
  }

  override fun onResume() {
    super.onResume()
    webView?.onResume()
  }

  override fun onPause() {
    super.onPause()
    webView?.onPause()
  }

  override fun onDestroyView() {
    isWebViewAvailable = true
    super.onDestroyView()
  }

  override fun onDestroy() {
    webView?.destroy()
    webView = null
    super.onDestroy()
  }

}