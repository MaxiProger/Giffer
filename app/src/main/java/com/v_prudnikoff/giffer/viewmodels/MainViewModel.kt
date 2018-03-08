package com.v_prudnikoff.giffer.viewmodels

import android.util.Log
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

    fun onMenuTrending() {
        loadTrendingGifs()
    }

    fun onQuerySubmit(query: String) {
        loadQueryGifs(query)
    }

    private fun loadTrendingGifs() {
            repository!!.getTrendingGifs(25)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        activity.setDataLoaded(it)
                    }, {
                        val errMsg = "Something bad's happened with connection"
                        Log.e("network", errMsg)
                        activity.showErrorMessage(errMsg)
                    })
    }

    private fun loadQueryGifs(query: String) {
        repository!!.getQueryGifs(25, query)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    activity.setDataLoaded(it)
                }, {
                    val errMsg = "Something bad's happened with connection"
                    Log.e("network", errMsg)
                    activity.showErrorMessage(errMsg)
                })
    }

}
