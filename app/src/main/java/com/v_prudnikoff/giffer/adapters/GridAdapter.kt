package com.v_prudnikoff.giffer.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.v_prudnikoff.giffer.R
import com.v_prudnikoff.giffer.models.GifModel
import io.reactivex.Observable
import pl.droidsonroids.gif.GifImageView


class GridAdapter(private val context: Context, private val data: Array<GifModel>) : BaseAdapter() {

    override fun getCount(): Int {
        return 100
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val gifView = parent.findViewById<GifImageView>(R.id.gifRaw)
        gifView.scaleType = ImageView.ScaleType.CENTER_CROP
        gifView.setImageResource(R.drawable.giff)
        return gifView
    }
}