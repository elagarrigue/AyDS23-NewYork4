package ayds.newyork.songinfo.moredetails.data

import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsModel {
    val artistInfoObservable: Observable<ArtistInfo>

    fun searchArtistInfo(artistName: String)
}

internal class MoreDetailsModelImpl(private val repository: ArtistInfoRepository): MoreDetailsModel {

    override val artistInfoObservable = Subject<ArtistInfo>()

    override fun searchArtistInfo(artistName: String) {
        repository.getArtistInfo(artistName).let {
            artistInfoObservable.notify(it)
        }
    }
}