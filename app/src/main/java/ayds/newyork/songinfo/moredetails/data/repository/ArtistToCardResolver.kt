package ayds.newyork.songinfo.moredetails.data.repository

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import com.test.artist.external.entities.Artist

class ArtistToCardResolver {
    private val resolvers = mapOf(
        Artist.NYTimesArtist::class to NYTimesArtistToCardResolver(),
        //Artist.WikipediaArtist::class to WikipediaArtistToCardResolver(),
        //Artist.LastFMArtist::class to LastFMArtistToCardResolver()
    )

    fun resolve(artist: Artist?): Card? {
        return artist?.let { getResolver(it)?.resolve(it) }
    }

    private fun getResolver(artist: Artist): ArtistToCardResolverStrategy? {
        return resolvers[artist::class]
    }
}

interface ArtistToCardResolverStrategy {
    fun resolve(artist: Artist?): Card?
}

class NYTimesArtistToCardResolver : ArtistToCardResolverStrategy {
    override fun resolve(artist: Artist?): Card? {
        return artist?.let { createNYTimesArtistCard(it as Artist.NYTimesArtist) } // TODO corroborar casteo
    }

    private fun createNYTimesArtistCard(nyTimesArtist: Artist.NYTimesArtist): Card {
        return Card(
            nyTimesArtist.info ?: "",
            nyTimesArtist.url,
            Source.NYTimes,
            nyTimesArtist.NYTIMESLOGO_URL,
            nyTimesArtist.isLocallyStored
        )
    }
}
/*
class WikipediaArtistToCardResolver : ArtistToCardResolverStrategy {
    override fun resolve(artist: Artist?): Card? {
        return artist?.let { createWikipediaArtistCard(it as Artist.WikipediaArtist) } // TODO corroborar casteo
    }

    private fun createWikipediaArtistCard(wikipediaArtist: Artist.WikipediaArtist): Card {
        return Card(
            wikipediaArtist.info ?: "",
            wikipediaArtist.url,
            Source.Wikipedia,
            "", // wikipedia.logoUrl instead
            wikipediaArtist.isLocallyStored
        )
    }
}
*/
/*
class LastFMArtistToCardResolver : ArtistToCardResolverStrategy {
    override fun resolve(artist: Artist?): Card? {
        return artist?.let { createLastFMArtistCard(it as Artist.LastFMArtist) } // TODO corroborar casteo
    }

    private fun createLastFMArtistCard(lastFMArtist: Artist.LastFMArtist): Card {
        return Card(
            lastFMArtist.info ?: "",
            lastFMArtist.url,
            Source.LastFM,
            "", // lastFM.logoUrl instead
            lastFMArtist.isLocallyStored
        )
    }
}
*/
