package ayds.newyork.songinfo.moredetails.domain.repository

import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo

interface NYTimesArtistInfoService {
    fun getArtistInfo(artistName: String) : ArtistInfo
}