package com.v_prudnikoff.giffer.views

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.v_prudnikoff.giffer.R
import com.v_prudnikoff.giffer.adapters.GridAdapter
import com.v_prudnikoff.giffer.helpers.Repository
import com.v_prudnikoff.giffer.models.GifModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import pl.droidsonroids.gif.GifDrawable


class GridFragment : Fragment() {

    private var gifsData: Array<GifModel>? = null
    private var gridAdapter: GridAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val gridView = container!!.findViewById<GridView>(R.id.mainGridView)
        gridView.numColumns = 6
        gridAdapter = GridAdapter(activity, gifsData!!)
        gridView.adapter = GridAdapter(activity, gifsData!!)
        Repository().getTrendingGifs(20)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
            gifsData = it
            gridAdapter!!.notifyDataSetChanged()

        }
        return inflater!!.inflate(R.layout.fragment_grid, container, false)
    }

    companion object {
        fun newInstance(): Fragment {
            return GridFragment()
        }
    }
}

