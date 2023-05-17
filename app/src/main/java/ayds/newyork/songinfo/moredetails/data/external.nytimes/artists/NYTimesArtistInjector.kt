package ayds.newyork.songinfo.moredetails.data.external.nytimes.artists

import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesArtistService
import ayds.newyork.songinfo.moredetails.presentation.view.ArtistInfoHelperImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object NYTimesArtistInjector {

    private const val NY_TIMES_API_URL = "https://api.nytimes.com/svc/search/v2/"
    private val nyTimesApiRetrofit = Retrofit.Builder()
        .baseUrl(NY_TIMES_API_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()
    private val nyTimesApi: NYTimesArtistAPI = nyTimesApiRetrofit.create(NYTimesArtistAPI::class.java)
    private val nyTimesToArtistResolver: NYTimesToArtistResolver = JsonToArtistResolver(
        ArtistInfoHelperImpl()
    )

    val nyTimesArtistService: NYTimesArtistService = NYTimesArtistServiceImpl(
        nyTimesApi,
        nyTimesToArtistResolver
    )
}