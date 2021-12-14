package com.chalermpong.firebaseengagementtime

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MessagingService: FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("FAA", "onNewToken $p0")
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        Log.d("FAA", "onMessageReceived $p0")
    }
}