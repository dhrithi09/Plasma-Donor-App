package project.dscjss.plasmadonor.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat.startActivity
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities

class OTPActivity : AppCompatActivity() {

    private lateinit var verify: AppCompatButton
    private lateinit var otp : AppCompatEditText
    private lateinit var sOtp :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)

        initViews()

        verify.setOnClickListener{
            if(checkOTP()){
                Utilities.showShortToast(this,"Sign in successful");
                startActivity(Intent(applicationContext,HomeScreen::class.java))
                finish()
            }
        }
    }

    private fun checkOTP() : Boolean {  //todo update
        sOtp = otp.editableText.toString()
        if (sOtp == "123"){
            return true
        }
        return false
    }

    private fun initViews(){
        verify = findViewById(R.id.bt_verify)
        otp = findViewById(R.id.et_otp)
    }
}