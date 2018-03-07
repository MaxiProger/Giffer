package com.v_prudnikoff.giffer.helpers

import android.util.Log
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import com.v_prudnikoff.giffer.models.GifModel
import io.reactivex.Observable
import org.json.JSONObject
import java.io.BufferedReader

class Repository {

    private val API_KEY = "DFizeBNLOuetmquCCDqhBXrrv8SDOlOV"
    private var numOfGifs = 0

    fun getTrendingGifs(numOfGifs: Int): Observable<Array<GifModel>> {
        val giffyEndpoint = URL("https://api.giphy.com/v1/gifs/trending")
        this.numOfGifs = numOfGifs
        val connection = giffyEndpoint.openConnection() as HttpsURLConnection
        connection.setRequestProperty("api_key", API_KEY)
        connection.setRequestProperty("limit", numOfGifs.toString())
        connection.setRequestProperty("rating", "pg")
        connection.setRequestProperty("fmt", "json")
        return Observable.fromCallable {
            if (connection.responseCode == 200) {
                val responseBody = connection.inputStream
                val responseBodyReader = InputStreamReader(responseBody, "UTF-8")
                parseJSON(getJSON(responseBodyReader))
            } else {
                Log.i("REST", "Something bad's happened in the  response")
                emptyArray()
            }
        }
    }

    fun getQueryGifs(numOfGifs: Int, query: String): Observable<Array<GifModel>> {
        val giffyEndpoint = URL("https://api.giphy.com/v1/gifs/search")
        this.numOfGifs = numOfGifs
        val connection = giffyEndpoint.openConnection() as HttpsURLConnection
        connection.setRequestProperty("api_key", API_KEY)
        connection.setRequestProperty("Q", query)
        return Observable.fromCallable {
            if (connection.responseCode == 200) {
                val responseBody = connection.inputStream
                val responseBodyReader = InputStreamReader(responseBody, "UTF-8")
                parseJSON(getJSON(responseBodyReader))
            } else {
                Log.i("REST", "Something bad's happened in the  response")
                emptyArray()
            }
        }
    }

    private fun getJSON(responseBodyReader: InputStreamReader): JSONObject {
        val reader = BufferedReader(responseBodyReader, 8)
        val sb = StringBuilder()
        var line: String?
        do {
            line = reader.readLine()
            if (line != null)
                sb.append(line + "\n")
        } while (line != null)
        return JSONObject(sb.toString())
    }

    private fun parseJSON(root: JSONObject): Array<GifModel> {
        val gifsArrayList = arrayListOf<GifModel>()
        val gifs = root.getJSONArray("data")
        for (i in 0..(gifs.length() - 1)) {
            val gifData = gifs.getJSONObject(i)
            val gifImage = gifData.getJSONObject("images")
            val gifSmall = gifImage.getJSONObject("fixed_width")
            val url = gifSmall.get("url").toString()
            gifsArrayList.add(GifModel(url))
        }
        return gifsArrayList.toTypedArray()
    }

}
