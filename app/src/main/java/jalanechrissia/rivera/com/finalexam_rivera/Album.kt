package jalanechrissia.rivera.com.finalexam_rivera

import com.google.gson.annotations.SerializedName

/**
 * Created by Jalane Chrissia on 21/03/2018.
 */
data class Album(
        val albumName: String,
        val artist: String,
        val image: Image
)

data class Image(
        @SerializedName("mega") val imageURL: String
)