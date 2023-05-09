package ayds.newyork.songinfo.moredetails.presentation

import android.app.Activity
import android.text.Html
import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import ayds.newyork.songinfo.utils.UtilsInjector
import ayds.newyork.songinfo.utils.navigation.NavigationUtils

interface MoreDetailsPresenter {
    fun onViewAttached(view: MoreDetailsView)
    fun onViewDetached()
    fun onButtonClicked(url: String)
    fun updateArtistInfo(artistInfo: ArtistInfo)
}

class MoreDetailsPresenterImpl: MoreDetailsPresenter {
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils
    private var view: MoreDetailsView? = null

    override fun onViewAttached(view: MoreDetailsView) {
        this.view = view
    }

    override fun onViewDetached() {
        view = null
    }

    override fun onButtonClicked(url: String) {
        navigationUtils.openExternalUrl(view as Activity, url)
    }

    override fun updateArtistInfo(artistInfo: ArtistInfo) {
        view?.updateUiState(artistInfo)
        view?.updateArtistInfoDescription()
        view?.updateLogoImage()
        view?.updateFullArticleState()
    }
}