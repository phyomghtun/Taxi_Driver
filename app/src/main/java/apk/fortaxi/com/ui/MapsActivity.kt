package apk.fortaxi.com.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import apk.fortaxi.com.R
import apk.fortaxi.com.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlin.properties.Delegates

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var myLocation: LatLng
    private lateinit var cusLocation: LatLng
    private var locationArrayList: ArrayList<LatLng>? = null
    private var customerLatitude by Delegates.notNull<Double>()
    private var customerLongitude by Delegates.notNull<Double>()
    private var myLatitude by Delegates.notNull<Double>()
    private var myLongitude by Delegates.notNull<Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        locationArrayList = ArrayList()

        customerLatitude = intent.getStringExtra("cus_latitude")!!.toDouble()
        customerLongitude = intent.getStringExtra("cus_longitude")!!.toDouble()
        myLatitude = intent.getStringExtra("my_latitude")!!.toDouble()
        myLongitude = intent.getStringExtra("my_longitude")!!.toDouble()
        cusLocation = LatLng(customerLatitude,customerLongitude)
        myLocation = LatLng(myLatitude,myLongitude)
        locationArrayList!!.add(cusLocation)
        locationArrayList!!.add(myLocation)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        for (i in locationArrayList!!.indices){
            mMap.addMarker(MarkerOptions().position(locationArrayList!![i]).title("Marker"))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
            mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList!![i]))
        }

    }
}