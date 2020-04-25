package com.sports.list.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.sports.list.Constants
import com.sports.list.extantions.Event
import com.sports.list.extantions.call

class SplashViewModel(private var urlString: String): ViewModel() {

    val showWeb = MutableLiveData<String>()
    val showStub = MutableLiveData<Event<Unit>>()

    fun onCreate() {
        getDatabase(FirebaseDatabase.getInstance())
    }

    private fun getDatabase(firebaseDatabase: FirebaseDatabase) {
        firebaseDatabase.reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                urlString = snapshot.child(Constants.URL).value as? String ?: ""
                val stub = snapshot.child(Constants.IS_STUB).value as? Boolean ?: true
                onDataBaseReceived(urlString, stub)
            }

            override fun onCancelled(p0: DatabaseError) {
                Log.e("Message", p0.message)
                Log.e("Detail", p0.details)
                Log.e("Code", "${p0.code}")
                showStub.call()
            }
        })
    }

    private fun onDataBaseReceived(urlString: String, stub: Boolean) {
        if (urlString.isNotEmpty() && !stub) {
            showWeb.value = urlString
        } else {
            showStub.call()
        }
    }
}

class SplashFactory(private val urlString: String): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return SplashViewModel(urlString) as T
    }
}
