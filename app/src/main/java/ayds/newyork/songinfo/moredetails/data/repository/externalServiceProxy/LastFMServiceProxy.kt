package ayds.newyork.songinfo.moredetails.data.repository.externalServiceProxy

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import ayds.lisboa1.lastfm.LastFMArtistData
import ayds.lisboa1.lastfm.LASTFM_IMAGE

class LastFMServiceProxy(
    private val lastFMArtistService: LastFMService,
) : ServiceProxy {
    override fun getCard(artistName: String): Card? {
        val lastFMArtist = lastFMArtistService.getArtistData(artistName)
        return LastFMArtistData?.let { createLastFMArtistCard(lastFMArtist) }
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