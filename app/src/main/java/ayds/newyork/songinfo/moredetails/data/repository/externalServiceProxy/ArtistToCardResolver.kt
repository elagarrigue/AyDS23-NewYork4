package ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import ayds.newYork4.artist.external.entities.Artist
import ayds.lisboa1.lastfm.LastFMArtistData
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
            nyTimesArtist.logoImageUrl,
            nyTimesArtist.isLocallyStored
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
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU", // wikipediaArtist.logoUrl instead
            false // should be added to the model
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
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU", // lastFM.logoUrl instead
            false // should be added to the model
        )
    }
}