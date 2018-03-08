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
        var reader: BufferedReader
        val giffyEndpoint = URL("https://api.giphy.com/v1/gifs/trending?api_key=" + API_KEY
                + "&raiting=pg&limit=" + numOfGifs.toString())
        val connection = giffyEndpoint.openConnection() as HttpsURLConnection
        try {
            return Observable.fromCallable {
                this.numOfGifs = numOfGifs
                connection.connect()
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                val buffer = StringBuffer()
                var line: String?
                do {
                    line = reader.readLine()
                    if (line != null)
                        buffer.append(line + "\n")
                } while (line != null)
                parseJSON(JSONObject(buffer.toString()))
            }
        } catch (ex: Exception) {
            Log.i("REST", "Something bad's happened in the  response")
            ex.printStackTrace()
        }
        finally {
            if (connection != null)
                connection.disconnect()
        }
        return Observable.just(emptyArray())
    }

    fun getQueryGifs(numOfGifs: Int, query: String): Observable<Array<GifModel>> {
        var reader: BufferedReader
        val giffyEndpoint = URL("https://api.giphy.com/v1/gifs/search?api_key=" + API_KEY
                + "&q=" + query + "&raiting=pg&limit=" + numOfGifs.toString())
        val connection = giffyEndpoint.openConnection() as HttpsURLConnection
        try {
            return Observable.fromCallable {
                this.numOfGifs = numOfGifs
                connection.connect()
                val stream = connection.inputStream
                reader = BufferedReader(InputStreamReader(stream))
                val buffer = StringBuffer()
                var line: String?
                do {
                    line = reader.readLine()
                    if (line != null)
                        buffer.append(line + "\n")
                } while (line != null)
                parseJSON(JSONObject(buffer.toString()))
            }
        } catch (ex: Exception) {
            Log.i("REST", "Something bad's happened in the  response")
            ex.printStackTrace()
        }
        finally {
            if (connection != null)
                connection.disconnect()
        }
        return Observable.just(emptyArray())
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
