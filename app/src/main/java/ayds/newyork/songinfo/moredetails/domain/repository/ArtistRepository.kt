package ayds.newyork.songinfo.moredetails.domain.repository

import ayds.newyork.songinfo.moredetails.domain.entities.Artist

interface ArtistRepository {
    fun getArtist(artistName: String) : Artist
}