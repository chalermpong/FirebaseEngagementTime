package com.chalermpong.firebaseengagementtime

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAnalytics = Firebase.analytics

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FAA", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Log.d("FAA", "token: $token")
        })
    }

    lateinit var mResumeTime: Calendar
    var mLastEngagementTime: Long = 0
    var mStopHandler = false
    val mHandler = Handler(Looper.getMainLooper())
    var runnable: Runnable = object : Runnable {
        override fun run() {
            // do your stuff - don't create a new runnable here!
            if (!mStopHandler) {
                val tv = findViewById<TextView>(R.id.tvMain)

                tv.text = "${(Calendar.getInstance().timeInMillis - mResumeTime.timeInMillis) / 1000} last=$mLastEngagementTime"
                mHandler.postDelayed(this, 100)
            }
        }
    }

    override fun onResume() {
        super.onResume()

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, "main")
            param(FirebaseAnalytics.Param.SCREEN_CLASS, "MainActivity1")
        }
        mResumeTime = Calendar.getInstance()
        mStopHandler = false
        mHandler.post(runnable)
    }

    override fun onPause() {
        super.onPause()
        mStopHandler = true
        mLastEngagementTime = Calendar.getInstance().timeInMillis - mResumeTime.timeInMillis
    }
}