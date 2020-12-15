package com.test.myapplication.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.test.myapplication.R
import com.test.myapplication.datamodel.CategoryDetailsResponse
import com.test.myapplication.utils.Constants
import kotlinx.android.synthetic.main.category_adapter.view.*

class CategoryAdapter(
    private val ListData: List<CategoryDetailsResponse.ArrayOfProduct?>,
    private var context: Context, var mItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.category_adapter, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = ListData!![position]
        holder.CategoryName.text = data!!.name
        val circularProgressDrawable = CircularProgressDrawable(context)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f
        circularProgressDrawable.start()

        Glide.with(context).load(data.imgUrl).placeholder(circularProgressDrawable).into(holder.CategoryImage)

        holder.itemClick.setOnClickListener {
            if (data != null) {
                mItemClickListener.onItemClick(data, position)
            }
        }
    }


    override fun getItemCount(): Int {
        return ListData!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val CategoryImage = itemView.category_image
        val CategoryName = itemView.category_txt
        val itemClick = itemView.category_click

    }

    interface OnItemClickListener {
        fun onItemClick(response: CategoryDetailsResponse.ArrayOfProduct, position: Int)
    }
}