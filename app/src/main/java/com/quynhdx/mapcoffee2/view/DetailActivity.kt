package com.quynhdx.mapcoffee2.view

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.quynhdx.mapcoffee2.R
import com.quynhdx.mapcoffee2.adapter.CommentAdapter
import com.quynhdx.mapcoffee2.model.ListCoffee_
import kotlinx.android.synthetic.main.activity_detail.*

private const val TAG = "DetailActivity"

class DetailActivity : AppCompatActivity() {

    lateinit var dataCoffee: ListCoffee_
    lateinit var commentAdapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO get data from Coffee Adapter
        val intent = intent
        dataCoffee = intent.getParcelableExtra("dataCoffee")
        Toast.makeText(this, dataCoffee.id, Toast.LENGTH_LONG).show()

        Log.d(TAG,"data list comment" + dataCoffee.evaluate!!.count().toString())
        setupView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        Glide.with(this).load(dataCoffee.background)
            .centerCrop().into(img_logo)
        Glide.with(this).load(dataCoffee.image?.get(0))
            .centerCrop().into(img_1)
        Glide.with(this).load(dataCoffee.image?.get(1))
            .centerCrop().into(img_2)
        Glide.with(this).load(dataCoffee.image?.get(2))
            .centerCrop().into(img_3)
        tv_Name.text = dataCoffee.name
        tv_vote.text = dataCoffee.vote
        tv_number_vote.text = "(" + dataCoffee.numberVotate + ")"
        rb_star.rating = dataCoffee.vote?.toFloat() ?: 0.0F
        tv_time_open.text = "Giờ mở cửa: " + dataCoffee.timeOpen
        tv_address.text = dataCoffee.address
        tv_phone.text = dataCoffee.phone
        tv_web.text = dataCoffee.web

        commentAdapter = CommentAdapter(applicationContext,this)
        if (dataCoffee.evaluate == null) return
        commentAdapter.setData(dataCoffee.evaluate!!)

        rcv_comment.adapter = commentAdapter
        rcv_comment.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_comment.layoutManager = layoutManager
        //................Chèn một kẻ ngang giữa các phần tử
        val dividerHorizontal = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerHorizontal.setDrawable(applicationContext.getDrawable(R.drawable.line_rv_timeline)!!)
        rcv_comment.addItemDecoration(dividerHorizontal)
    }
}
