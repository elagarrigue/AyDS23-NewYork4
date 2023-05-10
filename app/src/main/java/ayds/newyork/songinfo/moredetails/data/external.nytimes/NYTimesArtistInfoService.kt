package ayds.newyork.songinfo.moredetails.data.external.nytimes

import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist

interface NYTimesArtistService {
    fun getArtist(artistName: String): NYTimesArtist?
}
