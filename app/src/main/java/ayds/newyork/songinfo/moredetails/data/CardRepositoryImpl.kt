package ayds.newyork.songinfo.moredetails.data

import ayds.newyork.songinfo.moredetails.data.local.nytimes.CardLocalStorage
import ayds.newyork.songinfo.moredetails.domain.repository.CardRepository
import ayds.newyork.songinfo.moredetails.domain.entities.Card

internal class CardRepositoryImpl(
        private val cardLocalStorage: CardLocalStorage,
        private val nyTimesArtistService: NYTimesArtistService //Aca iria broker, por eso no lo refactorizo a card
): CardRepository {

    override fun getCardByArtist(artistName: String): List<Card> {
        var cards = cardLocalStorage.getCards(artistName)
        when {
            !cards.isEmpty() -> markCardAsLocal(cards)
            else -> {
                try {
                    // cards = consigo cards de broker
                    saveCards(artistName, cards)
                } catch (ioException: Exception) {
                    cards = null
                }
            }
        }
        return cards
    }

    private fun markCardAsLocal(cards: List<Card>) {
        cards.map { it.isLocallyStored = true }
    }

    private fun saveCards(artistName: String, cards: List<Card>) {
        for(card in cards) {
            cardLocalStorage.saveCard(artistName, card)
        }
    }
}