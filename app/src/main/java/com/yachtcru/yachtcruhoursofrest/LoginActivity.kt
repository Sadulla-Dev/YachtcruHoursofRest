package com.yachtcru.yachtcruhoursofrest

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.CookieManager
import android.widget.Button
import com.yachtcru.yachtcruhoursofrest.helper.Constant
import com.yachtcru.yachtcruhoursofrest.helper.PrefHelper
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    lateinit var prefHelper: PrefHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        prefHelper = PrefHelper(this)

        onClickBtn()
    }

    private fun onClickBtn(){
        loginBtn.setOnClickListener {
            moveIntent()
        }

        regsitrBtn.setOnClickListener {
            moveIntentReg()
        }
    }

    override fun onStart() {
        super.onStart()
        if (prefHelper.getBoolean( Constant.PREF_IS_LOGIN )) {
            moveIntent()
        }
    }

    private fun moveIntent(){
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun moveIntentReg(){
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

}