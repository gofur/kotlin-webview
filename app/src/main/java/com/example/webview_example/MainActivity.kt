package com.example.webview_example

import android.graphics.Color
import android.os.Bundle
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.example.webview_example.js.JavaScriptMessageListener
import com.example.webview_example.js.MyJsInterface

class MainActivity : ComponentActivity(), JavaScriptMessageListener {

    private lateinit var webView: WebView
    private lateinit var jsChannel: MyJsInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ EDGE-TO-EDGE (XML WAY)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // ✅ STATUS BAR TRANSPARENT
        window.statusBarColor = Color.TRANSPARENT

        // ✅ DARK ICONS (false kalau background gelap)
        WindowCompat.getInsetsController(window, window.decorView)
            .isAppearanceLightStatusBars = true

        setContentView(R.layout.activity_main)

        val root = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.root)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        webView = findViewById(R.id.webview)

        // ✅ APPLY SAFE AREA
        ViewCompat.setOnApplyWindowInsetsListener(root) { _, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())

            toolbar.updatePadding(top = bars.top)
            insets
        }

        // ===== JS CHANNEL (Flutter-like) =====
        jsChannel = MyJsInterface(this).apply {
            addListener(this@MainActivity)
        }

        webView.apply {
            settings.javaScriptEnabled = true
            settings.domStorageEnabled = true
            webChromeClient = WebChromeClient()
            addJavascriptInterface(jsChannel, "Android")
            loadUrl("https://nativechan.manot40.workers.dev/")
//            loadUrl("file:///android_asset/index.html")
        }
    }

    // ===== FLUTTER-LIKE CALLBACK =====
    override fun onMessage(message: String) {
        when {
            message.startsWith("LOGIN") -> {
                // handle login
                Toast.makeText(this, "From Login: $message", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
