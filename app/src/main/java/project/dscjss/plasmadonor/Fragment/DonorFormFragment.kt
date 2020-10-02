package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.donor_form_fragment.*
import kotlinx.android.synthetic.main.patient_form_fragment.*
import kotlinx.android.synthetic.main.patient_form_fragment.btSubmit
import kotlinx.android.synthetic.main.patient_form_fragment.cbBpProblem
import kotlinx.android.synthetic.main.patient_form_fragment.cbDiabetes
import kotlinx.android.synthetic.main.patient_form_fragment.cbLiver
import kotlinx.android.synthetic.main.patient_form_fragment.etAge
import kotlinx.android.synthetic.main.patient_form_fragment.etBloodGrp
import kotlinx.android.synthetic.main.patient_form_fragment.etEmail
import kotlinx.android.synthetic.main.patient_form_fragment.etGender
import kotlinx.android.synthetic.main.patient_form_fragment.etLocation
import kotlinx.android.synthetic.main.patient_form_fragment.etMobile
import kotlinx.android.synthetic.main.patient_form_fragment.etName
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities
import project.dscjss.plasmadonor.ViewModel.DonorFormViewModel

class DonorFormFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    companion object {
        private const val TAG = "DonorForm"
        fun newInstance() = DonorFormFragment()
    }

    private lateinit var viewModel: DonorFormViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.donor_form_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
        // TODO: Use the ViewModel

        btSubmit.setOnClickListener {
            var check : Boolean = false
            if(etName.text.isBlank()){
                etName.error = "Name cannot be blank!"
                check = true
            }
            if(etBloodGrp.text.isBlank()){
                etBloodGrp.error = "Blood group cannot be blank!"
                check = true
            }
            if(etAge.text.isBlank()){
                etAge.error = "Age cannot be blank!"
                check = true
            }
            if(etGender.text.isBlank()){
                etGender.error = "Gender cannot be blank!"
                check = true
            }
            if(etLocation.text.isBlank()){
                etLocation.error = "Location cannot be blank!"
                check = true
            }
            if(etMobile.text.isBlank()){
                etMobile.error = "Mobile cannot be blank!"
                check = true
            }
            if(etEmail.text.isBlank()){
                etEmail.error = "Email cannot be blank!"
                check = true
            }

            if(check)
                return@setOnClickListener

            insertData()
        }
        etName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etName.error = null
            }
        })
        etAge.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etAge.error = null
            }
        })
        etBloodGrp.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etBloodGrp.error = null
            }
        })
        etGender.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etGender.error = null
            }
        })
        etLocation.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etLocation.error = null
            }
        })
        etMobile.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etMobile.error = null
            }
        })
        etEmail.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etEmail.error = null
            }
        })
    }


    private fun insertData() {
        var donorDetails = HashMap<String, String>()
        donorDetails["Name"] = etName.text.toString()
        donorDetails["Age"] = etAge.text.toString()
        donorDetails["Gender"] = etGender.text.toString()
        donorDetails["Location"] = etLocation.text.toString()
        donorDetails["Mobile"] = etMobile.text.toString()
        donorDetails["BloodGroup"] = etBloodGrp.text.toString()
        donorDetails["Diabetes"] = cbDiabetes.isChecked.toString()
        donorDetails["BpProblem"] = cbBpProblem.isChecked.toString()
        donorDetails["LiverProblem"] = cbLiver.isChecked.toString()

        firebaseFirestore.collection("donors")
            .add(donorDetails)
            .addOnSuccessListener {
                Utilities.showShortToast(requireContext(), "Data Inserted successfully")
                clearFields()
            }
            .addOnFailureListener {
                Utilities.showShortToast(requireContext(), "Something went wrong")
            }
    }

    private fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseFirestore = FirebaseFirestore.getInstance()
        viewModel = ViewModelProvider(this).get(DonorFormViewModel::class.java)
    }

    private fun clearFields() {

        etName.setText("")
        etAge.setText("")
        etGender.setText("")
        etLocation.setText("")
        etMobile.setText("")
        etBloodGrp.setText("")
        etEmail.setText("")
        if (cbDiabetes.isChecked) cbDiabetes.isChecked = false
        if (cbBpProblem.isChecked) cbBpProblem.isChecked = false
        if (cbLiver.isChecked) cbLiver.isChecked = false

    }


}