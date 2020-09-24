package project.dscjss.plasmadonor.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.PatientFormViewModel

class PatientFormFragment : Fragment() {

    companion object {
        fun newInstance() = PatientFormFragment()
    }

    private lateinit var viewModel: PatientFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.patient_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PatientFormViewModel::class.java)
        // TODO: Use the ViewModel
    }

}