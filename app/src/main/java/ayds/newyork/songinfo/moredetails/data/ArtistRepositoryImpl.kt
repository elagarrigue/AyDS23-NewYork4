package ayds.newyork.songinfo.moredetails.data

import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesArtistService
import ayds.newyork.songinfo.moredetails.data.local.nytimes.NYTimesLocalStorage
import ayds.newyork.songinfo.moredetails.domain.entities.Artist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.EmptyArtist
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
                try {
                    artist = nyTimesArtistService.getArtist(artistName)
                    artist?.let {
                        nyTimesLocalStorage.insertArtist(artistName, it.info ?: "")
                    }
                } catch(ioException : Exception) {
                    artist = null
                }
            }
        }
        return artist ?: EmptyArtist
    }

    private fun markArtistAsLocal(artist: NYTimesArtist) {
        artist.isLocallyStored = true
    }
}