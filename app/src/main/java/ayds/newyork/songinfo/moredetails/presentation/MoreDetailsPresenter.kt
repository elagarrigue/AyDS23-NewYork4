package ayds.newyork.songinfo.moredetails.presentation

import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.moredetails.domain.entities.Artist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.EmptyArtist
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.newyork.songinfo.utils.UtilsInjector
import ayds.newyork.songinfo.utils.navigation.NavigationUtils
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsPresenter {
    val uiState: MoreDetailsUiState
    val uiStateObservable: Observable<MoreDetailsUiState>

    fun onButtonClicked(activity: AppCompatActivity)
    fun updateArtist(artistName: String)
}

class MoreDetailsPresenterImpl(private val repository: ArtistRepository): MoreDetailsPresenter {
    override var uiState: MoreDetailsUiState = MoreDetailsUiState()
    override val uiStateObservable = Subject<MoreDetailsUiState>()

    private val artistHelper: ArtistInfoHelper = MoreDetailsViewInjector.getArtistHelper()
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils

    override fun onButtonClicked(activity: AppCompatActivity){
        uiState.artistUrl?.let {
            navigationUtils.openExternalUrl(activity, it)
        }
    }

    override fun updateArtist(artistName: String) {
        val artist = repository.getArtist(artistName)
        updateUiState(artist)
        uiStateObservable.notify(uiState)
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