package com.quynhdx.mapcoffee2.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.quynhdx.mapcoffee2.R
import com.quynhdx.mapcoffee2.model.ListCoffee_

private const val TAG = "Splash ==="

class Splash : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Write a message to the database

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("list coffee")
        val gson = Gson()
        val listDataCoffee = arrayListOf<ListCoffee_>()

        // Read from the database
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val _size = dataSnapshot.children.count()

                for ( (_inx,_chi) in dataSnapshot.children.withIndex()) {
                    val _str = gson.toJson(_chi.value)
                    val _evaluate : ListCoffee_ = gson.fromJson(_str, ListCoffee_().javaClass)

                    if(listDataCoffee.size < _size) {
                        // TODO : add new data
                        listDataCoffee.add(_evaluate)
                    } else {
                        // TODO : update data
                        listDataCoffee[_inx] = _evaluate
                    }
                }
                Log.d(TAG, listDataCoffee.size.toString())
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

    }
}
