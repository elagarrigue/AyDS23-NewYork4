package ayds.newyork.songinfo.moredetails.data.local.nytimes

import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesArtistService
import ayds.newyork.songinfo.moredetails.domain.entities.Artist
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistRepository

internal class ArtistRepositoryImpl(
    private val nyTimesLocalStorage: NYTimesLocalStorage,
    private val nyTimesArtistService: NYTimesArtistService
): ArtistRepository {

    override fun getArtist(artistName: String): Artist {
        var artist = nyTimesLocalStorage.getArtistByName(artistName)
        when {
            artist != null -> markArtistAsLocal(artist)
            else -> {
                artist = nyTimesArtistService.getArtist(artistName)
                nyTimesLocalStorage.insertArtist(artistName, artist.info!!)
            }
        }
        return artist
    }

    private fun markArtistAsLocal(artist: Artist) {
        artist.isLocallyStored = true
    }
}