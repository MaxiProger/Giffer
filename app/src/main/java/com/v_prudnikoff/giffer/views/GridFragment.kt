package com.v_prudnikoff.giffer.views

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import com.v_prudnikoff.giffer.R
import com.v_prudnikoff.giffer.adapters.GridAdapter
import com.v_prudnikoff.giffer.helpers.ScreenHelper
import com.v_prudnikoff.giffer.helpers.ScreenHelper.Companion.COLUMN_NUM
import com.v_prudnikoff.giffer.interfaces.DataInteface
import com.v_prudnikoff.giffer.models.GifModel
import kotlinx.android.synthetic.main.app_bar_main.*


class GridFragment : Fragment(), DataInteface {

    private var gridAdapter: GridAdapter? = null
    private var gridView: GridView? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater!!.inflate(R.layout.fragment_grid, container, false)
        gridView = rootView.findViewById(R.id.mainGridView) as GridView
        gridView!!.numColumns = COLUMN_NUM
        gridView!!.stretchMode = GridView.STRETCH_COLUMN_WIDTH
        gridView!!.columnWidth = ScreenHelper().getScreenWidth() / COLUMN_NUM
        return rootView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        gridAdapter = GridAdapter(activity)
        gridView!!.adapter = gridAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.toolbar.title = resources.getString(R.string.trending)
    }

    override fun setDataChanged(data: Array<GifModel>) {
        gridAdapter!!.data = data
        gridAdapter!!.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): Fragment {
            return GridFragment()
        }
    }
}

