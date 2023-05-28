package ayds.newyork.songinfo.moredetails.data

import ayds.newyork.songinfo.moredetails.domain.repository.CardRepository
import ayds.newyork.songinfo.moredetails.data.local.nytimes.CardLocalStorage
import com.test.artist.external.NYTimesArtistService
import com.test.artist.external.entities.Artist
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception

class CardRepositoryTest {

    private val cardLocalStorage: CardLocalStorage = mockk(relaxUnitFun = true)
    private val nyTimesArtistService: NYTimesArtistService = mockk(relaxUnitFun = true)

    private val cardRepository: CardRepository by lazy {
        CardRepositoryImpl(cardLocalStorage, nyTimesArtistService)
    }

    @Test
    fun `given non existing artist by name should return empty artist`() {
        every { cardLocalStorage.getCards("artistName") } returns null
        every { nyTimesArtistService.getArtist("artistName") } returns null

        val result = cardRepository.getCardByArtist("artistName")

        assertEquals(Artist.EmptyArtist, result)
    }

    @Test
    fun `given existing artist by name should return artist and mark it as local`() {
        val artist = Artist.NYTimesArtist("url", "info", false)
        every { cardLocalStorage.getArtistByName("artistName") } returns artist

        val result = cardRepository.getCardByArtist("artistName")

        assertEquals(artist, result)
        assertTrue(artist.isLocallyStored)
    }

    @Test
    fun `given non existing artist by name should get the artist and store it`() {
        val artist = Artist.NYTimesArtist("url", "info", false)
        every { cardLocalStorage.getArtistByName("artistName") } returns null
        every { nyTimesArtistService.getArtist("artistName") } returns artist

        val result = cardRepository.getCardByArtist("artistName")

        assertEquals(artist, result)
        assertFalse(artist.isLocallyStored)
        verify { cardLocalStorage.insertArtist("artistName", artist.info!!) }
    }

    @Test
    fun `given service exception should return empty artist`() {
        every { cardLocalStorage.getArtistByName("artistName") } returns null
        every { nyTimesArtistService.getArtist("artistName") } throws mockk<Exception>()

        val result = cardRepository.getCardByArtist("artistName")

        assertEquals(Artist.EmptyArtist, result)
    }
}