package ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy

import ayds.lisboa1.lastfm.LastFMService
import ayds.newyork.songinfo.moredetails.data.repository.ArtistToCardResolver
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newYork4.artist.external.NYTimesArtistService
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService

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


class WikipediaArtistProxy(
    private val wikipediaArtistService: WikipediaService,
    private val artistToCardResolver: ArtistToCardResolver
) : ArtistProxy {
    override fun getCard(artistName: String): Card? {
        val wikipediaArtist = wikipediaArtistService.getArtist(artistName)
        val artistCard = artistToCardResolver.resolve(wikipediaArtist)
        return artistCard
    }
}


class LastFMArtistProxy(
    private val lastFMArtistService: LastFMService,
    private val artistToCardResolver: ArtistToCardResolver
) : ArtistProxy {
    override fun getCard(artistName: String): Card? {
        val lastFMArtist = lastFMArtistService.getArtistData(artistName)
        val artistCard = artistToCardResolver.resolve(lastFMArtist)
        return artistCard
    }
}
