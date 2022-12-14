package apk.fortaxi.com.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import apk.fortaxi.com.adapter.CustomerListAdapter
import apk.fortaxi.com.databinding.ActivityHomeBinding
import apk.fortaxi.com.extension.loadUrl
import apk.fortaxi.com.model.Customer
import apk.fortaxi.com.viewmodel.HomeViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class HomeActivity : AppCompatActivity() {
    private lateinit var name: String
    private lateinit var email: String
    private lateinit var profile: String
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var customerListAdapter: CustomerListAdapter
    private val default_pf = "https://www.drshaneholmes.com/wp-content/uploads/2020/03/146-1468479_my-profile-icon-blank-profile-picture-circle-hd.png"

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    var latitude : String? = null
    var longitude : String? = null

    var dataList = ArrayList <Customer> ()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        name = intent.getStringExtra("name").toString()
        email = intent.getStringExtra("email").toString()
        profile = intent.getStringExtra("profile").toString()
        binding.nameTv.text = name
        binding.emailTv.text = email

        if(profile == "null"){
            binding.userProfile.loadUrl(default_pf)
        }else{
            binding.userProfile.loadUrl(profile)
        }

        binding.signOut.setOnClickListener {
            mGoogleSignInClient.signOut()
                .addOnCompleteListener {
                    Intent(this@HomeActivity,MainActivity::class.java).also {

                        startActivity(it)

                        finish()
                    }
                }
        }

        binding.search.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })

        getLocation()
        initRecycler()
        getCustomer()

    }

    private fun initRecycler(){
         customerListAdapter = CustomerListAdapter(this)
         binding.rView.apply {
             layoutManager = LinearLayoutManager(applicationContext)
             adapter = customerListAdapter
         }
    }

    private fun getCustomer(){
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        viewModel.getDetails().observe(this){
            Log.d("customer", it.toString())
            customerListAdapter.setCustomer(it)
            dataList.addAll(it)
            binding.progress.visibility = View.INVISIBLE
            latitude?.let { it1 -> longitude?.let { it2 ->
                customerListAdapter.setLocation(it1,
                    it2
                )
            } }
        }

    }

    //Location
    private fun getLocation(){
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)
        != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), 100)

            return
        }

        //get Latitude and Longitude
        val location = fusedLocationProviderClient.lastLocation
        location.addOnSuccessListener {
            if(it != null){
                latitude = it.latitude.toString()
                longitude = it.longitude.toString()

            }
        }
    }

    //Search
    private fun filter(text: String) {
        val filteredNames = ArrayList <Customer> ()
        dataList.filterTo(filteredNames) {
            it.name!!.toLowerCase().contains(text.toLowerCase())
        }
        if (filteredNames != null) {
            customerListAdapter!!.filterList(filteredNames)
        }
    }

}