package ayds.newyork.songinfo.moredetails.data.external.nytimes.artists

import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import com.google.gson.Gson
import com.google.gson.JsonObject

interface NYTimesToArtistInfoResolver {
    fun getArtistInfoFromExternalData(serviceData: String?): ArtistInfo
}

internal class JsonToArtistInfoResolver() : NYTimesToArtistInfoResolver {
    override fun getArtistInfoFromExternalData(serviceData: String?): ArtistInfo {
        TODO("Not yet implemented")
    }
}