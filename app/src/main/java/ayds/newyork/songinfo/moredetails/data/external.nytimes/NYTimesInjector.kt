package ayds.newyork.songinfo.moredetails.data.external.nytimes

import ayds.newyork.songinfo.moredetails.data.external.nytimes.artists.*
import ayds.newyork.songinfo.moredetails.domain.repository.NYTimesArtistInfoService

object NYTimesInjector {
    val nyTimesArtistInfoService: NYTimesArtistInfoService = NYTimesArtistInfoInjector.nyTimesArtistInfoService
}