package ayds.newyork.songinfo.moredetails.data.local.nytimes

import ayds.newyork.songinfo.moredetails.domain.entities.Card

interface CardLocalStorage {
    fun saveCard(artistName: String, card: Card)
    fun getCards(artistName: String) : List<Card>
}