package ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import ayds.winchester3.wikiartist.artist.externalWikipedia.WIKIPEDIA_LOGO_URL
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaArtist

class WikipediaServiceProxy(
    private val wikipediaArtistService: WikipediaService,
) : ServiceProxy {
    override fun getCard(artistName: String): Card? {
        val wikipediaArtist = wikipediaArtistService.getArtist(artistName)
        return wikipediaArtist?.let { createWikipediaArtistCard(wikipediaArtist)}
    }
    private fun createWikipediaArtistCard(wikipediaArtist: WikipediaArtist): Card {
        return Card(
            wikipediaArtist.description,
            wikipediaArtist.wikipediaURL,
            Source.Wikipedia,
            WIKIPEDIA_LOGO_URL
        )
    }
}