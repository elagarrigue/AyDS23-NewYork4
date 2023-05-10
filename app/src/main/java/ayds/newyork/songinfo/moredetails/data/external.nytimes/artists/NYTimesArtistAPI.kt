package ayds.newyork.songinfo.moredetails.data.external.nytimes.artists

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NYTimesArtistAPI {

    @GET("articlesearch.json?api-key=fFnIAXXz8s8aJ4dB8CVOJl0Um2P96Zyx")
    fun getArtist(
        @Query("q") artist: String
    ): Call<String>
}