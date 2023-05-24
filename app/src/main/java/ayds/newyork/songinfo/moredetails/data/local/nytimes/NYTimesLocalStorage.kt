package ayds.newyork.songinfo.moredetails.data.local.nytimes

import com.test.artist.external.entities.Artist.NYTimesArtist

interface NYTimesLocalStorage {
    fun insertArtist(artistName: String, artistDescription: String)
    fun getArtistByName(artistName: String) : NYTimesArtist?
}