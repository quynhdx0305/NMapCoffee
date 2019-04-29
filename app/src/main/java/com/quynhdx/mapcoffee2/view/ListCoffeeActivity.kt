package com.quynhdx.mapcoffee2.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.quynhdx.mapcoffee2.R
import com.quynhdx.mapcoffee2.adapter.CoffeeAdapter
import com.quynhdx.mapcoffee2.model.ListCoffee_
import kotlinx.android.synthetic.main.activity_list_coffee.*
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.content.IntentFilter
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context


private const val TAG = "ListCoffeeActivity"
class ListCoffeeActivity : AppCompatActivity() {

    lateinit var coffeeAdapter: CoffeeAdapter
    var listDataCoffee : ArrayList<ListCoffee_> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_coffee)

        // TODO get data from MapsActivity
        val intent = intent
        listDataCoffee = intent.getParcelableArrayListExtra("listDataCoffeeNear")

        Log.d(TAG, listDataCoffee.count().toString())
        setup()

        //TODO auto finish when Detail Activity find direction action
        val broadcastReciever = object : BroadcastReceiver() {

            override fun onReceive(arg0: Context, intent: Intent) {
                val action = intent.action
                if (action == "finish") {
                    finish()
                }
            }
        }
        this.registerReceiver(broadcastReciever, IntentFilter("finish"))
    }

    private fun setup() {
        coffeeAdapter = CoffeeAdapter(applicationContext,this)
        coffeeAdapter.setData(listDataCoffee)

        rcv_coffee.adapter = coffeeAdapter
        //recyclerView
        rcv_coffee.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcv_coffee.layoutManager = layoutManager
        //................Chèn một kẻ ngang giữa các phần tử
        val dividerHorizontal = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        dividerHorizontal.setDrawable(applicationContext.getDrawable(R.drawable.line_rv_timeline)!!)
        rcv_coffee.addItemDecoration(dividerHorizontal)
    }
}
