package ayds.newyork.songinfo.moredetails.presentation

import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.newyork.songinfo.utils.UtilsInjector
import ayds.newyork.songinfo.utils.navigation.NavigationUtils

interface MoreDetailsPresenter {
    fun onViewAttached(view: MoreDetailsView)
    fun onViewDetached()
    fun onButtonClicked(url: String)
    fun updateArtist(artistName: String)
}

class MoreDetailsPresenterImpl(private val repository: ArtistRepository): MoreDetailsPresenter {
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils
    private var view: MoreDetailsView? = null

    override fun onViewAttached(view: MoreDetailsView) {
        this.view = view
    }

    override fun onViewDetached() {
        view = null
    }

    override fun onButtonClicked(url: String) {
        navigationUtils.openExternalUrl(view as AppCompatActivity, url)
    }

    override fun updateArtist(artistName: String) {
        view?.updateUiState(repository.getArtist(artistName))
        view?.updateArtistDescription()
        view?.updateLogoImage()
        view?.updateFullArticleState()
    }
}