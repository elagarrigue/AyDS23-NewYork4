package ayds.newyork.songinfo.moredetails.data.repository

import ayds.newyork.songinfo.moredetails.data.local.nytimes.CardLocalStorage
import ayds.newyork.songinfo.moredetails.domain.repository.CardRepository
import ayds.newyork.songinfo.moredetails.domain.entities.Card

internal class CardRepositoryImpl(
    private val cardLocalStorage: CardLocalStorage,
    private val cardBroker: CardBroker
): CardRepository {

    override fun getCardsByArtist(artistName: String): List<Card> {
        var cards = cardLocalStorage.getCards(artistName)
        when {
            cards.isNotEmpty() -> markCardAsLocal(cards)
            else -> try {
                cards = cardBroker.getCards(artistName)
                saveCards(artistName, cards)
            } catch (ioException: Exception) {
                cards = emptyList()
            }
        }
        return cards
    }

    private fun markCardAsLocal(cards: List<Card>) {
        cards.map {
            it.isLocallyStored = true
        }
    }

    private fun saveCards(artistName: String, cards: List<Card>) {
        for(card in cards) {
            cardLocalStorage.saveCard(artistName, card)
        }
    }
}