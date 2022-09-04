package com.yachtcru.yachtcruhoursofrest

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    internal var url  = "https://www.yachtcru.com/hours-of-rest-promo-2/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        webInst()
        onClickBack()
        saveData()
    }

    private fun webInst(){
        webView2.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                progressBar!!.visibility = View.VISIBLE
                view?.loadUrl(url)
                return true
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar!!.visibility = View.GONE
            }
        }
    }

    private fun saveData(){
        val webSettings = webView2!!.settings
        webSettings.javaScriptEnabled = true
        webSettings.blockNetworkImage = false
        webSettings.loadsImagesAutomatically = true
        webSettings.useWideViewPort = true
        webSettings.loadWithOverviewMode = true
        webSettings.javaScriptCanOpenWindowsAutomatically = true
        webSettings.domStorageEnabled = true
        webSettings.setSupportMultipleWindows(true)
        webSettings.loadWithOverviewMode = true
        webSettings.allowContentAccess = true
        webSettings.setGeolocationEnabled(true)
        webSettings.allowFileAccess = true
        webView2.fitsSystemWindows = true
        webView2!!.loadUrl(url)
    }

    private fun onClickBack() {
        back_icon_reg.setOnClickListener {
            moveIntent()
        }
        back_text_reg.setOnClickListener {
            moveIntent()
        }
    }

    private fun moveIntent() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        if (webView2!!.canGoBack()){
            webView2!!.goBack()
        } else {
            super.onBackPressed()
        }

    }

}