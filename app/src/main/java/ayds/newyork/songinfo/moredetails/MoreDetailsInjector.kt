package ayds.newyork.songinfo.moredetails

import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesAPI
import ayds.newyork.songinfo.moredetails.presentation.MoreDetailsView
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.newyork.songinfo.moredetails.data.local.nytimes.ArtistInfoRepositoryImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

object MoreDetailsInjector {

    private const val NY_TIMES_API_URL = "https://api.nytimes.com/svc/search/v2/"
    private lateinit var retrofit: Retrofit
    private lateinit var nyTimesApi: NYTimesAPI 
    private lateinit var moreDetailsView : MoreDetailsView
    private lateinit var nyTimesLocalStorage : NYTimesLocalStorage //Este seria la base de datos si le mejoramos el nombre(se me ocurrio asi basado en el
                                                                     // SpotifyLocalSorage del gome/model/repository/local/spotify)
    private lateinit var presenter: moreDetailsPresenter   //Este seria el presentador
    private lateinit var cursorToNYTimesArtistMapper: CursorToNYTimesArtistMapper //Este seria el equivalente de home/model/repository/local/spotify de cursorToSpotifySongMapper
    private lateinit var nyTimesService: NYTimesService //Este seria el equivalente a SpotifyTrackService de home/model/repository/external/spotify/tracks
    private lateinit var artistInfoRepository: ArtistInfoRepository //El equivalente del repository
    private lateinit var nyTimesToArtistInfoResolver: NYTimesToArtistInfoResolver // equivalente de home/model/repository/external/spotift/tracks SpotifyToSongResolver

    //Asumiendo que OtherInfoView llama al init del injector mandandose a "si mismo(this" por parametro
    fun init(moreDetailsView: MoreDetailsView) {
        initOtherInfoView(moreDetailsView)
        initPresenter()
        initNYTimesLocalStorage()
        initNYTimesApi()
        initCursorToNYTimesArtistMapper()
        initNYTimesToArtistInfoResolver()
        initNYTimesService()
        initInfoRepository()
    }

    private fun initOtherInfoView(moreDetailsView : MoreDetailsView){
        this.moreDetailsView = moreDetailsView
    }

    private fun initNYTimesApi(){
        nyTimesApi = retrofit().create(NYTimesAPI::class.java)
    }

    private fun initCursorToNYTimesArtistMapper(){
        cursorToNYTimesArtistMapper = CursorToNYTimesArtistMapperImpl()
    }

    private fun initNYTimesToArtistInfoResolver(){
        nyTimesToArtistInfoResolver = NYTimesToArtistInfoResolverImpl()
    }

    private fun initNYTimesService(){
        nyTimesService = NYTimesServiceImpl(nyTimesApi, nyTimesToArtistInfoResolver)
    }

    private fun initInfoRepository(){
        infoRepository = InfoRepository(nyTimesLocalStorage, nyTimesService)
    }

    private fun retrofit() : Retrofit = Retrofit.Builder()
            .baseUrl(NY_TIMES_API_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()

    private fun initNYTimesLocalStorage(){
        this.nyTimesLocalStorage = NYTimesLocalStorageImpl(moreDetailsView, cursorToNYTimesArtistMapper)
    }

    //Asumiendo que el presentador tiene esa funcion para "recibir" la vista moreDetails
    private fun initPresenter(){
        this.presenter.setMoreDetailsView(moreDetailsView)
    }
}