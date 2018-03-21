package jalanechrissia.rivera.com.finalexam_rivera

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.album_list.view.*

/**
 * Created by Jalane Chrissia on 21/03/2018.
 */
class AlbumAdapter (val albumList: ArrayList<Album>): RecyclerView.Adapter<AlbumAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.album_list, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val album: Album = albumList[position]
        holder?.view?.txtAlbumName?.setText(album.name)
        holder?.view?.txtArtistName?.setText(album.artist)
        val pokeImage = holder!!.view.imgAlbum
        Picasso.with(holder.view.context).load(album.image).into(pokeImage)
    }

    class ViewHolder (val view: View): RecyclerView.ViewHolder(view){

    }
}