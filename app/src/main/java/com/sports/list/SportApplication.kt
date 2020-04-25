package com.sports.list

import android.app.Application
import com.google.firebase.FirebaseApp

class SportApplication: Application() {

    override fun onCreate() {
        FirebaseApp.initializeApp(this)
        super.onCreate()
    }
}
