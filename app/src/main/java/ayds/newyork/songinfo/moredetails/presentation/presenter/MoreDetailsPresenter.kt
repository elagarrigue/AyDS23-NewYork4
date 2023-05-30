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
    override val uiStateObservable = Subject<MoreDetailsUiState>()
    override var uiState = MoreDetailsUiState()

    override fun updateArtistCards(artistName: String) {
        val cards = getCards(artistName)
        if(cards.isEmpty()){
            updateNoResultsUiState()
        } else {
            for (card in cards){
                updateCardUiState(card, artistName)
            }
        }
        uiStateObservable.notify(uiState)
    }

    private fun getCards(artistName: String): List<Card> {
        return repository.getCardsByArtist(artistName)
    }

    private fun updateCardUiState(card: Card, artistName: String) {
        uiState = uiState.copy(
            cardDescription = cardDescriptionHelper.getCardDescriptionText(card, artistName),
            cardUrl = card.infoUrl,
            source = card.source,
            logoImageUrl = card.sourceLogoUrl,
            actionsEnabled = card.infoUrl != null,
        )
    }

    private fun updateNoResultsUiState() {
        uiState = uiState.copy(
            cardDescription = "",
            cardUrl = "",
            source = null,
            logoImageUrl = "",
            actionsEnabled = false,
        )
    }
}