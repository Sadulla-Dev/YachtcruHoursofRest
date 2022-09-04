package com.yachtcru.yachtcruhoursofrest

import android.app.Activity
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.yachtcru.yachtcruhoursofrest.helper.Constant
import com.yachtcru.yachtcruhoursofrest.helper.PrefHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    internal var url = "https://personnel-data.com/login"
    internal var fleet = "https://personnel-data.com/fleet"
    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prefHelper = PrefHelper(this)

        webInst()
    }

    private fun webInst(){
        val webSettings = webView1!!.settings
        webView1.webViewClient = object : WebViewClient() {
            override fun doUpdateVisitedHistory(view: WebView?, url: String?, isReload: Boolean) {
                super.doUpdateVisitedHistory(view, url, isReload)
                if (view?.url!!.contains("fleet")) {
                    prefHelper.put(Constant.PREF_IS_LOGIN, true)
                    layout_l.visibility = View.GONE
                }
                if (view?.url!!.contains("logout")) {
                    showMessage("Logout")
                    moveIntent()
                    prefHelper.clear()

                } else if (view?.url == fleet) {
                    if (!prefHelper.getBoolean(Constant.PREF_DIALOG)) {
                        showDialog()
                    }
                }
                if (view?.url!!.contains("login")) {
                    layout_l.visibility = View.VISIBLE
                    onClickBack()
                }
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar!!.visibility = View.GONE
            }

            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                progressBar!!.visibility = View.VISIBLE
                view?.loadUrl(url)
                return true
            }
        }
        webSettings.javaScriptEnabled = true
        webView1.loadUrl(url)
    }

    private fun onClickBack() {
        back_icon.setOnClickListener {
            unSaveSettings()
            moveIntent()
        }
        back_text.setOnClickListener {
            unSaveSettings()
            moveIntent()
        }
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(this@MainActivity)
        builder.setTitle("Do you want to save your login info?")
        builder.setMessage("")
        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            showMessage("Saved")
            saveSettings()
            prefHelper.put(Constant.PREF_DIALOG, true)
        }
        builder.setNegativeButton(android.R.string.no) { dialog, which ->
            unSaveSettings()
            prefHelper.clear()
        }
        builder.show()

    }

    private fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    private fun moveIntent() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun saveSettings() {
        val webSettings = webView1!!.settings
        webView1.clearCache(false)
        webSettings.saveFormData = true
        webSettings.savePassword = true
        webSettings.databaseEnabled = true
        webSettings.setAppCacheEnabled(true)
        webSettings.setAppCacheEnabled(true)
        CookieManager.getInstance().getCookie(url)
    }

    private fun unSaveSettings() {
        val webSettings = webView1!!.settings
        webView1.clearCache(true)
        webView1.clearHistory()
        webView1.clearFormData()
        webView1.clearSslPreferences()
        webView1.clearMatches()
        webSettings.setAppCacheEnabled(false)
        CookieManager.getInstance().removeAllCookies(null)
        CookieManager.getInstance().flush()
        WebStorage.getInstance().deleteAllData()
        prefHelper.clear()
    }


}