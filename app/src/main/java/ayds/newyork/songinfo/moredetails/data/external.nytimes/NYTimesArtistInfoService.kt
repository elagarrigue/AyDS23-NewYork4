package ayds.newyork.songinfo.moredetails.data.external.nytimes

import ayds.newyork.songinfo.moredetails.domain.entities.Artist

interface NYTimesArtistService {
    fun getArtist(artistName: String): Artist
}
