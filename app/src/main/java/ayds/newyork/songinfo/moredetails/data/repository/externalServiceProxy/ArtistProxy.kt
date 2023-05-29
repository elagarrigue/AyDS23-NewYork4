package ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy

import ayds.newyork.songinfo.moredetails.data.repository.ArtistToCardResolver
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import com.test.artist.external.NYTimesArtistService

interface ArtistProxy {
    fun getCard(artistName: String): Card?
}

class NYTimesArtistProxy(
    private val nyTimesArtistService: NYTimesArtistService,
    private val artistToCardResolver: ArtistToCardResolver
) : ArtistProxy {
    override fun getCard(artistName: String): Card? {
        val nyTimesArtist = nyTimesArtistService.getArtist(artistName)
        val artistCard = artistToCardResolver.resolve(nyTimesArtist)
        return artistCard
    }
}

/*
class WikipediaArtistProxy(
    private val wikipediaArtistService: WikipediaArtistService,
    private val artistToCardResolver: ArtistToCardResolver
) : ArtistProxy {
    override fun getCard(artistName: String): Card? {
        val wikipediaArtist = wikipediaArtistService.getArtist(artistName)
        val artistCard = artistToCardResolver.resolve(wikipediaArtist)
        return artistCard
    }
}
*/
/*
class LastFMArtistProxy(
    private val lastFMArtistService: LastFMArtistService,
    private val artistToCardResolver: ArtistToCardResolver
) : ArtistProxy {
    override fun getCard(artistName: String): Card? {
        val lastFMArtist = lastFMArtistService.getArtist(artistName)
        val artistCard = artistToCardResolver.resolve(lastFMArtist)
        return artistCard
    }
}
*/