package project.dscjss.plasmadonor.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import project.dscjss.plasmadonor.R

class LoginActivity : AppCompatActivity() {

    private lateinit var proceed : AppCompatButton
    private lateinit var phone : AppCompatEditText
    private lateinit var phoneNo : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initViews()

        proceed.setOnClickListener {
            if(checkInput()) {
                initiateLogin()
            }
        }

    }

    private fun initiateLogin(){

        startActivity(Intent(applicationContext, HomeScreen::class.java))
    }

    private fun checkInput() :Boolean{
        phoneNo = phone.editableText.toString()
        if(phoneNo.length == 10){
            return true
        }
        return false
    }

    private fun initViews(){

        proceed = findViewById(R.id.bt_proceed)
        phone = findViewById(R.id.et_phone)

    }

    override fun onStart() {
        super.onStart()
        //todo check if already signed in then transfer to home screen activity
    }
}