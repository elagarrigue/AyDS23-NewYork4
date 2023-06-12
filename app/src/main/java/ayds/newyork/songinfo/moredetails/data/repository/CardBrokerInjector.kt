package ayds.newyork.songinfo.moredetails.data.repository

import ayds.lisboa1.lastfm.LastFMInjector
import ayds.newyork.songinfo.moredetails.data.repository.external.ServiceProxy
import ayds.newyork.songinfo.moredetails.data.repository.external.NYTimesServiceProxy
import ayds.newyork.songinfo.moredetails.data.repository.external.LastFMServiceProxy
import ayds.newyork.songinfo.moredetails.data.repository.external.WikipediaServiceProxy
import ayds.newYork4.artist.external.artists.NYTimesArtistInjector
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaInjector

object CardBrokerInjector {
    private lateinit var nyTimesServiceProxy: ServiceProxy
    private lateinit var wikipediaServiceProxy: ServiceProxy
    private lateinit var lastFMServiceProxy: ServiceProxy
    private lateinit var cardBroker: CardBroker

    fun init() {
        initArtistProxies()
        initCardBroker()
    }

    private fun initArtistProxies() {
        nyTimesServiceProxy = NYTimesServiceProxy(NYTimesArtistInjector.nyTimesArtistService)
        wikipediaServiceProxy = WikipediaServiceProxy(WikipediaInjector.wikipediaService)
        lastFMServiceProxy = LastFMServiceProxy(LastFMInjector.getLastFMService())
    }

    private fun initCardBroker() {
        val proxies: MutableList<ServiceProxy> = ArrayList()
        proxies.add(nyTimesServiceProxy)
        proxies.add(wikipediaServiceProxy)
        proxies.add(lastFMServiceProxy)
        cardBroker = CardBroker(proxies)
    }

    fun getCardBroker(): CardBroker = cardBroker
}
