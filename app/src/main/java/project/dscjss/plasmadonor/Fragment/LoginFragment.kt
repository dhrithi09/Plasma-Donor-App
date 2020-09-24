package project.dscjss.plasmadonor.Fragment

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.signup_fragment.*
import project.dscjss.plasmadonor.Activity.MainActivity
import project.dscjss.plasmadonor.Interface.FragmentChangeInterface
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.LoginViewModel

class LoginFragment : Fragment() {

    lateinit var fragmentChangeInterface: FragmentChangeInterface
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var firebaseFirestore: FirebaseFirestore
    private val RC_SIGN_IN = 101
    private val TAG = "Login Fragment"
    private lateinit var googleSignInClient: GoogleSignInClient

    companion object {
        fun newInstance() = LoginFragment()
    }

    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        // TODO: Use the ViewModel

        fragmentChangeInterface = context as FragmentChangeInterface

        tvSignUpLogin.setOnClickListener {
            fragmentChangeInterface.changeFragment(SignupFragment())
        }

        tvForgot.setOnClickListener {

            if (etEmailLogin.text.isNullOrEmpty()) {
                etEmailLogin.error = "Enter Email to receive to Reset Password form"
                etEmailLogin.requestFocus()
                return@setOnClickListener
            }
            else {
                etEmailLogin.error = null
                etEmailLogin.clearFocus()
            }

            firebaseAuth.sendPasswordResetEmail(etEmailLogin.text.toString())
                .addOnSuccessListener {
                    Toast.makeText(context, "Please check your Email", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

        }

        tvLoginButton.setOnClickListener {
            if (!checkFields())
                return@setOnClickListener

            firebaseAuth.signInWithEmailAndPassword(etEmailLogin.text.toString(), etPasswordLogin.text.toString())
                .addOnSuccessListener {
                    Toast.makeText(context, "Login Success: ${it.user!!.displayName}", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(context, MainActivity::class.java))
                    activity!!.finish()
                }
                .addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

        }

        tvLoginGmail.setOnClickListener {

            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(activity!!, gso)

            signInGmail()

        }

    }

    private fun signInGmail() {

        if (firebaseAuth.currentUser != null)
            firebaseAuth.signOut()

        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser

                    // Check if user already exist in Database
                    val docRef = firebaseFirestore.collection("users")
                        .whereEqualTo("uid", user!!.uid)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document.size() == 0) {
                                Log.d(TAG, "No such User")

                                val userHash: HashMap<String, String> = HashMap()
                                userHash["uid"] = firebaseAuth.currentUser!!.uid
                                userHash["FirstName"] =
                                    user.displayName.toString().substringBefore(' ', "")
                                userHash["LastName"] =
                                    user.displayName.toString().substringAfter(' ', "")
                                userHash["Email"] = user.email.toString()
                                userHash["Phone"] = "Not Provided"
                                userHash["BloodGroup"] = "Not Provided"
                                userHash["Age"] = "Not Provided"
                                userHash["Weight"] = "Not Provided"
                                userHash["Gender"] = "Not Provided"
                                userHash["Location"] = "Not Provided"

                                FirebaseFirestore.getInstance().collection("users")
                                    .add(userHash)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            Toast.makeText(
                                                context,
                                                "Data Inserted",
                                                Toast.LENGTH_SHORT
                                            ).show()

                                            startActivity(Intent(context, MainActivity::class.java))
                                            activity!!.finish()

                                        }
                                    }

                            }
                            else {
                                startActivity(Intent(context, MainActivity::class.java))
                                activity!!.finish()
                            }


//                            val account = task.getResult(ApiException::class.java)!!
//                            Log.d(TAG, "firebaseAuthWithGoogle: ${account.displayName}")
//                            Toast.makeText(context, "Login Success: ${account.displayName}", Toast.LENGTH_SHORT).show()

                        }
                        .addOnFailureListener { exception ->
                            Log.e(TAG, "get failed with ", exception)
                        }



                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    Toast.makeText(context, "Login Failed: ${task.exception}", Toast.LENGTH_SHORT).show()
                    // ...
//                    Snackbar.make(view, "Authentication Failed.", Snackbar.LENGTH_SHORT).show()
                }

            }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {

                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle: ${account.displayName}")
                Toast.makeText(context, "Login Success: ${account.displayName}", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(context, MainActivity::class.java))
//                activity!!.finish()

                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed ${e.message}", e)
                Toast.makeText(context, "Login Failed: ${e.message}", Toast.LENGTH_SHORT).show()
                // ...
            }
        }
    }

    private fun checkFields(): Boolean {

        if (etEmailLogin.text.toString().isNullOrEmpty()) {
            etEmailLogin.error = "Email can't be empty"
            etEmailLogin.requestFocus()
            return false
        }
        else {
            etEmailLogin.error = null
            etEmailLogin.clearFocus()
        }

        if (etPasswordLogin.text.toString().isNullOrEmpty()) {
            etPasswordLogin.error = "Email can't be empty"
            etPasswordLogin.requestFocus()
            return false
        }
        else {
            etPasswordLogin.error = null
            etPasswordLogin.clearFocus()
        }

        return true

    }

}