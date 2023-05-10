package ayds.newyork.songinfo.moredetails.presentation

import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.moredetails.data.external.nytimes.artists.NYTimesArtistInjector
import ayds.newyork.songinfo.moredetails.data.ArtistRepositoryImpl
import ayds.newyork.songinfo.moredetails.data.local.nytimes.NYTimesLocalStorage
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.CursorToArtistMapper
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.CursorToArtistMapperImpl
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.NYTimesLocalStorageImpl
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistRepository

object MoreDetailsViewInjector {
    private var cursorToArtistMapper: CursorToArtistMapper = CursorToArtistMapperImpl()

    private lateinit var moreDetailsView: MoreDetailsView
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter
    private lateinit var nyTimesLocalStorage: NYTimesLocalStorage
    private lateinit var artistRepository: ArtistRepository

    fun init(moreDetailsView: MoreDetailsView) {
        initMoreDetailsView(moreDetailsView)
        initNYTimesLocalStorage()
        initInfoRepository()
        initPresenter()
    }

    private fun initMoreDetailsView(moreDetailsView : MoreDetailsView){
        this.moreDetailsView = moreDetailsView
    }

    private fun initNYTimesLocalStorage(){
        nyTimesLocalStorage = NYTimesLocalStorageImpl(moreDetailsView as AppCompatActivity, cursorToArtistMapper)
    }

    private fun initInfoRepository(){
        artistRepository = ArtistRepositoryImpl(nyTimesLocalStorage, NYTimesArtistInjector.nyTimesArtistService)
    }

    private fun initPresenter(){
        moreDetailsPresenter = MoreDetailsPresenterImpl(artistRepository)
        moreDetailsPresenter.onViewAttached(moreDetailsView)
    }

    fun getMoreDetailsPresenter(): MoreDetailsPresenter {
        return moreDetailsPresenter
    }
}