package ayds.newyork.songinfo.moredetails.data.repository.external

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import ayds.winchester3.wikiartist.artist.externalWikipedia.WIKIPEDIA_LOGO_URL
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaArtist
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaService

class WikipediaServiceProxy(
    private val wikipediaArtistService: WikipediaService,
) : ServiceProxy {
    override fun getCard(artistName: String): Card? {
        val wikipediaArtist = wikipediaArtistService.getArtist(artistName)
        return wikipediaArtist?.let {
            createWikipediaArtistCard(it)
        }
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