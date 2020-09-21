package project.dscjss.plasmadonor.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.login_fragment.*
import project.dscjss.plasmadonor.Interface.FragmentChangeInterface
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.SignupViewModel

class SignupFragment : Fragment() {

    private lateinit var fragmentChangeInterface: FragmentChangeInterface
    private lateinit var viewModel: SignupViewModel

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

        fragmentChangeInterface = context as FragmentChangeInterface

        tvSignUpLogin.setOnClickListener {
            fragmentChangeInterface.changeFragment(SignupFragment())
        }

    }

}