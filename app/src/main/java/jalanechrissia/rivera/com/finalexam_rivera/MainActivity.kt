package jalanechrissia.rivera.com.finalexam_rivera

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.album_list.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var mEtName: String? = null
    private var mAlbumList = ArrayList<Album>()
    private val imgSize = "mega"
    private val img = "image"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                mEtName = etName.text.toString()
                progressBar.visibility = View.VISIBLE

                val url = "http://ws.audioscrobbler.com/2.0/?method=artist.search&artist=$mEtName&api_key=2255fb38ce178d2811f68f9bbb971da1&format=json"
                val request = Request.Builder().url(url).build()
                val client = OkHttpClient.Builder()
                        .connectTimeout(60, TimeUnit.SECONDS)
                        .writeTimeout(120, TimeUnit.SECONDS)
                        .readTimeout(60, TimeUnit.SECONDS)
                        .build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        Log.e("Last.fm", "Failed to fetch album", e)
                    }

                    override fun onResponse(call: Call?, response: Response?) {
                        if (response != null && response.isSuccessful) {
                            val json = response.body()?.string()
                            displayResult(json)
                        }
                    }
                })
                true
            } else{
                false
            }
        }
    }

    private fun displayResult(json: String?){
        runOnUiThread {
            val gson = GsonBuilder().create()
            val album = gson.fromJson(json, Album::class.java)

            if (mEtName == album.albumName){
                progressBar.visibility = View.GONE
                val jsonObject = JSONObject(json)
                val albumImg = jsonObject.getJSONObject(img).getString(imgSize)

                mAlbumList.add(Album(album.albumName, album.artist, album.image.imageURL))
                val artistImage = imgAlbum

                Picasso.with(this@MainActivity).load(albumImg).into(artistImage)
            }
        }
    }
}
