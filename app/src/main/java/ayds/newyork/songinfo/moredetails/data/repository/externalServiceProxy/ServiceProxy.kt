package ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy

import ayds.newyork.songinfo.moredetails.domain.entities.Card

interface ServiceProxy {
    fun getCard(artistName: String): Card
}







