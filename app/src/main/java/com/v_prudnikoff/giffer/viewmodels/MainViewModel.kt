package com.v_prudnikoff.giffer.viewmodels

import com.v_prudnikoff.giffer.helpers.Repository
import com.v_prudnikoff.giffer.views.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val activity: MainActivity) {

    private var repository: Repository? = null

    init {
        repository = Repository()
    }

    fun onCreate() {
        loadTrendingGifs()
    }

    private fun loadTrendingGifs() {
        repository!!.getTrendingGifs(25)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    activity.setDataChanged(it)
                }
    }

    fun loadQueryGifs(query: String) {
        repository!!.getQueryGifs(25, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    activity.setDataChanged(it)
                }
    }

}
