package ayds.newyork.songinfo.moredetails.data

import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesArtistService
import ayds.newyork.songinfo.moredetails.data.local.nytimes.NYTimesLocalStorage
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception

class SongRepositoryTest {

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
    fun `given existing artist by name should return artist`() {
        val artist: NYTimesArtist = mockk()
        every { nyTimesLocalStorage.getArtistByName("artistName") } returns artist

        val result = artistRepository.getArtist("artistName")

        assertEquals(artist, result)
    }

    @Test
    fun `given existing artist by name should return artist and mark it as local`() {
        val artist = NYTimesArtist("url", "info", false)
        every { nyTimesLocalStorage.getArtistByName("artistName") } returns artist

        val result = artistRepository.getArtist("artistName")

        assertEquals(artist, result)
        assertTrue(artist.isLocallyStored)
    }

    @Test
    fun `given non existing artist by name should get the artist and store it`() {
        val artist = NYTimesArtist("url", "info", false)
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