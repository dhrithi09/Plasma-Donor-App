package project.dscjss.plasmadonor.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.profile_fragment.*
import project.dscjss.plasmadonor.Activity.UserLoginActivity
import project.dscjss.plasmadonor.Interface.FragmentChangeInterface
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities
import project.dscjss.plasmadonor.ViewModel.ProfileViewModel

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    lateinit var utilities : Utilities
    lateinit var fragmentChangeInterface: FragmentChangeInterface
    private lateinit var viewModel: ProfileViewModel
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore
    private val TAG = "Profile Fragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()

        init()

        if (firebaseAuth.currentUser == null)  {
            startActivity(Intent (context, UserLoginActivity::class.java))
            activity!!.finish()
        }
        else {

            firebaseFirestore.collection("users")
                .whereEqualTo("uid", firebaseAuth.currentUser!!.uid)
                .get()
                .addOnSuccessListener {doc->
                    val it = doc.documents[0]

                    tvProfileName.text = "${it["FirstName"].toString()} ${it["LastName"].toString()}"
                    tvProfileAge.text = it["Age"].toString()
                    tvBloodGroup.text = it["BloodGroup"].toString()
                    tvProfileWeight.text = it["Weight"].toString()
                    tvProfileGender.text = it["Gender"].toString()
                    tvProfileMobile.text = it["Phone"].toString()
                    tvProfileEmail.text = it["Email"].toString()
                    tvProfileLocation.text = it["Location"].toString()

                }
                .addOnFailureListener {

                    utilities.showShortToast(context!! , "Something went wrong fetching user details")

                    Log.e(TAG, it.message.toString())
                }
        }

    }

    private fun init(){

        fragmentChangeInterface = context as FragmentChangeInterface
        utilities =Utilities

        buttonProfileEdit.setOnClickListener{
            fragmentChangeInterface.changeFragment(EditProfileFragment())
        }

    }

}