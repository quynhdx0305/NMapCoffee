package com.quynhdx.mapcoffee2.view

import android.animation.Animator
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.firebase.database.*
import com.google.gson.Gson
import com.quynhdx.mapcoffee2.R
import com.quynhdx.mapcoffee2.model.ListCoffee_
import kotlinx.android.synthetic.main.activity_splash.*

private const val TAG = "Splash ==="
const val NOT_YET = 0
const val DONE = 1

class Splash : AppCompatActivity() {

    @Volatile private var check = NOT_YET
    val gson = Gson()
    var listDataCoffee : ArrayList<ListCoffee_> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("list coffee")

        playAnimation()
        listenDataChange(myRef)
    }

    private  fun listenDataChange(myRef: DatabaseReference) {
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
                check = DONE
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }

    private fun playAnimation() {

        animation_splash.setAnimation("world-locations.json")

        animation_splash.addAnimatorListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {}

            override fun onAnimationEnd(p0: Animator?) {

                when(check) {
                    DONE -> {
                        val intent = Intent(this@Splash, MapsActivity::class.java)
                        intent.putExtra("listDataCoffee", listDataCoffee)
                        startActivity( intent )
                        finish()
                    }
                    NOT_YET -> animation_splash.playAnimation()
                }

            }

            override fun onAnimationCancel(p0: Animator?) {}

            override fun onAnimationStart(p0: Animator?) {}

        })

        animation_splash.playAnimation()
    }
}
