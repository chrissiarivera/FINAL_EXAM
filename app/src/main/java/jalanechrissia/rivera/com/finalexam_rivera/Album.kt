package jalanechrissia.rivera.com.finalexam_rivera

import com.google.gson.annotations.SerializedName

/**
 * Created by Jalane Chrissia on 21/03/2018.
 */
data class Album(
        val name: String,
        val artist: String,
        @SerializedName("#text") val image: String = ""
)