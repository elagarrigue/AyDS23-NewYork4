package ayds.newyork.songinfo.moredetails.data.repository

import ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy.ServiceProxy
import ayds.newyork.songinfo.moredetails.domain.entities.Card

class CardBroker(private val serviceProxies: List<ServiceProxy>) {
    fun getCards(artistName: String): List<Card> {
        val cards: MutableList<Card> = ArrayList()
        for (proxy in serviceProxies) {
            val card = proxy.getCard(artistName)
            card?.let {
                cards.add(it)
            }
        }
        return cards
    }
}