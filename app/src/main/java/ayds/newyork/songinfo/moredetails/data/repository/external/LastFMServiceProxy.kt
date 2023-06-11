package ayds.newyork.songinfo.moredetails.data.repository.external

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import ayds.lisboa1.lastfm.LastFMArtistData
import ayds.lisboa1.lastfm.LASTFM_IMAGE
import ayds.lisboa1.lastfm.LastFMService

class LastFMServiceProxy(
    private val lastFMArtistService: LastFMService,
) : ServiceProxy {
    override fun getCard(artistName: String): Card? {
        val lastFMArtist = lastFMArtistService.getArtistData(artistName)
        return lastFMArtist?.let {
            createLastFMArtistCard(it)
        }
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