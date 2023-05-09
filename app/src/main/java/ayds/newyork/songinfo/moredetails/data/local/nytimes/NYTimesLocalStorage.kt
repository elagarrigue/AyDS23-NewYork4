package ayds.newyork.songinfo.moredetails.data.local.nytimes

import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo

interface NYTimesLocalStorage {
    fun insertArtist(artistName: String, artistDescription: String)
    fun getArtistInfoByName(artistName: String) : ArtistInfo?
}