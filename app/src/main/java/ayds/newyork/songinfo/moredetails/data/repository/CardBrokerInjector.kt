package ayds.newyork.songinfo.moredetails.data.repository

import ayds.lisboa1.lastfm.LastFMInjector
import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.ArtistProxy
import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.NYTimesArtistProxy
import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.LastFMArtistProxy
import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.WikipediaArtistProxy
import ayds.newYork4.artist.external.artists.NYTimesArtistInjector
import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.LastFMArtistToCardResolver
import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.NYTimesArtistToCardResolver
import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.WikipediaArtistToCardResolver
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaInjector

object CardBrokerInjector {
    private lateinit var nyTimesArtistProxy: ArtistProxy
    private lateinit var wikipediaArtistProxy: ArtistProxy
    private lateinit var lastFMArtistProxy: ArtistProxy
    private lateinit var lastFMArtistToCardResolver: LastFMArtistToCardResolver
    private lateinit var nyTimesArtistToCardResolver: NYTimesArtistToCardResolver
    private lateinit var wikipediaArtistToCardResolver: WikipediaArtistToCardResolver
    private lateinit var cardBroker: CardBroker

    fun init() {
        initArtistToCardResolvers()
        initArtistProxies()
        initCardBroker()
    }

    private fun initArtistProxies() {
        nyTimesArtistProxy = NYTimesArtistProxy(
            NYTimesArtistInjector.nyTimesArtistService,
            nyTimesArtistToCardResolver
        )

        wikipediaArtistProxy = WikipediaArtistProxy(
            WikipediaInjector.wikipediaService,
            wikipediaArtistToCardResolver
        )
        lastFMArtistProxy = LastFMArtistProxy(
            LastFMInjector.getLastFMService(),
            lastFMArtistToCardResolver
        )
    }

    private fun initArtistToCardResolvers() {
        lastFMArtistToCardResolver = LastFMArtistToCardResolver()
        nyTimesArtistToCardResolver = NYTimesArtistToCardResolver()
        wikipediaArtistToCardResolver = WikipediaArtistToCardResolver()
    }

    private fun initCardBroker() {
        cardBroker = CardBroker(nyTimesArtistProxy, wikipediaArtistProxy, lastFMArtistProxy)
    }

    fun getCardBroker(): CardBroker {
        return cardBroker
    }
}
