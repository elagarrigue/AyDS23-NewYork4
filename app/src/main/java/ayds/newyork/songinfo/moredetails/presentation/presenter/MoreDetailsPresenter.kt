package ayds.newyork.songinfo.moredetails.presentation.presenter

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import ayds.newyork.songinfo.moredetails.presentation.view.CardDescriptionHelper
import ayds.newyork.songinfo.moredetails.domain.repository.CardRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsPresenter {
    var newYorkTimesUiState: MoreDetailsUiState
    var wikipediaUiState: MoreDetailsUiState
    var lastFmUiState: MoreDetailsUiState
    val newYorkTimesUiStateObservable: Observable<MoreDetailsUiState>
    val wikipediaUiStateObservable: Observable<MoreDetailsUiState>
    val lastFmUiStateObservable: Observable<MoreDetailsUiState>

    fun updateArtistCards(artistName: String)
}

class MoreDetailsPresenterImpl(
    private val repository: CardRepository,
    private val cardDescriptionHelper: CardDescriptionHelper
): MoreDetailsPresenter {
    override var newYorkTimesUiState = MoreDetailsUiState()
    override var wikipediaUiState = MoreDetailsUiState()
    override var lastFmUiState = MoreDetailsUiState()
    override val newYorkTimesUiStateObservable = Subject<MoreDetailsUiState>()
    override val wikipediaUiStateObservable = Subject<MoreDetailsUiState>()
    override val lastFmUiStateObservable = Subject<MoreDetailsUiState>()

    override fun updateArtistCards(artistName: String) {
        val cards = getCards(artistName)
        updateNoResultsUiState()
        for (card in cards){
            updateCardUiState(card, artistName)
        }
        newYorkTimesUiStateObservable.notify(newYorkTimesUiState)
        wikipediaUiStateObservable.notify(wikipediaUiState)
        lastFmUiStateObservable.notify(lastFmUiState)
    }

    private fun getCards(artistName: String): List<Card> {
        return repository.getCardsByArtist(artistName)
    }

    private fun updateCardUiState(card: Card, artistName: String) {
        when(card.source){
            Source.NYTimes -> newYorkTimesUiState = newYorkTimesUiState.copy(
                cardDescription = cardDescriptionHelper.getCardDescriptionText(card, artistName),
                cardUrl = card.infoUrl,
                source = card.source,
                logoImageUrl = card.sourceLogoUrl,
                actionsEnabled = card.infoUrl != null,
            )
            Source.Wikipedia -> wikipediaUiState = wikipediaUiState.copy(
                cardDescription = cardDescriptionHelper.getCardDescriptionText(card, artistName),
                cardUrl = card.infoUrl,
                source = card.source,
                logoImageUrl = card.sourceLogoUrl,
                actionsEnabled = card.infoUrl != null,
            )
            Source.LastFM -> lastFmUiState = lastFmUiState.copy(
                cardDescription = cardDescriptionHelper.getCardDescriptionText(card, artistName),
                cardUrl = card.infoUrl,
                source = card.source,
                logoImageUrl = card.sourceLogoUrl,
                actionsEnabled = card.infoUrl != null,
            )
        }
    }

    private fun updateNoResultsUiState() {
        newYorkTimesUiState = newYorkTimesUiState.copy(
            cardDescription = "No results.",
            cardUrl = null,
            source = null,
            logoImageUrl = "",
            actionsEnabled = false,
        )
        wikipediaUiState = wikipediaUiState.copy(
            cardDescription = "No results.",
            cardUrl = null,
            source = null,
            logoImageUrl = "",
            actionsEnabled = false,
        )
        lastFmUiState = lastFmUiState.copy(
            cardDescription = "No results.",
            cardUrl = null,
            source = null,
            logoImageUrl = "",
            actionsEnabled = false,
        )
    }
}