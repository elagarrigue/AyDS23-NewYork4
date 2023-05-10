package ayds.newyork.songinfo.moredetails.presentation

import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.moredetails.data.external.nytimes.artists.NYTimesArtistInfoInjector
import ayds.newyork.songinfo.moredetails.data.local.nytimes.ArtistInfoRepositoryImpl
import ayds.newyork.songinfo.moredetails.data.local.nytimes.NYTimesLocalStorage
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.CursorToArtistInfoMapper
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.CursorToArtistInfoMapperImpl
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.NYTimesLocalStorageImpl
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistInfoRepository

object MoreDetailsViewInjector {
    private var cursorToArtistInfoMapper: CursorToArtistInfoMapper = CursorToArtistInfoMapperImpl()

    private lateinit var moreDetailsView: MoreDetailsView
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter
    private lateinit var nyTimesLocalStorage: NYTimesLocalStorage
    private lateinit var artistInfoRepository: ArtistInfoRepository

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
        nyTimesLocalStorage = NYTimesLocalStorageImpl(moreDetailsView as AppCompatActivity, cursorToArtistInfoMapper)
    }

    private fun initInfoRepository(){
        artistInfoRepository = ArtistInfoRepositoryImpl(nyTimesLocalStorage, NYTimesArtistInfoInjector.nyTimesArtistInfoService)
    }

    private fun initPresenter(){
        moreDetailsPresenter = MoreDetailsPresenterImpl(artistInfoRepository)
        moreDetailsPresenter.onViewAttached(moreDetailsView)
    }

    fun getMoreDetailsPresenter(): MoreDetailsPresenter {
        return moreDetailsPresenter
    }
}