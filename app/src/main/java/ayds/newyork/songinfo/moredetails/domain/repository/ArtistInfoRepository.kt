package ayds.newyork.songinfo.moredetails.domain.repository

import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo

interface ArtistInfoRepository {
    fun getArtistInfo(artistName: String): ArtistInfo
}
