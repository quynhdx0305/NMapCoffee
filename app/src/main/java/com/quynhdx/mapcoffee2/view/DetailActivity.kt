package com.quynhdx.mapcoffee2.view

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.quynhdx.mapcoffee2.R
import com.quynhdx.mapcoffee2.model.ListCoffee_
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    lateinit var dataCoffee: ListCoffee_

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // TODO get data from ListMapsActivity
        val intent = intent
        dataCoffee = intent.getParcelableExtra("dataCoffee")
        Toast.makeText(this, dataCoffee.id, Toast.LENGTH_LONG).show()

        setupView()
    }

    @SuppressLint("SetTextI18n")
    private fun setupView() {
        Glide.with(this).load(dataCoffee.background)
            .into(img_logo)
        Glide.with(this).load(dataCoffee.image?.get(0))
            .into(img_1)
        Glide.with(this).load(dataCoffee.image?.get(1))
            .into(img_2)
        Glide.with(this).load(dataCoffee.image?.get(2))
            .into(img_3)
        tv_Name.text = dataCoffee.name
        tv_vote.text = dataCoffee.vote
        tv_number_vote.text = "(" + dataCoffee.numberVotate + ")"
        rb_star.rating = dataCoffee.vote?.toFloat() ?: 0.0F
        tv_time_open.text = "Giờ mở cửa: " + dataCoffee.timeOpen
        tv_address.text = dataCoffee.address
        tv_phone.text = dataCoffee.phone
        tv_web.text = dataCoffee.web
    }
}
