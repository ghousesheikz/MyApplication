package com.test.myapplication.view.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.myapplication.R
import com.test.myapplication.database.ModelSaveData
import kotlinx.android.synthetic.main.productitem_row_adapter.view.*

class ProductListAdapter(
    private var ListData: List<ModelSaveData>?,
    private var context: Context
) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.productitem_row_adapter, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = ListData!![position]
        holder.name_txt.text = Html.fromHtml("<font color='#FF3D00'>" + "<b>" + "Product Name : " + "</b>" + "</font>" + if (data.name != null || data.name !== "") data.name else "")
        holder.description_txt.text = Html.fromHtml("<font color='#FF3D00'>" + "<b>" + "Product Description : " + "</b>" + "</font>" + if (data.description != null || data.description !== "") data.description else "")

        holder.price_txt.text = Html.fromHtml("<font color='#FF3D00'>" + "<b>" + "Price : ₹ " + "</b>" + "</font>" + if (data.price.toString() != null || data.price.toString() !== "") data.price.toString() else "")
        holder.quantity_txt.text = Html.fromHtml("<font color='#FF3D00'>" + "<b>" + "Quantity : " + "</b>" + "</font>" + if (data.quantity.toString() != null || data.quantity.toString() !== "") data.quantity.toString() else "")

        Glide.with(context).load(data.imgUrl).into(holder.product_image)

    }


    override fun getItemCount(): Int {
        return ListData!!.size
    }

    fun updateAdapter(updatelist: List<ModelSaveData>?) {
        this.ListData = updatelist
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name_txt = itemView.name_txt
        val description_txt = itemView.description_txt
        val price_txt = itemView.price_txt
        val quantity_txt = itemView.quantity_txt
        val product_image = itemView.product_image

    }


}