package project.dscjss.plasmadonor.Fragment

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.DonorListViewModel

class DonorListFragment : Fragment() {

    companion object {
        fun newInstance() = DonorListFragment()
    }

    private lateinit var viewModel: DonorListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.donor_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DonorListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}