package ayds.newyork.songinfo.moredetails.data.external.nytimes

import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo

interface NYTimesArtistInfoService {
    fun getArtistInfo(artistName: String): ArtistInfo
}
