package project.dscjss.plasmadonor.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import project.dscjss.plasmadonor.Fragment.ProfileFragment
import project.dscjss.plasmadonor.Interface.FragmentChangeInterface
import project.dscjss.plasmadonor.R

class MainActivity : AppCompatActivity(), FragmentChangeInterface {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFrame, ProfileFragment())
            .commit()
    }

    override fun changeFragment(fragment: Fragment) {

        if (supportFragmentManager.backStackEntryCount == 0) {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.mainFrame, fragment).commit()
        }
        else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrame, fragment).commit()
        }

    }
}