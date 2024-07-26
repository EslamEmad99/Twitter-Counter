package com.halan.twitter_counter.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.halan.twitter_counter.ui.main.MainActivity

/**
 * The SplashActivity serves as the entry point of the application.
 * It is typically used to display a logo or welcome screen while the app initializes necessary components
 * such as loading data, setting up dependencies, or checking user authentication status.
 * The splash screen enhances the user experience by providing a smooth transition into the main part of the app.
 * Once initialization is complete, the SplashActivity navigates to the main activity of the app.
 * **/
@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MainActivity::class.java))
    }
}
