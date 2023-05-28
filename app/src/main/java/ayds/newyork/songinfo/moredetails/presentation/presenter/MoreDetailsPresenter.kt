package ayds.newyork.songinfo.moredetails.presentation.presenter

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.presentation.view.CardDescriptionHelper
import ayds.newyork.songinfo.moredetails.domain.repository.CardRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsPresenter {
    var uiState: MoreDetailsUiState
    val uiStateObservable: Observable<MoreDetailsUiState>

    fun updateArtist(artistName: String)
}

class MoreDetailsPresenterImpl(
        private val repository: CardRepository,
        private val cardDescriptionHelper: CardDescriptionHelper,
        private var cards: List<Card>
): MoreDetailsPresenter {
    override val uiStateObservable = Subject<MoreDetailsUiState>()
    override var uiState = MoreDetailsUiState()
    override fun updateArtist(artistName: String) {
        cards = getArtist(artistName)
        for (card in cards){
            updateUiState(card)
        }
        uiStateObservable.notify(uiState)
    }

    private fun getArtist(artistName: String): List<Card> {
        return repository.getCardByArtist(artistName)
    }

    private fun updateUiState(card: Card) {
        when (card) {
            null -> updateNoResultsUiState(card)
            else -> updateCardUiState(card)
        }
    }

    private fun updateCardUiState(card: Card) {
        uiState = uiState.copy(
            cardDescription = cardDescriptionHelper.getCardDescriptionText(card),
            cardUrl = card.infoUrl,
            source = card.source,
            logoImageUrl = card.sourceLogoUrl,
            actionsEnabled = true,
        )
    }

    private fun updateNoResultsUiState(card: Card) {
        uiState = uiState.copy(
            cardDescription = cardDescriptionHelper.getCardDescriptionText(card),
            cardUrl = "",
            source = null,
            logoImageUrl = "",
            actionsEnabled = false,
        )
    }
}