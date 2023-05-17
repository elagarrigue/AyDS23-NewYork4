package ayds.newyork.songinfo.moredetails.presentation.view

import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.moredetails.data.external.nytimes.artists.NYTimesArtistInjector
import ayds.newyork.songinfo.moredetails.data.ArtistRepositoryImpl
import ayds.newyork.songinfo.moredetails.data.local.nytimes.NYTimesLocalStorage
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.CursorToArtistMapper
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.CursorToArtistMapperImpl
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.NYTimesLocalStorageImpl
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenterImpl
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsUiState

object MoreDetailsViewInjector {
    private var cursorToArtistMapper: CursorToArtistMapper = CursorToArtistMapperImpl()

    private lateinit var moreDetailsView: AppCompatActivity
    private lateinit var moreDetailsUiState: MoreDetailsUiState
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter
    private lateinit var nyTimesLocalStorage: NYTimesLocalStorage
    private lateinit var artistRepository: ArtistRepository
    private lateinit var artistHelper : ArtistInfoHelper

    fun init(moreDetailsView: AppCompatActivity) {
        initArtistHelper()
        initMoreDetailsUiState()
        initMoreDetailsView(moreDetailsView)
        initNYTimesLocalStorage()
        initInfoRepository()
        initPresenter()
    }

    private fun initArtistHelper(){
        artistHelper = ArtistInfoHelperImpl()
    }

    private fun initMoreDetailsUiState(){
        moreDetailsUiState = MoreDetailsUiState()
    }

    private fun initMoreDetailsView(moreDetailsView : AppCompatActivity){
        MoreDetailsViewInjector.moreDetailsView = moreDetailsView
    }

    private fun initNYTimesLocalStorage(){
        nyTimesLocalStorage = NYTimesLocalStorageImpl(moreDetailsView, cursorToArtistMapper)
    }

    private fun initInfoRepository(){
        artistRepository = ArtistRepositoryImpl(nyTimesLocalStorage, NYTimesArtistInjector.nyTimesArtistService)
    }

    private fun initPresenter(){
        moreDetailsPresenter = MoreDetailsPresenterImpl(artistRepository, moreDetailsUiState, artistHelper)
    }

    fun getMoreDetailsPresenter(): MoreDetailsPresenter {
        return moreDetailsPresenter
    }
}