package ayds.newyork.songinfo.moredetails.data.local.nytimes

import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist

interface NYTimesLocalStorage {
    fun insertArtist(artistName: String, artistDescription: String)
    fun getArtistByName(artistName: String) : NYTimesArtist?
}