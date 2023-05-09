package ayds.newyork.songinfo.moredetails.data.external.nytimes.artists

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

internal interface NYTimesArtistInfoAPI {

    @GET("articlesearch.json?api-key=fFnIAXXz8s8aJ4dB8CVOJl0Um2P96Zyx")
    fun getArtistInfo(@Query("q") query: String): Call<String>
}