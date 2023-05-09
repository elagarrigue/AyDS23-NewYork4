package ayds.newyork.songinfo.moredetails.data.external.nytimes.artists

import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesArtistInfoService
import ayds.newyork.songinfo.moredetails.data.local.nytimes.ArtistInfoRepositoryImpl
import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import com.google.gson.Gson
import com.google.gson.JsonObject
import retrofit2.Response

internal class NYTimesArtistInfoServiceImpl(
    private val nyTimesApi: NYTimesArtistInfoAPI,
    private val nyTimesToArtistInfoResolver: NYTimesToArtistInfoResolver
) : NYTimesArtistInfoService {
    override fun getArtistInfo(artistName: String): ArtistInfo {
        val callResponse = getArtistInfoFromService(artistName)
        return nyTimesToArtistInfoResolver.getArtistInfoFromExternalData(callResponse.body(), artistName)
    }

    private fun getArtistInfoFromService(artistName: String): Response<String> {
        return nyTimesApi.getArtistInfo(artistName).execute()
    }
}