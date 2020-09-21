package project.dscjss.plasmadonor.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import project.dscjss.plasmadonor.R

class SplashActivity : AppCompatActivity() {

    val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler.postDelayed(Runnable {
            startActivity(Intent(applicationContext , UserLoginActivity::class.java))
            finish()
        }, 2000)

    }
}