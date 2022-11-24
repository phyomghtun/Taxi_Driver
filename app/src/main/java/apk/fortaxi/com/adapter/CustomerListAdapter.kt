package apk.fortaxi.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apk.fortaxi.com.databinding.ItemCustomerBinding
import apk.fortaxi.com.extension.loadUrl
import apk.fortaxi.com.model.Customer
import apk.fortaxi.com.ui.MapsActivity

class CustomerListAdapter(var context: Context): RecyclerView.Adapter<CustomerListAdapter.ViewHolder>() {
    private var customerList: List<Customer> = emptyList()
    private var myLatitude: String? = null
    private var myLongitude: String? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setCustomer(customerList: List<Customer>){
        this.customerList = customerList
        notifyDataSetChanged()
    }

    fun setLocation(lat: String,long: String){
         this.myLatitude = lat
         this.myLongitude = long
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCustomerBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.cusName.text = customerList[position].name.toString()
        holder.binding.cusPhone.text = customerList[position].phone.toString()
        holder.binding.cusLocation.text = customerList[position].latlog.toString()
        val cus_latlong = customerList[position].latlog.toString().split(",")

        holder.binding.cusProfile.loadUrl(customerList[position].customerphoto.toString())
        holder.binding.customerCard.setOnClickListener {
            Intent(context, MapsActivity::class.java).also {
                it.putExtra("cus_latitude", cus_latlong[0])
                it.putExtra("cus_longitude", cus_latlong[1])
                it.putExtra("my_latitude", myLatitude)
                it.putExtra("my_longitude", myLongitude)
                context.startActivity(it)
            }
        }
    }

    override fun getItemCount(): Int = customerList.size

    class ViewHolder(val binding: ItemCustomerBinding):RecyclerView.ViewHolder(binding.root)

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filteredNames: ArrayList <Customer> ) {
        // this.dataList.clear()
        this.customerList = filteredNames
        notifyDataSetChanged()
    }

}