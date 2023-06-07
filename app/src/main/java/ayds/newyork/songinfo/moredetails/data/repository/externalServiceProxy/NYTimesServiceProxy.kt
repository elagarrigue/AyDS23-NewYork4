package ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy

import ayds.newYork4.artist.external.NYTimesArtistService
import ayds.newYork4.artist.external.entities.Artist
import ayds.newYork4.artist.external.entities.NY_TIMES_LOGO_URL
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source

class NYTimesServiceProxy(
    private val nyTimesArtistService: NYTimesArtistService,
) : ServiceProxy {
    override fun getCard(artistName: String): Card? {
        val nyTimesArtist = nyTimesArtistService.getArtist(artistName)
        return nyTimesArtist?.let { createNYTimesArtistCard(nyTimesArtist) }
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