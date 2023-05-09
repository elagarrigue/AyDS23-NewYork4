package ayds.newyork.songinfo.moredetails.presentation

import android.content.Context
import ayds.newyork.songinfo.moredetails.data.MoreDetailsModelInjector
import ayds.newyork.songinfo.moredetails.data.external.nytimes.artists.NYTimesArtistInfoInjector
import ayds.newyork.songinfo.moredetails.data.local.nytimes.ArtistInfoRepositoryImpl
import ayds.newyork.songinfo.moredetails.data.local.nytimes.NYTimesLocalStorage
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.CursorToArtistInfoMapper
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.CursorToArtistInfoMapperImpl
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.NYTimesLocalStorageImpl
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistInfoRepository

object MoreDetailsViewInjector {

    private lateinit var moreDetailsView: MoreDetailsView
    private lateinit var nyTimesLocalStorage: NYTimesLocalStorage
    private lateinit var artistInfoRepository: ArtistInfoRepository
    private lateinit var cursorToArtistInfoMapper: CursorToArtistInfoMapper

    val presenter: MoreDetailsPresenter = MoreDetailsPresenterImpl()
    val artistInfoHelper: ArtistInfoHelper = ArtistInfoHelperImpl()

    fun init(moreDetailsView: MoreDetailsView) {
        MoreDetailsModelInjector.initMoreDetailsModel(moreDetailsView)
        initOtherInfoView(moreDetailsView)
        initPresenter()
        initCursorToNYTimesArtistMapper()
        initNYTimesLocalStorage()
        initInfoRepository()
    }

    private fun initOtherInfoView(moreDetailsView : MoreDetailsView){
        this.moreDetailsView = moreDetailsView
    }

    private fun initPresenter(){
        presenter.onViewAttached(moreDetailsView)
    }

    private fun initCursorToNYTimesArtistMapper(){
        cursorToArtistInfoMapper = CursorToArtistInfoMapperImpl()
    }

    private fun initNYTimesLocalStorage(){
        nyTimesLocalStorage = NYTimesLocalStorageImpl(moreDetailsView as Context, cursorToArtistInfoMapper)
    }

    private fun initInfoRepository(){
        artistInfoRepository = ArtistInfoRepositoryImpl(nyTimesLocalStorage, NYTimesArtistInfoInjector.nyTimesArtistInfoService)
    }
}