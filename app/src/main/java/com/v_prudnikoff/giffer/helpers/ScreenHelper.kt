package com.v_prudnikoff.giffer.helpers

import android.content.res.Resources

class ScreenHelper {

    companion object {
        const val COLUMN_NUM = 2
    }

    fun getScreenWidth(): Int {
        return Resources.getSystem().displayMetrics.widthPixels
    }
}
