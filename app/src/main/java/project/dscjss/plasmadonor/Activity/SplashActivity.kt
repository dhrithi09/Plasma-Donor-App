package project.dscjss.plasmadonor.Activity

import android.content.Intent
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
            startActivity(Intent(applicationContext , LoginActivity::class.java))
            finish()
        }, 2000)

    }
}