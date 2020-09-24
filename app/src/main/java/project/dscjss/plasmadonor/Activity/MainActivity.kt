package project.dscjss.plasmadonor.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import project.dscjss.plasmadonor.Fragment.ProfileFragment
import project.dscjss.plasmadonor.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, ProfileFragment())
            .commit()
    }
}