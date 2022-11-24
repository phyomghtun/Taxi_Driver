package apk.fortaxi.com.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apk.fortaxi.com.databinding.ItemCustomerBinding
import apk.fortaxi.com.extension.loadUrl
import apk.fortaxi.com.model.Customer

class CustomerListAdapter(context: Context): RecyclerView.Adapter<CustomerListAdapter.ViewHolder>() {
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
        holder.binding.cusProfile.loadUrl(customerList[position].customerphoto.toString())
        holder.binding.customerCard.setOnClickListener {
           // Log.d("Clicked",myLatitude+myLongitude)
        }
    }

    override fun getItemCount(): Int = customerList.size

    class ViewHolder(val binding: ItemCustomerBinding):RecyclerView.ViewHolder(binding.root)

}