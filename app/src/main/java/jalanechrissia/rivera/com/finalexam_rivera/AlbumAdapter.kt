package jalanechrissia.rivera.com.finalexam_rivera

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Jalane Chrissia on 21/03/2018.
 */
class PokeAdapter (val albumList: ArrayList<Album>): RecyclerView.Adapter<PokeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.album_list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val album: Album = albumList[position]
        holder?.view?.txtAlbumName?.setText(album.albumName)
        holder?.view?.txtArtistName?.setText(album.artist)
    }

    class ViewHolder (val view: View): RecyclerView.ViewHolder(view){

    }
}