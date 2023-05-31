package ayds.newyork.songinfo.moredetails.data

import ayds.newyork.songinfo.moredetails.domain.repository.CardRepository
import ayds.newyork.songinfo.moredetails.data.local.nytimes.CardLocalStorage
import ayds.newyork.songinfo.moredetails.data.repository.CardRepositoryImpl
import ayds.newyork.songinfo.moredetails.data.repository.CardBroker
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsUiState
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert.*
import org.junit.Test
import java.lang.Exception

class CardRepositoryTest {

    private val cardLocalStorage: CardLocalStorage = mockk(relaxUnitFun = true)
    private val nyTimesArtistService: CardBroker = mockk(relaxUnitFun = true)

    private val cardRepository: CardRepository by lazy {
        CardRepositoryImpl(cardLocalStorage, nyTimesArtistService)
    }

    @Test
    fun `given non existing artist by name should return empty artist`() {

        every { cardLocalStorage.getCards("artistName") } returns emptyList()
        every { nyTimesArtistService.getCards("artistName") } returns emptyList()

        val result = cardRepository.getCardsByArtist("artistName")
        val cardsMoreDetailsUiState = MoreDetailsUiState(emptyList())
        val cardsEmpty=cardsMoreDetailsUiState.cards
        assertEquals(cardsEmpty, result)
    }

    @Test
    fun `given existing artist by name should return artist and mark it as local`() {
        val artistInfo = Card("descripcion", "url", Source.NYTimes,"new York")
        every { cardLocalStorage.getCards("artistName").first() } returns artistInfo

        val result = cardRepository.getCardsByArtist("artistName")

        assertEquals(artistInfo, result.first())
        assertTrue(artistInfo.isLocallyStored)
    }

    @Test
    fun `given non existing artist by name should get the artist and store it`() {
        val artist = Card("descripcion", "url", Source.NYTimes,"new York")
        every { cardLocalStorage.getCards("artistName") } returns emptyList()
        every { nyTimesArtistService.getCards("artistName").first() } returns artist

        val result = cardRepository.getCardsByArtist("artistName")

        assertEquals(artist, result.first())
        assertFalse(artist.isLocallyStored)
        verify { cardLocalStorage.saveCard("artistName", artist) }
    }

    @Test
    fun `given service exception should return empty artist`() {
        every { cardLocalStorage.getCards("artistName") } returns emptyList()
        every { nyTimesArtistService.getCards("artistName") } throws Exception()

        val result = cardRepository.getCardsByArtist("artistName")
        val cardsMoreDetailsUiState = MoreDetailsUiState(emptyList())
        val cardsEmpty=cardsMoreDetailsUiState.cards
        assertEquals(cardsEmpty, result)
    }
}