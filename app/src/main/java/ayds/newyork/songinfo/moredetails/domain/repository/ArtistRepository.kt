package ayds.newyork.songinfo.moredetails.domain.repository

import com.test.artist.external.entities.Artist

interface ArtistRepository {
    fun getArtist(artistName: String) : Artist
}