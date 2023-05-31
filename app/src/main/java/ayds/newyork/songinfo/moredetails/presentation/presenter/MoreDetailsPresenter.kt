package ayds.newyork.songinfo.moredetails.presentation.presenter

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.presentation.view.CardDescriptionHelper
import ayds.newyork.songinfo.moredetails.domain.repository.CardRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsPresenter {
    var uiState: MoreDetailsUiState
    val uiStateObservable: Observable<MoreDetailsUiState>

    fun updateArtistCards(artistName: String)
}

class MoreDetailsPresenterImpl(
    private val repository: CardRepository,
    private val cardDescriptionHelper: CardDescriptionHelper
): MoreDetailsPresenter {
    override var uiState = MoreDetailsUiState()
    override val uiStateObservable = Subject<MoreDetailsUiState>()

    override fun updateArtistCards(artistName: String) {
        updateNoResultsUiState()
        val cards = getCards(artistName)
        updateUiState(cards, artistName)
        uiStateObservable.notify(uiState)
    }

    private fun updateNoResultsUiState() {
        uiState = uiState.copy(
            cards = emptyList()
        )
    }

    private fun getCards(artistName: String): List<Card> {
        return repository.getCardsByArtist(artistName)
    }

    private fun updateUiState(cards: List<Card>, artistName: String) {
        for (card: Card in cards){
            card.description = cardDescriptionHelper.getCardDescriptionText(card, artistName)
        }
        uiState = uiState.copy(
            cards = cards
        )
    }
}