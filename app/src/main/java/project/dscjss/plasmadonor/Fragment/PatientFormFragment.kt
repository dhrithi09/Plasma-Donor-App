package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.patient_form_fragment.*
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.Util.Utilities
import project.dscjss.plasmadonor.Util.isPhoneNumberValid
import project.dscjss.plasmadonor.ViewModel.PatientFormViewModel

class PatientFormFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseFirestore: FirebaseFirestore

    companion object {
        private const val TAG = "PatientForm"
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
        init()
        setupBloodGroupSpinner()
        setupGenderSpinner()
        // TODO: Use the ViewModel

        btSubmit.setOnClickListener {
            var check : Boolean = false
            if(etName.text.isBlank()){
                etName.error = "Name cannot be blank!"
                check = true
            }

            if(spBloodGrp?.selectedItem.toString().equals(getString(R.string.blood_group), true)){
                (spBloodGrp.selectedView as TextView).error = "Select Blood Group"
                check = true
            }
            if(etAge.text.isBlank()){
                etAge.error = "Age cannot be blank!"
                check = true
            }

            if(spGender?.selectedItem.toString().equals(getString(R.string.gender), true)){
                (spGender.selectedView as TextView).error = "Select Gender"
                check = true
            }
            if(etLocation.text.isBlank()){
                etLocation.error = "Location cannot be blank!"
                check = true
            }
            if(etMobile.text.isBlank()){
                etMobile.error = "Mobile no. cannot be blank!"
                check=true
            } else if (!isPhoneNumberValid(etMobile.text.toString())) {
                etMobile.error = "Mobile no. invalid!"
                check=true
            }
            if(etEmail.text.isBlank()){
                etEmail.error = "Email cannot be blank!"
                check = true
            }
            if(etHospital.text.isBlank()){
                etHospital.error = "Hospital cannot be blank!"
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
        etHospital.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                etHospital.error = null
            }
        })

    }

    private fun setupGenderSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.gender_array,
            android.R.layout.simple_spinner_item
        )
            .also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spGender?.adapter = arrayAdapter
            }
    }

    private fun setupBloodGroupSpinner() {
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.blood_group_array,
            android.R.layout.simple_spinner_item
        )
            .also { arrayAdapter ->
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spBloodGrp?.adapter = arrayAdapter
            }
    }

    private fun insertData() {
        var PatientDetails = HashMap<String, String>()
        PatientDetails["Name"] = etName.text.toString()
        PatientDetails["Age"] = etAge.text.toString()
        PatientDetails["Gender"] = spGender?.selectedItem.toString()
        PatientDetails["Location"] = etLocation.text.toString()
        PatientDetails["Hospital"] = etHospital.text.toString()
        PatientDetails["Mobile"] = etMobile.text.toString()
        PatientDetails["BloodGroup"] = spBloodGrp?.selectedItem.toString()
        PatientDetails["Diabetes"] = cbDiabetes.isChecked.toString()
        PatientDetails["BpProblem"] = cbBpProblem.isChecked.toString()
        PatientDetails["LiverProblem"] = cbLiver.isChecked.toString()

        firebaseFirestore.collection("patients")
            .add(PatientDetails)
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
        viewModel = ViewModelProvider(this).get(PatientFormViewModel::class.java)
    }

    private fun clearFields() {

        etName.setText("")
        etAge.setText("")
        spGender?.setSelection(0)
        etHospital.setText("")
        etLocation.setText("")
        etMobile.setText("")
        spBloodGrp?.setSelection(0)
        etEmail.setText("")
        if (cbDiabetes.isChecked) cbDiabetes.isChecked = false
        if (cbBpProblem.isChecked) cbBpProblem.isChecked = false
        if (cbLiver.isChecked) cbLiver.isChecked = false

    }

}