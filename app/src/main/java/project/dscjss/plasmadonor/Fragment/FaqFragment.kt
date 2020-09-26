package project.dscjss.plasmadonor.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.faq_fragment.*
import project.dscjss.plasmadonor.Adapter.FaqAdapter
import project.dscjss.plasmadonor.Model.FaqModel
import project.dscjss.plasmadonor.R
import project.dscjss.plasmadonor.ViewModel.FaqViewModel

class FaqFragment : Fragment() {

    companion object {
        fun newInstance() = FaqFragment()
    }

    private lateinit var viewModel: FaqViewModel
    private var faqList: MutableList<FaqModel> = mutableListOf<FaqModel>()
    private lateinit var firebaseFirestore: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.faq_fragment, container, false)
        getData()
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FaqViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun setRecyclerview(){
        faqRecycle.adapter = FaqAdapter(faqList)
    }

    private fun getData(){
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("faq")
            .get().addOnSuccessListener {doc->
                val it = doc.documents
                faqList.clear()
                for(i in it){

                    val ques = i["question"].toString()
                    val sol = i["solution"].toString()

                    faqList.add(
                        FaqModel(ques,sol)
                    )
                }
                setRecyclerview()
            }
    }

}