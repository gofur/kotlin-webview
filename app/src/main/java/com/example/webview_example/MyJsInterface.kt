package com.example.webview_example.js

import android.app.Activity
import android.webkit.JavascriptInterface


class MyJsInterface(
    private val activity: Activity
) {

    private val listeners = mutableSetOf<JavaScriptMessageListener>()

    fun addListener(listener: JavaScriptMessageListener) {
        listeners.add(listener)
    }

    @JavascriptInterface
    fun loginAction(message: String) {
        // jalankan fungsi login
        activity.runOnUiThread {
            listeners.forEach {
                it.onMessage(message)
            }
        }
    }

    @JavascriptInterface
    fun logoutAction(message: String){

        // jalankan fungsi logout
        activity.runOnUiThread {
            listeners.forEach {
                it.onMessage(message)
            }
        }
    }
}
