package ayds.newyork.songinfo.moredetails.presentation.view

import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.moredetails.data.repository.CardRepositoryImpl
import ayds.newyork.songinfo.moredetails.data.repository.local.CardLocalStorage
import ayds.newyork.songinfo.moredetails.data.repository.local.sqlitedb.CursorToCardMapper
import ayds.newyork.songinfo.moredetails.data.repository.local.sqlitedb.CursorToCardMapperImpl
import ayds.newyork.songinfo.moredetails.data.repository.local.sqlitedb.CardLocalStorageImpl
import ayds.newyork.songinfo.moredetails.data.repository.CardBrokerInjector
import ayds.newyork.songinfo.moredetails.domain.repository.CardRepository
import ayds.newyork.songinfo.moredetails.presentation.presenter.CardDescriptionHelper
import ayds.newyork.songinfo.moredetails.presentation.presenter.CardDescriptionHelperImpl
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
        CardBrokerInjector.init()
        cardRepository = CardRepositoryImpl(cardLocalStorage, CardBrokerInjector.getCardBroker())
    }

    private fun initPresenter(){
        moreDetailsPresenter = MoreDetailsPresenterImpl(cardRepository, cardDescriptionHelper)
    }

    fun getMoreDetailsPresenter(): MoreDetailsPresenter {
        return moreDetailsPresenter
    }
}