package com.quynhdx.mapcoffee2.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater
import com.quynhdx.mapcoffee2.R
import com.quynhdx.mapcoffee2.model.ListCoffee_
import android.content.Context
import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.util.Log
import android.widget.*
import com.quynhdx.mapcoffee2.view.ListCoffeeActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.RequestOptions.bitmapTransform
import com.quynhdx.mapcoffee2.view.DetailActivity


class CoffeeAdapter(context: Context, view: ListCoffeeActivity) :
                RecyclerView.Adapter<CoffeeAdapter.ViewHolder>() {

    internal var data: List<ListCoffee_> = ArrayList()
    var context: Context? = context
    var mView: ListCoffeeActivity? = view
    lateinit var dataCoffeeNear: ListCoffee_

    fun setData(data: List<ListCoffee_>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.quynhdx.mapcoffee2.R.layout.item_coffee,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoffeeAdapter.ViewHolder, position: Int) {
        val dataCoffee = data[position]

        holder.tvName.text = dataCoffee.name
        holder.tvAddress.text = dataCoffee.address
        holder.ratingBar.rating = dataCoffee.vote?.toFloat() ?: 0.0F
        // TODO Show background
        Glide.with(context!!).load(dataCoffee.background)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.imgLogo)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        internal var tvName: TextView = itemView.findViewById(R.id.tv_Name)
        internal var tvAddress: TextView = itemView.findViewById(R.id.tv_Address)
        internal var imgLogo: ImageView = itemView.findViewById(R.id.img_logo)
        internal var ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

        val intent_ = Intent(context, DetailActivity::class.java)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val pos = this.layoutPosition
            intent_.putExtra("dataCoffee", data[pos])
            context!!.startActivity(intent_)
        }

    }
}