package ayds.newyork.songinfo.moredetails.data.repository

import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.ArtistProxy
import ayds.newyork.songinfo.moredetails.domain.entities.Card

class CardBroker(
    private val nyTimesArtistProxy: ArtistProxy,
    private val wikipediaArtistProxy: ArtistProxy,
    private val lastFMArtistProxy: ArtistProxy
) {
    fun getCards(artistName: String): List<Card> {
        val cards: MutableList<Card> = ArrayList()

        val nyTimesCard = nyTimesArtistProxy.getCard(artistName)
        if (nyTimesCard != null) {
            cards.add(nyTimesCard)
        }

        val wikiCard = wikipediaArtistProxy.getCard(artistName)
        if (wikiCard != null) {
            cards.add(wikiCard)
        }

        val lastFMCard = lastFMArtistProxy.getCard(artistName)
        if (lastFMCard != null) {
            cards.add(lastFMCard)
        }

        return cards
    }
}