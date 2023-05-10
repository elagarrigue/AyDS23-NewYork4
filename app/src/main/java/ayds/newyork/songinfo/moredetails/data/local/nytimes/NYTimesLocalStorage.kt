package ayds.newyork.songinfo.moredetails.data.local.nytimes

import ayds.newyork.songinfo.moredetails.domain.entities.Artist

interface NYTimesLocalStorage {
    fun insertArtist(artistName: String, artistDescription: String)
    fun getArtistByName(artistName: String) : Artist?
}