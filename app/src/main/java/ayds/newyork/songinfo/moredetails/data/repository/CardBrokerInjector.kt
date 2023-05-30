package ayds.newyork.songinfo.moredetails.data.repository

import ayds.lisboa1.lastfm.LastFMInjector
import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.ArtistProxy
import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.NYTimesArtistProxy
import ayds.newYork4.artist.external.artists.NYTimesArtistInjector
import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.LastFMArtistProxy
import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.WikipediaArtistProxy
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaInjector

object CardBrokerInjector {
    private lateinit var nyTimesArtistProxy: ArtistProxy
    private lateinit var wikipediaArtistProxy: ArtistProxy
    private lateinit var lastFMArtistProxy: ArtistProxy
    private lateinit var artistToCardResolver: ArtistToCardResolver
    private lateinit var cardBroker: CardBroker

    fun init() {
        initArtistToCardResolver()
        initArtistProxies()
        initCardBroker()
    }

    private fun initArtistProxies() {
        nyTimesArtistProxy = NYTimesArtistProxy(
            NYTimesArtistInjector.nyTimesArtistService,
            artistToCardResolver
        )

        wikipediaArtistProxy = WikipediaArtistProxy(
            WikipediaInjector.wikipediaService,
            artistToCardResolver
        )
        lastFMArtistProxy = LastFMArtistProxy(
            LastFMInjector.getLastFMService(),
            artistToCardResolver
        )
    }

    private fun initArtistToCardResolver() {
        artistToCardResolver = ArtistToCardResolver()
    }

    private fun initCardBroker() {
        cardBroker = CardBroker(nyTimesArtistProxy, wikipediaArtistProxy, lastFMArtistProxy)
    }

    fun getCardBroker(): CardBroker {
        return cardBroker
    }
}
