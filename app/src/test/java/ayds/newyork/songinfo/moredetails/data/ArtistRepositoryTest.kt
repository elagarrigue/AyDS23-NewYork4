package ayds.newyork.songinfo.moredetails.data

import ayds.newyork.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.newyork.songinfo.moredetails.data.local.nytimes.NYTimesLocalStorage
import com.test.artist.external.NYTimesArtistService
import com.test.artist.external.entities.Artist
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception

class ArtistRepositoryTest {

    private val nyTimesLocalStorage: NYTimesLocalStorage = mockk(relaxUnitFun = true)
    private val nyTimesArtistService: NYTimesArtistService = mockk(relaxUnitFun = true)

    private val artistRepository: ArtistRepository by lazy {
        ArtistRepositoryImpl(nyTimesLocalStorage, nyTimesArtistService)
    }

    @Test
    fun `given non existing artist by name should return empty artist`() {
        every { nyTimesLocalStorage.getArtistByName("artistName") } returns null
        every { nyTimesArtistService.getArtist("artistName") } returns null

        val result = artistRepository.getArtist("artistName")

        assertEquals(Artist.EmptyArtist, result)
    }

    @Test
    fun `given existing artist by name should return artist and mark it as local`() {
        val artist = Artist.NYTimesArtist("url", "info", false)
        every { nyTimesLocalStorage.getArtistByName("artistName") } returns artist

        val result = artistRepository.getArtist("artistName")

        assertEquals(artist, result)
        assertTrue(artist.isLocallyStored)
    }

    @Test
    fun `given non existing artist by name should get the artist and store it`() {
        val artist = Artist.NYTimesArtist("url", "info", false)
        every { nyTimesLocalStorage.getArtistByName("artistName") } returns null
        every { nyTimesArtistService.getArtist("artistName") } returns artist

        val result = artistRepository.getArtist("artistName")

        assertEquals(artist, result)
        assertFalse(artist.isLocallyStored)
        verify { nyTimesLocalStorage.insertArtist("artistName", artist.info!!) }
    }

    @Test
    fun `given service exception should return empty artist`() {
        every { nyTimesLocalStorage.getArtistByName("artistName") } returns null
        every { nyTimesArtistService.getArtist("artistName") } throws mockk<Exception>()

        val result = artistRepository.getArtist("artistName")

        assertEquals(Artist.EmptyArtist, result)
    }
}