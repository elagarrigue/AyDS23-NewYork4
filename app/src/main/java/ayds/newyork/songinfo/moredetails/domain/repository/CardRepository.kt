package ayds.newyork.songinfo.moredetails.domain.repository

import ayds.newyork.songinfo.moredetails.domain.entities.Card

interface CardRepository {
    fun getCardByArtist(artistName: String) : List<Card>
}