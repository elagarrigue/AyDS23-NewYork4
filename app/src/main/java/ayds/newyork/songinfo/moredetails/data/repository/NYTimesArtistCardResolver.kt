package ayds.newyork.songinfo.moredetails.data.repository

import ayds.newYork4.artist.external.entities.Artist
import ayds.newYork4.artist.external.entities.NY_TIMES_LOGO_URL
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source

class NYTimesArtistResolver : ArtistCardResolver {
    fun resolve(artist:  Artist.NYTimesArtist?): Card? {
        return artist?.let { createNYTimesArtistCard(it) }
    }

    private fun createNYTimesArtistCard(nyTimesArtist: Artist.NYTimesArtist): Card {
        return Card(
            nyTimesArtist.info,
            nyTimesArtist.url,
            Source.NYTimes,
            NY_TIMES_LOGO_URL
        )
    }
}