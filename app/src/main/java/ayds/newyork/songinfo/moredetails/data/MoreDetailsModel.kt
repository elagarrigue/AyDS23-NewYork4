package ayds.newyork.songinfo.moredetails.data

import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import ayds.newyork.songinfo.moredetails.domain.repository.NYTimesArtistInfoService
import ayds.observer.Observable
import ayds.observer.Subject

interface  MoreDetailsModel {
    val artistInfoObservable: Observable<ArtistInfo>

    fun searchArtistInfo(term: String)
}

internal class MoreDetailsModelImpl(private val repository: NYTimesArtistInfoService): MoreDetailsModel {

    override val artistInfoObservable = Subject<ArtistInfo>()

    override fun searchArtistInfo(term: String) {
        repository.getArtistInfo(term).let {
            artistInfoObservable.notify(it)
        }
    }
}