package com.sports.list.splash

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sports.list.list.MainActivity
import com.sports.list.R
import com.sports.list.web.WebViewActivity
import com.sports.list.extantions.getViewModel
import com.sports.list.extantions.safeObserve

class SplashActivity: AppCompatActivity(R.layout.activity_splash) {

    private val vm by lazy { getViewModel<SplashViewModel>(SplashFactory(getString(R.string.url))) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm.onCreate()
        onVmSubscribe()
    }

    private fun onGetDatabase() {
        FirebaseDatabase.getInstance().reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("Database", "Get Data url ${snapshot.value}")
                Log.d("Database", "Get Data stub ${snapshot.value}")
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun onVmSubscribe() {
        vm.showWeb.safeObserve(this) { showNextStep(WebViewActivity.getInstance(this, it)) }
        vm.showStub.safeObserve(this) { showNextStep(MainActivity.getIntent(this)) }
    }

    private fun showNextStep(intent: Intent) {
        startActivity(intent)
        finish()
    }
}
