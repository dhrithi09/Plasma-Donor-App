package project.dscjss.plasmadonor.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import project.dscjss.plasmadonor.Activity.WebActivity
import project.dscjss.plasmadonor.R

class TwitterSearchFragment : Fragment(), View.OnClickListener {
    lateinit var mdKolkata: MaterialCardView
    lateinit var mdDelhi: MaterialCardView
    lateinit var mdPune: MaterialCardView
    lateinit var mdMumbai: MaterialCardView
    lateinit var mdBangalore: MaterialCardView
    lateinit var mdThane: MaterialCardView
    lateinit var mdHyderbad: MaterialCardView
    lateinit var mdNagpur: MaterialCardView
    lateinit var mdLucknow: MaterialCardView
    lateinit var mdAhmedabad: MaterialCardView
    lateinit var mdChennai: MaterialCardView
    lateinit var mdGoa: MaterialCardView
    lateinit var cvCityName: MaterialCardView
    lateinit var btnSearchCities: MaterialButton
    lateinit var btnSearch: MaterialButton
    lateinit var etCityName: EditText
    lateinit var switch: SwitchCompat
    lateinit var city: String
    private var verify: String = "\"not%20verified\"%20-\"unverified\"%20-"
    var url = "https://twitter.com/search?q=verified%20"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_twitter_search, container, false)
        init(view)
        setClick()
        switch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                switch.text = "Verified Posts Only "
                verify = ""
            } else {
                switch.text = "All Posts "
                verify = "\"not%20verified\"%20-\"unverified\"%20-"
            }
        }
        return view
    }

    private fun setClick() {
        mdKolkata.setOnClickListener(this)
        mdDelhi.setOnClickListener(this)
        mdPune.setOnClickListener(this)
        mdMumbai.setOnClickListener(this)
        mdBangalore.setOnClickListener(this)
        mdThane.setOnClickListener(this)
        mdHyderbad.setOnClickListener(this)
        mdNagpur.setOnClickListener(this)
        mdAhmedabad.setOnClickListener(this)
        mdLucknow.setOnClickListener(this)
        mdChennai.setOnClickListener(this)
        mdGoa.setOnClickListener(this)
        btnSearchCities.setOnClickListener(this)
        btnSearch.setOnClickListener(this)
    }

    private fun init(view: View) {
        mdKolkata = view.findViewById(R.id.mdKolkata)
        mdDelhi = view.findViewById(R.id.mdDelhi)
        mdPune = view.findViewById(R.id.mdPune)
        mdMumbai = view.findViewById(R.id.mdMumbai)
        mdBangalore = view.findViewById(R.id.mdBangalore)
        mdThane = view.findViewById(R.id.mdThane)
        mdHyderbad = view.findViewById(R.id.mdHyderbad)
        mdNagpur = view.findViewById(R.id.mdNagpur)
        mdAhmedabad = view.findViewById(R.id.mdAhmedabad)
        mdLucknow = view.findViewById(R.id.mdLucknow)
        mdChennai = view.findViewById(R.id.mdChennai)
        mdGoa = view.findViewById(R.id.mdGoa)
        switch = view.findViewById(R.id.swVerification)
        cvCityName = view.findViewById(R.id.cvCityName)
        btnSearchCities = view.findViewById(R.id.btnSearchCities)
        btnSearch = view.findViewById(R.id.btnSearch)
        etCityName = view.findViewById(R.id.etCityName)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.mdKolkata -> {
                Toast.makeText(context, "Explore Kolkata", Toast.LENGTH_SHORT).show()
                city = "kolkata"
                urlParse(city)
            }
            R.id.mdDelhi -> {
                city = "delhi"
                urlParse(city)
            }
            R.id.mdPune -> {
                city = "pune"
                urlParse(city)
            }
            R.id.mdMumbai -> {
                city = "mumbai"
                urlParse(city)
            }
            R.id.mdBangalore -> {
                city = "bangalore"
                urlParse(city)
            }
            R.id.mdThane -> {
                city = "thane"
                urlParse(city)
            }
            R.id.mdHyderbad -> {
                city = "hyderbad"
                urlParse(city)
            }
            R.id.mdNagpur -> {
                city = "nagpur"
                urlParse(city)
            }
            R.id.mdAhmedabad -> {
                city = "ahmedabad"
                urlParse(city)
            }
            R.id.mdLucknow -> {
                city = "lucknow"
                urlParse(city)
            }
            R.id.mdChennai -> {
                city = "chennai"
                urlParse(city)
            }
            R.id.mdGoa -> {
                city = "goa"
                urlParse(city)
            }
            R.id.btnSearchCities -> {
                cvCityName.visibility = View.VISIBLE
                btnSearch.visibility = View.VISIBLE
            }
            R.id.btnSearch -> {
                city = etCityName.text.toString()
                urlParse(city)
            }
        }
    }

    private fun urlParse(city: String) {
        url = "https://twitter.com/search?q=verified%20"
        url = "$url$city%20(plasma)%20-$verify\"needed\"%20-\"need\"%20-\"needs\""
        val url2 = "%20-\"required\"%20-\"require\"%20-\"requires\"%20-\"requirement\"%20-\"requirements\"&f=live"
        url = "$url$url2"
        val intent = Intent(requireContext(), WebActivity::class.java)
        intent.putExtra("urlLink", url)
        startActivity(intent)
    }
}
