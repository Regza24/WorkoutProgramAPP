package com.example.workoutprogramapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.workoutprogramapp.Item
import com.example.workoutprogramapp.R
import com.squareup.picasso.Picasso


class ItemAdapter(val pCtx: Context, val layoutResId: Int, val itemList: List<Item>): ArrayAdapter<Item>(pCtx, layoutResId, itemList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(pCtx)

        val view: View = layoutInflater.inflate(layoutResId, null)

        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvTime: TextView = view.findViewById(R.id.tv_time)
        val ivIcon: ImageView = view.findViewById(R.id.iv_icon)

        val item = itemList[position]

        Picasso.with(pCtx).load(item.imageUrl.toString()).into(ivIcon)
        tvName.text = item.name
        tvTime.text = item.time

        return view
    }
}