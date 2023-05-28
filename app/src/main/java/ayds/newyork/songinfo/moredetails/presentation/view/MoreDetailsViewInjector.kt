package ayds.newyork.songinfo.moredetails.presentation.view

import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.moredetails.data.CardRepositoryImpl
import ayds.newyork.songinfo.moredetails.data.local.nytimes.CardLocalStorage
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.CursorToCardMapper
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.CursorToCardMapperImpl
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.CardLocalStorageImpl
import ayds.newyork.songinfo.moredetails.domain.repository.CardRepository
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenterImpl

object MoreDetailsViewInjector {
    private var cursorToArtistMapper: CursorToCardMapper = CursorToCardMapperImpl()

    private lateinit var moreDetailsView: AppCompatActivity
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter
    private lateinit var cardLocalStorage: CardLocalStorage
    private lateinit var cardRepository: CardRepository
    private lateinit var cardDescriptionHelper : CardDescriptionHelper

    fun init(moreDetailsView: AppCompatActivity) {
        initCardDescriptionHelper()
        initMoreDetailsView(moreDetailsView)
        initNYTimesLocalStorage()
        initInfoRepository()
        initPresenter()
    }

    private fun initCardDescriptionHelper(){
        cardDescriptionHelper = CardDescriptionHelperImpl()
    }

    private fun initMoreDetailsView(moreDetailsView : AppCompatActivity){
        this.moreDetailsView = moreDetailsView
    }

    private fun initNYTimesLocalStorage(){
        cardLocalStorage = CardLocalStorageImpl(moreDetailsView, cursorToArtistMapper)
    }

    private fun initInfoRepository(){
        cardRepository = CardRepositoryImpl(cardLocalStorage, NYTimesArtistInjector.nyTimesArtistService) //Broker en lugar de service
    }

    private fun initPresenter(){
        moreDetailsPresenter = MoreDetailsPresenterImpl(cardRepository, cardDescriptionHelper)
    }

    fun getMoreDetailsPresenter(): MoreDetailsPresenter {
        return moreDetailsPresenter
    }
}