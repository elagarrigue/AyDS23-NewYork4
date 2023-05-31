package ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy

import ayds.lisboa1.lastfm.LastFMService
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newYork4.artist.external.NYTimesArtistService
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService

interface ArtistProxy {
    fun getCard(artistName: String): Card?
}

class NYTimesArtistProxy(
    private val nyTimesArtistService: NYTimesArtistService,
    private val nyTimesArtistToCardResolver: NYTimesArtistToCardResolver
) : ArtistProxy {
    override fun getCard(artistName: String): Card? {
        val nyTimesArtist = nyTimesArtistService.getArtist(artistName)
        return nyTimesArtistToCardResolver.resolve(nyTimesArtist)
    }
}


class WikipediaArtistProxy(
    private val wikipediaArtistService: WikipediaService,
    private val wikipediaArtistToCardResolver: WikipediaArtistToCardResolver
) : ArtistProxy {
    override fun getCard(artistName: String): Card? {
        val wikipediaArtist = wikipediaArtistService.getArtist(artistName)
        return wikipediaArtistToCardResolver.resolve(wikipediaArtist)
    }
}


class LastFMArtistProxy(
    private val lastFMArtistService: LastFMService,
    private val lastFMArtistToCardResolver: LastFMArtistToCardResolver
) : ArtistProxy {
    override fun getCard(artistName: String): Card? {
        val lastFMArtist = lastFMArtistService.getArtistData(artistName)
        return lastFMArtistToCardResolver.resolve(lastFMArtist)
    }
}