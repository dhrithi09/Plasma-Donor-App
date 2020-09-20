package project.dscjss.plasmadonor.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import project.dscjss.plasmadonor.R
import java.lang.Thread.sleep

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sleep(2000);

        startActivity(Intent(applicationContext , LoginActivity::class.java))
    }
}