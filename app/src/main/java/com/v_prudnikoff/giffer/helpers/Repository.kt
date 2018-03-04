package com.v_prudnikoff.giffer.helpers

import android.util.Log
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import com.v_prudnikoff.giffer.models.GifModel
import io.reactivex.Observable
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.Channels


class Repository {

    private val API_KEY = "DFizeBNLOuetmquCCDqhBXrrv8SDOlOV"

    fun getTrendingGifs(numOfGifs: Int): Observable<Array<GifModel>> {
        val giffyEndpoint = URL("https://api.giphy.com/v1/gifs/trending")
        val connection = giffyEndpoint.openConnection() as HttpsURLConnection
        connection.setRequestProperty("api_key", API_KEY)
        connection.setRequestProperty("limit", numOfGifs.toString())
        connection.setRequestProperty("rating", "g")
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
            val gifOrigin = gifImage.getJSONObject("original")
            val url = gifOrigin.get("url").toString()
            val urlConnection = URL(url).openConnection()
            urlConnection.connect()
            val contentLength = urlConnection.contentLength
            if (contentLength < 0) {
                throw IOException("Content-Length not present")
            }
            val buffer = ByteBuffer.allocateDirect(contentLength)
            val channel = Channels.newChannel(urlConnection.getInputStream())
            while (buffer.remaining() > 0)
                channel.read(buffer)
            channel.close()
            gifsArrayList.add(GifModel(buffer))
        }
        return gifsArrayList.toTypedArray()
    }

}
