package ayds.newyork.songinfo.moredetails.presentation

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.repository.CardRepository
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenterImpl
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsUiState
import ayds.newyork.songinfo.moredetails.presentation.view.CardDescriptionHelper

import io.mockk.*
import org.junit.Assert
import org.junit.Test

class MoreDetailsPresenterTest {
    private val artistInfoRepository: CardRepository= mockk()
    private val artistCardHelper:CardDescriptionHelper= mockk()
    private val presenter = MoreDetailsPresenterImpl(artistInfoRepository,artistCardHelper)

    @Test
    fun `on fetch should notify subscribers with MoreDetailsUiState`() {
        val cards: List<Card> = mockk(relaxed = true)

        presenter.uiStateObservable.subscribe {
            uiState -> Assert.assertEquals(uiState.cards, cards)
        }

        every { artistInfoRepository.getCardsByArtist("artistName") } returns cards

        presenter.updateArtistCards("artistName")
    }
}