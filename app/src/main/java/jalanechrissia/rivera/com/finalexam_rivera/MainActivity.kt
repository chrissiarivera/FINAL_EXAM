package jalanechrissia.rivera.com.finalexam_rivera

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var mEtName: String? = null
    private var mAlbumList = ArrayList<Album>()
    private var mRecyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        mRecyclerView!!.layoutManager = LinearLayoutManager(this)

        etName?.setOnEditorActionListener (object : TextView.OnEditorActionListener {
                override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        mEtName = etName.text.toString()
                        progressBar.visibility = View.VISIBLE

                        for(i in 1..50){
                            doAsync {
                                val resultJson = URL("http://ws.audioscrobbler.com/2.0/?method=album.search&album=$mEtName&api_key=2255fb38ce178d2811f68f9bbb971da1&format=json").readText()
                                val jsonObject = JSONObject(resultJson)

                                val albumName = jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(i).getString("name")
                                val artistName = jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(i).getString("artist")
                                val imgLink = jsonObject.getJSONObject("results").getJSONObject("albummatches").getJSONArray("album").getJSONObject(i).getJSONArray("image").getJSONObject(2).getString("#text")

                                if(imgLink == null){

                                }

                                uiThread {
                                    mAlbumList.add(Album(albumName, artistName, imgLink))
                                    mRecyclerView!!.adapter = AlbumAdapter(mAlbumList)
                                }
                            }
                        }
//
//                        for(i in 1..50) {
//                            doAsync {
//                                val url = "http://ws.audioscrobbler.com/2.0/?method=album.search&album=$mEtName&api_key=2255fb38ce178d2811f68f9bbb971da1&format=json"
//                                val request = Request.Builder().url(url).build()
//                                val client = OkHttpClient.Builder()
//                                        .connectTimeout(60, TimeUnit.SECONDS)
//                                        .writeTimeout(120, TimeUnit.SECONDS)
//                                        .readTimeout(60, TimeUnit.SECONDS)
//                                        .build()
//
//                                client.newCall(request).enqueue(object : Callback {
//                                    override fun onFailure(call: Call?, e: IOException?) {
//                                        Log.e("Last.fm", "Failed to fetch album", e)
//                                    }
//
//                                    override fun onResponse(call: Call?, response: Response?) {
//                                        if (response != null && response.isSuccessful) {
//                                            val json = response.body()?.string()
//                                            val albumGson = GsonBuilder().create()
//                                            val albumFeed = albumGson.fromJson(json, Return::class.java)
//
//                                            uiThread {
//                                                mAlbumList.add(Album(albumFeed.results.albummatches.name, albumFeed.results.albummatches.artist, albumFeed.results.albummatches.image))
//                                                val adapter = AlbumAdapter(mAlbumList)
//                                                mRecyclerView?.adapter = adapter
//                                            }
//                                        }
//                                    }
//                                })
//                            }
//                        }
                        return true
                    }
                    return false
                }
        })
    }
}
