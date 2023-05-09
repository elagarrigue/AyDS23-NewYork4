package ayds.newyork.songinfo.moredetails.data.external.nytimes.artists

import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesAPI
import ayds.newyork.songinfo.moredetails.data.local.nytimes.ArtistInfoRepositoryImpl
import ayds.newyork.songinfo.moredetails.domain.repository.NYTimesArtistInfoService
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object NYTimesArtistInfoInjector {
    private const val NY_TIMES_API_URL = "https://api.nytimes.com/svc/search/v2/"
    private val nyTimesApi: NYTimesAPI = retrofit().create(NYTimesAPI::class.java)
    private val nyTimesToArtistInfoResolver: NYTimesToArtistInfoResolver = JsonToArtistInfoResolver()

    private fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(ArtistInfoRepositoryImpl.NY_TIMES_API_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    val nyTimesArtistInfoService: NYTimesArtistInfoService = NYTimesArtistInfoServiceImpl(
        nyTimesApi,
        nyTimesToArtistInfoResolver
    )
}