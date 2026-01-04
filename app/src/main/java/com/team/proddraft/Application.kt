package com.team.proddraft

import android.app.Application
import android.content.Intent
import android.util.Log
import com.team.proddraft.presentation.CrashActivity
import dagger.hilt.android.HiltAndroidApp
import kotlin.system.exitProcess

@HiltAndroidApp
class Application : Application() {

    override fun onCreate() {
        super.onCreate()

        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            Log.e("CRASH_CATCHER", throwable.toString())
            val intent = Intent(this, CrashActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intent.putExtra("error_message", throwable.localizedMessage)
            startActivity(intent)
            android.os.Process.killProcess(android.os.Process.myPid())
            exitProcess(10)
        }
    }
}