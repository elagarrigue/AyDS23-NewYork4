package ayds.newyork.songinfo.moredetails.data.local.nytimes

import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesArtistInfoService
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.NYTimesLocalStorage
import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistInfoRepository

internal class ArtistInfoRepositoryImpl(
    private val nyTimesLocalStorage: NYTimesLocalStorage,
    private val nyTimesArtistInfoService: NYTimesArtistInfoService
): ArtistInfoRepository {

    override fun getArtistInfo(artistName: String): ArtistInfo {
        var artistInfo = nyTimesLocalStorage.getArtistInfo(artistName)
        when {
            artistInfo != null -> markArtistAsLocal(artistInfo)
            else -> {
                artistInfo = nyTimesArtistInfoService.getArtistInfo(artistName)

            }
        }
        return artistInfo
    }

    private fun markArtistAsLocal(artistInfo: ArtistInfo) {
        artistInfo.isLocallyStored = true
    }
}