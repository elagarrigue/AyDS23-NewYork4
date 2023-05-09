package ayds.newyork.songinfo.moredetails.data.external.nytimes

import ayds.newyork.songinfo.moredetails.data.external.nytimes.artists.*
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistInfoRepository

object NYTimesInjector {
    val nyTimesArtistInfoService: ArtistInfoRepository = NYTimesArtistInfoInjector.nyTimesArtistInfoService
}