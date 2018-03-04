package com.v_prudnikoff.giffer.views

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.v_prudnikoff.giffer.R

class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_info, container, false)
    }

    companion object {
        fun newInstance(): Fragment {
            return InfoFragment()
        }
    }
}
