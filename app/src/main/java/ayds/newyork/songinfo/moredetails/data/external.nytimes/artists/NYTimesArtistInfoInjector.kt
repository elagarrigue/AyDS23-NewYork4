package ayds.newyork.songinfo.moredetails.data.external.nytimes.artists

import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesArtistInfoService
import ayds.newyork.songinfo.moredetails.data.local.nytimes.ArtistInfoRepositoryImpl
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.newyork.songinfo.moredetails.presentation.ArtistInfoHelperImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object NYTimesArtistInfoInjector {
    private const val NY_TIMES_API_URL = "https://api.nytimes.com/svc/search/v2/"
    private val nyTimesApi: NYTimesArtistInfoAPI = retrofit().create(NYTimesArtistInfoAPI::class.java)
    private val nyTimesToArtistInfoResolver: NYTimesToArtistInfoResolver = JsonToArtistInfoResolver(ArtistInfoHelperImpl())

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(NY_TIMES_API_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val nyTimesArtistInfoService: NYTimesArtistInfoService = NYTimesArtistInfoServiceImpl(
        nyTimesApi,
        nyTimesToArtistInfoResolver
    )
}