package ayds.newyork.songinfo.moredetails.data

import android.content.Context
import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesArtistInfoService
import ayds.newyork.songinfo.moredetails.data.external.nytimes.artists.NYTimesArtistInfoInjector
import ayds.newyork.songinfo.moredetails.data.local.nytimes.ArtistInfoRepositoryImpl
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.NYTimesLocalStorage
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.newyork.songinfo.moredetails.presentation.MoreDetailsView

object MoreDetailsModelInjector {

    private lateinit var moreDetailsModel: MoreDetailsModel

    fun getMoreDetailsModel(): MoreDetailsModel = moreDetailsModel

    fun initMoreDetailsModel(moreDetailsView: MoreDetailsView) {
        val artistInfoLocalStorage = NYTimesLocalStorage(moreDetailsView as Context)
        val nyTimesArtistInfoService: NYTimesArtistInfoService = NYTimesArtistInfoInjector.nyTimesArtistInfoService

        val repository: ArtistInfoRepository = ArtistInfoRepositoryImpl(artistInfoLocalStorage, nyTimesArtistInfoService)

        moreDetailsModel = MoreDetailsModelImpl(repository)
    }

}