package project.dscjss.plasmadonor.Activity

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import project.dscjss.plasmadonor.R

class SplashActivity : AppCompatActivity() {

    var handler = Handler()
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firebaseAuth = FirebaseAuth.getInstance()

        handler.postDelayed(Runnable {
            if (firebaseAuth.currentUser != null) {
                startActivity(Intent(applicationContext , MainActivity::class.java))
            }
            else {
                startActivity(Intent(applicationContext, UserLoginActivity::class.java))
            }
            finish()
        }, 3500)

    }
}