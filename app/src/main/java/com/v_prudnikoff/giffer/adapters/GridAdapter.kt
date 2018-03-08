package com.v_prudnikoff.giffer.adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.v_prudnikoff.giffer.models.GifModel
import android.widget.LinearLayout
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.v_prudnikoff.giffer.helpers.ScreenHelper

class GridAdapter(private val context: Context) : BaseAdapter() {

    var data: Array<GifModel>? = null

    override fun getCount(): Int {
        if (data != null)
           return data!!.size
        else return 0
    }

    override fun getItem(position: Int): Any? {
        return null
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imgGif = ImageView(context)
        val scWidth = ScreenHelper().getScreenWidth() / ScreenHelper.COLUMN_NUM
        val layoutParams = LinearLayout.LayoutParams(scWidth, scWidth)
        imgGif.layoutParams = layoutParams
        Glide.with(context)
                .load(data!![position].url)
                .asGif()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgGif)
        return imgGif
    }
}