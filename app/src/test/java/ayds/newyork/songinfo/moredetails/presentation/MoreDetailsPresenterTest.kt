package ayds.newyork.songinfo.moredetails.presentation

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.repository.CardRepository
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenterImpl
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsUiState
import ayds.newyork.songinfo.moredetails.presentation.view.CardDescriptionHelper

import io.mockk.*
import org.junit.Test

class MoreDetailsPresenterTest {
    private val artistInfoRepository: CardRepository= mockk()
    private val artistCardHelper:CardDescriptionHelper= mockk()
    private val presenter = MoreDetailsPresenterImpl(artistInfoRepository,artistCardHelper)

    @Test
    fun `on fetch should notify subscribers with otherInfoUiState`() {
        val cards: List<Card> = mockk()

        val otherInfoUiStateTester: (MoreDetailsUiState) -> Unit = mockk(relaxed = true)
        presenter.uiStateObservable.subscribe {
            otherInfoUiStateTester(it)
        }

        every { artistInfoRepository.getCardsByArtist("artistName") } returns cards

        val otherInfoUiState = MoreDetailsUiState(
           cards
        )
        presenter.updateArtistCards("artistName")

        verify { otherInfoUiStateTester(otherInfoUiState) }
    }
}