package apk.fortaxi.com.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import apk.fortaxi.com.model.Customer
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class HomeViewModel: ViewModel() {
    private val db = Firebase.firestore

    private val customerDetails = MutableLiveData<List<Customer>>()
    private val customerList = ArrayList<Customer>()

    fun getDetails(): MutableLiveData<List<Customer>>{

        db.collection("Customers").get()
            .addOnSuccessListener {
                for(u in it){
                    customerList.add(
                        Customer(
                        u.getString("name"),
                        u.getString("phone"),
                        u.getString("latlog"),
                        u.getString("customerphoto")
                        )
                    )
                }
                customerDetails.value = customerList
            }
            .addOnFailureListener {  }

        return customerDetails
    }


}