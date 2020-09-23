package project.dscjss.plasmadonor.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.tvLogin
import kotlinx.android.synthetic.main.signup_fragment.*
import project.dscjss.plasmadonor.Interface.FragmentChangeInterface
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.SignupViewModel

class SignupFragment : Fragment() {

    lateinit var fragmentChangeInterface: FragmentChangeInterface
    private lateinit var viewModel: SignupViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        fun newInstance() = SignupFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.signup_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignupViewModel::class.java)

        init()



    }

    private fun init() {

        fragmentChangeInterface = context as FragmentChangeInterface
        firebaseAuth = FirebaseAuth.getInstance()

        tvLogin.setOnClickListener {
            fragmentChangeInterface.changeFragment(LoginFragment())
        }

        tvSignUpButton.setOnClickListener {
            if (!checkFields()) {
                return@setOnClickListener
            }

            firebaseAuth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                .addOnCompleteListener {
                    it.addOnSuccessListener {

                        // Sign Up Success Dialog
                        Toast.makeText(context, "User Created", Toast.LENGTH_SHORT).show()
                        addDetails()

                    }

                    it.addOnFailureListener { e ->

                        Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()

                    }

                }

        }

    }

    private fun addDetails() {

        var userProfileChangeRequest = UserProfileChangeRequest.Builder()
            .setDisplayName("${etFirstName.text} ${etLastName.text}")
            .build()

        firebaseAuth.currentUser!!.updateProfile(userProfileChangeRequest)

        val user: HashMap<String, String> = HashMap()
        user["uid"] = firebaseAuth.currentUser!!.uid
        user["FirstName"] = etFirstName.text.toString()
        user["LastName"] = etLastName.text.toString()
        user["Email"] = etEmail.text.toString()
        user["Phone"] = etPhone.text.toString()

        FirebaseFirestore.getInstance().collection("users")
            .add(user)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Data Inserted", Toast.LENGTH_SHORT).show()
                }
                else {
                    addDetails()
                }
            }

    }

    private fun checkFields(): Boolean {


        if (etEmail.text.isNullOrEmpty()) {
            etEmail.error = "Email can't be empty"
            etEmail.requestFocus()
            return false
        }
        else {
            etEmail.error = null
            etEmail.clearFocus()
        }


        if (etFirstName.text.isNullOrEmpty()) {
            etFirstName.error = "Email can't be empty"
            etFirstName.requestFocus()
            return false
        }
        else {
            etFirstName.error = null
            etFirstName.clearFocus()
        }


        if (etLastName.text.isNullOrEmpty()) {
            etLastName.error = "Email can't be empty"
            etLastName.requestFocus()
            return false
        }
        else {
            etLastName.clearFocus()
            etLastName.error = null
        }

        if (etPhone.text.isNullOrEmpty()) {
            etPhone.error = "Email can't be empty"
            etPhone.requestFocus()
            return false
        }
        else {
            etPhone.clearFocus()
            etPhone.error = null
        }


        if (etPassword.text.isNullOrEmpty()) {
            etPassword.error = "Email can't be empty"
            etPassword.requestFocus()
            return false
        }
        else if (etPassword.text.length < 8) {
            etPassword.error = "Password must be 8 digits long"
            etPassword.requestFocus()
            return false
        }
        else {
            etPassword.error = null
            etPassword.clearFocus()
        }

        return true

    }

}