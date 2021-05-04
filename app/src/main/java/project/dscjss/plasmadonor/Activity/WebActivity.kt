package project.dscjss.plasmadonor.Activity

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web.*
import project.dscjss.plasmadonor.R


class WebActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web)
        val url = intent.getStringExtra("urlLink")
        println(url)
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url!!)
        val webSetting = webView.settings
        webSetting.javaScriptEnabled = true
        webView.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_INSET;
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) webView.goBack()
        else {
            super.onBackPressed()
        }
    }
}
