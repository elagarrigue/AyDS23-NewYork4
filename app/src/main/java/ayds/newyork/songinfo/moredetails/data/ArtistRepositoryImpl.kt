package ayds.newyork.songinfo.moredetails.data


import ayds.newyork.songinfo.moredetails.data.local.nytimes.NYTimesLocalStorage
import ayds.newyork.songinfo.moredetails.domain.entities.Artist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.EmptyArtist
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistRepository
import com.test.artist.external.NYTimesArtistService

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
                artist?.let {
                    nyTimesLocalStorage.insertArtist(artistName, artist.info ?: "")
                }
            }
        }
        return artist ?: EmptyArtist
    }

    private fun markArtistAsLocal(artist: NYTimesArtist) {
        artist.isLocallyStored = true
    }
}