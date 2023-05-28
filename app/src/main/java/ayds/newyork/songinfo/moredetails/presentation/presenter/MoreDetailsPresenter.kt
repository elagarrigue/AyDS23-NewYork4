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
        private val cardDescriptionHelper: CardDescriptionHelper
): MoreDetailsPresenter {
    override val uiStateObservable = Subject<MoreDetailsUiState>()
    override var uiState = MoreDetailsUiState()
    override fun updateArtist(artistName: String) {
        updateUiState(getArtist(artistName))
        uiStateObservable.notify(uiState)
    }

    private fun getArtist(artistName: String): List<Card> {
        return repository.getCardByArtist(artistName)
    }

    private fun updateUiState(Card: card) {
        when (card) {
            is NYTimesArtist -> updateArtistUiState(artist)
            EmptyArtist -> updateNoResultsUiState()
        }
    }

    private fun updateArtistUiState(artist: NYTimesArtist) {
        uiState = uiState.copy(
            artistDescription = artistHelper.getArtistText(artist),
            artistUrl = artist.url,
            actionsEnabled = artist.url != null
        )
    }

    private fun updateNoResultsUiState() {
        uiState = uiState.copy(
            artistDescription = artistHelper.getArtistText(),
            artistUrl = null,
            actionsEnabled = false
        )
    }
}