package com.test.myapplication.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.myapplication.R
import com.test.myapplication.database.ModelSaveData
import com.test.myapplication.datamodel.MovieDetailsResponse
import com.test.myapplication.utils.Constants.IMAGE_URL
import kotlinx.android.synthetic.main.movie_item_row_adapter.view.*

class MovieListAdapter(
    private val ListData: List<ModelSaveData>?,
    private var context: Context, var mItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MovieListAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item_row_adapter, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = ListData!![position]
        holder.Title.text = data.title
        holder.movieDesc.text = data.overview

        Glide.with(context).load(IMAGE_URL + data.posterPath).into(holder.moviePostre)

        holder.itemClick.setOnClickListener {
            mItemClickListener.onItemClick(data, position)
        }
    }


    override fun getItemCount(): Int {
        return ListData!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Title = itemView.movieTitle
        val movieDesc = itemView.movieDesc
        val moviePostre = itemView.moviePostre
        val itemClick = itemView.itemclick

    }

    interface OnItemClickListener {
        fun onItemClick(response: ModelSaveData?, position: Int)
    }
}