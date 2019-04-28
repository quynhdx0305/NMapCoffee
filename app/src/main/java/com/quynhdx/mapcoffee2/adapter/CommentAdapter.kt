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
import com.quynhdx.mapcoffee2.model.Evaluate
import com.quynhdx.mapcoffee2.view.DetailActivity


class CommentAdapter(context: Context, view: DetailActivity) :
                RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    internal var data: List<Evaluate> = ArrayList()
    var context: Context? = context

    fun setData(data: List<Evaluate>) {
        this.data = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.quynhdx.mapcoffee2.R.layout.item_comment,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentAdapter.ViewHolder, position: Int) {
        val dataComment = data[position]
        holder.tvName.text = dataComment.userName
        holder.rtbComment.rating = dataComment.vote?.toFloat() ?: 0.0F
        holder.tvComment.text = dataComment.comment

        // TODO Set Avatar
        Glide.with(context!!).load(dataComment.avatar)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.imgAvatar)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tvName: TextView = itemView.findViewById(R.id.tv_name)
        internal var imgAvatar: ImageView = itemView.findViewById(R.id.img_avatar)
        internal var rtbComment: RatingBar = itemView.findViewById(R.id.rtb_comment)
        internal var tvComment: TextView = itemView.findViewById(R.id.tv_comment)


    }
}