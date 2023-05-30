package ayds.newyork.songinfo.moredetails.data.repository

import ayds.lisboa1.lastfm.LastFMArtistData
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import ayds.newYork4.artist.external.entities.Artist
import ayds.winchester3.wikiartist.artist.externalWikipedia.WikipediaArtist


class ArtistToCardResolver {
    private val resolvers = mapOf(
        Artist.NYTimesArtist::class to NYTimesArtistToCardResolver(),
        WikipediaArtist::class to WikipediaArtistToCardResolver(),
        LastFMArtistData::class to LastFMArtistToCardResolver()
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
            nyTimesArtist.info,
            nyTimesArtist.url,
            Source.NYTimes,
            nyTimesArtist.logoImageUrl,
            nyTimesArtist.isLocallyStored
        )
    }
}

class WikipediaArtistToCardResolver : ArtistToCardResolverStrategy {
    override fun resolve(artist: Artist?): Card? {
        return artist?.let { createWikipediaArtistCard(it as WikipediaArtist) } // TODO corroborar casteo
    }

    private fun createWikipediaArtistCard(wikipediaArtist: WikipediaArtist): Card {
        return Card(
            wikipediaArtist.description,
            wikipediaArtist.wikipediaURL,
            Source.Wikipedia,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU", // wikipediaArtist.logoUrl instead
            false // should be added to the model
        )
    }
}


class LastFMArtistToCardResolver : ArtistToCardResolverStrategy {
    override fun resolve(artist: Artist?): Card? {
        return artist?.let { createLastFMArtistCard(it as LastFMArtistData) } // TODO corroborar casteo
    }

    private fun createLastFMArtistCard(lastFMArtist: LastFMArtistData): Card {
        return Card(
            lastFMArtist.artisInfo,
            lastFMArtist.artistUrl,
            Source.LastFM,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU", // lastFM.logoUrl instead
            false // should be added to the model
        )
    }
}

