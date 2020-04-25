package com.sports.list.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.sports.list.list.MainActivity
import com.sports.list.R
import com.sports.list.WebViewActivity
import com.sports.list.extantions.getViewModel
import com.sports.list.extantions.safeObserve

class SplashActivity: AppCompatActivity(R.layout.activity_splash) {

    private val vm by lazy { getViewModel<SplashViewModel>(SplashFactory(getString(R.string.url))) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.onCreate()
        onVmSubscribe()
    }

    private fun onVmSubscribe() {
        vm.showWeb.safeObserve(this) { showNextStep(WebViewActivity.getInstance(this, it)) }
        vm.showStub.safeObserve(this) { showNextStep(MainActivity.getIntent(this)) }
    }

    private fun showNextStep(intent: Intent) {
        startActivity(intent)
    }
}
