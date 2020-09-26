package project.dscjss.plasmadonor.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.PatientListViewModel

class PatientListFragment : Fragment() {

    companion object {
        fun newInstance() = PatientListFragment()
    }

    private lateinit var viewModel: PatientListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.patient_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PatientListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}