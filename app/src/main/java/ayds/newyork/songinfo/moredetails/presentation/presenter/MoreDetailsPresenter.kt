package ayds.newyork.songinfo.moredetails.presentation.presenter

import ayds.newyork.songinfo.moredetails.domain.entities.Artist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.EmptyArtist
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.newyork.songinfo.moredetails.presentation.view.ArtistInfoHelper
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsPresenter {
    var uiState: MoreDetailsUiState
    val uiStateObservable: Observable<MoreDetailsUiState>

    fun updateArtist(artistName: String)
}

class MoreDetailsPresenterImpl(
    private val repository: ArtistRepository,
    private val artistHelper: ArtistInfoHelper
): MoreDetailsPresenter {
    override val uiStateObservable = Subject<MoreDetailsUiState>()
    override var uiState= MoreDetailsUiState()
    override fun updateArtist(artistName: String) {
        updateUiState(getArtist(artistName))
        uiStateObservable.notify(uiState)
    }

    private fun getArtist(artistName: String):Artist {
        return repository.getArtist(artistName)
    }

    private fun updateUiState(artist: Artist) {
        when (artist) {
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