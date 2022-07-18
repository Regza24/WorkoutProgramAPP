package com.example.workoutprogramapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class DescriptionActivity : AppCompatActivity() {
    private lateinit var tvName: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvAdv: TextView
    private lateinit var tvDesc: TextView
    private lateinit var ivFull: ImageView
    private lateinit var ref: DatabaseReference

    companion object{
        const val EXTRA_NAME = "extra name"
        const val EXTRA_ID = "extra id"
        const val EXTRA_TIME = "time"
        const val EXTRA_IMAGE = "img"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_description)

        val id = intent.getStringExtra(EXTRA_ID).toString()
        val name = intent.getStringExtra(EXTRA_NAME)
        val time = intent.getStringExtra(EXTRA_TIME)
        val img = intent.getStringExtra(EXTRA_IMAGE).toString()

        ref = FirebaseDatabase.getInstance().getReference("DescItem").child(id)

        tvName = findViewById(R.id.tv_name_desc)
        tvTime = findViewById(R.id.tv_time)
        tvAdv = findViewById(R.id.tv_adv)
        tvDesc = findViewById(R.id.tv_fulldesc)
        ivFull = findViewById(R.id.iv_desc)

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                if (p0.exists()){
                    Picasso.with(applicationContext).load(img.toString()).into(ivFull)
                    tvName.text = name
                    tvTime.text = time
                    tvAdv.text = p0.child("descAdv").getValue().toString()
                    tvDesc.text = p0.child("descFull").getValue().toString()
                }

            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
}

