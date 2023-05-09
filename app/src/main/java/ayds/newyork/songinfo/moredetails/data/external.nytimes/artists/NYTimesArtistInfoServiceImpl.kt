package ayds.newyork.songinfo.moredetails.data.external.nytimes.artists

import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesAPI
import ayds.newyork.songinfo.moredetails.domain.repository.NYTimesArtistInfoService
import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import retrofit2.Response

internal class NYTimesArtistInfoServiceImpl(
    private val nyTimesApi: NYTimesAPI,
    private val nyTimesToArtistInfoResolver: NYTimesToArtistInfoResolver
) : NYTimesArtistInfoService {
    override fun getArtistInfo(artistName: String): ArtistInfo {
        val callResponse = getArtistInfoFromService(artistName)
        return nyTimesToArtistInfoResolver.getArtistInfoFromExternalData(callResponse.body())
    }

    private fun getArtistInfoFromService(artistName: String): Response<String> {
        return nyTimesApi.getArtistInfo(artistName).execute()
    }
}