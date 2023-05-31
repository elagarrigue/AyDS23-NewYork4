package ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy

import ayds.lisboa1.lastfm.LASTFM_IMAGE
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import ayds.newYork4.artist.external.entities.Artist
import ayds.lisboa1.lastfm.LastFMArtistData
import ayds.newYork4.artist.external.entities.NY_TIMES_LOGO_URL
import ayds.winchester3.wikiartist.artist.externalWikipedia.WIKIPEDIA_LOGO_URL
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaArtist

class NYTimesArtistToCardResolver {
    fun resolve(artist: Artist.NYTimesArtist?): Card? {
        return artist?.let { createNYTimesArtistCard(it) }
    }

    private fun createNYTimesArtistCard(nyTimesArtist: Artist.NYTimesArtist): Card {
        return Card(
            nyTimesArtist.info,
            nyTimesArtist.url,
            Source.NYTimes,
            NY_TIMES_LOGO_URL
        )
    }
}

class WikipediaArtistToCardResolver {
    fun resolve(artist: WikipediaArtist?): Card? {
        return artist?.let { createWikipediaArtistCard(it) }
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


class LastFMArtistToCardResolver {
    fun resolve(artist: LastFMArtistData?): Card? {
        return artist?.let { createLastFMArtistCard(it) }
    }

    private fun createLastFMArtistCard(lastFMArtist: LastFMArtistData): Card {
        return Card(
            lastFMArtist.artisInfo,
            lastFMArtist.artistUrl,
            Source.LastFM,
            LASTFM_IMAGE
        )
    }
}