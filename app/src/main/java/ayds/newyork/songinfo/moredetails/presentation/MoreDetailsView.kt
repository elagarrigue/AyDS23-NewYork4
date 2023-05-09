package ayds.newyork.songinfo.moredetails.presentation

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.R
import ayds.newyork.songinfo.moredetails.data.MoreDetailsModel
import ayds.newyork.songinfo.moredetails.data.MoreDetailsModelInjector
import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import ayds.newyork.songinfo.utils.UtilsInjector
import ayds.newyork.songinfo.utils.navigation.NavigationUtils
import ayds.newyork.songinfo.utils.view.ImageLoader
import ayds.observer.Observable
import ayds.observer.Subject

interface MoreDetailsView {
    val uiEventObservable: Observable<MoreDetailsUiEvent>
    val uiState: MoreDetailsUiState

    fun openExternalLink(url: String)
}

class MoreDetailsActivity: AppCompatActivity(), MoreDetailsView {
    private lateinit var artistName: String
    private lateinit var artistInfoView: TextView
    private lateinit var logoImageView: ImageView
    private lateinit var openUrlButtonView: View
    private lateinit var moreDetailsModel: MoreDetailsModel

    private val onActionSubject = Subject<MoreDetailsUiEvent>()
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils
    private val imageLoader: ImageLoader = UtilsInjector.imageLoader

    override val uiEventObservable: Observable<MoreDetailsUiEvent> = onActionSubject
    override var uiState: MoreDetailsUiState = MoreDetailsUiState()

    override fun openExternalLink(url: String) {
        navigationUtils.openExternalUrl(this, url)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initProperties()
        initObservers()
        initListeners()

        createArtistName()
    }

    private fun initModule() {
        MoreDetailsViewInjector.init(this)
        moreDetailsModel = MoreDetailsModelInjector.getMoreDetailsModel()
    }

    private fun initProperties() {
        artistInfoView = findViewById(R.id.textPane2)
        logoImageView = findViewById(R.id.imageView)
        openUrlButtonView = findViewById(R.id.openUrlButton)
    }

    private fun initObservers() {
        moreDetailsModel.artistInfoObservable.subscribe {
            value -> updateArtistInfo(value)
        }
    }

    private fun initListeners() {
        runOnUiThread {
            openUrlButtonView.setOnClickListener {
                notifyArtistInfoAction()
                openExternalLink(uiState.artistInfoUrl!!)
            }
        }
    }

    private fun notifyArtistInfoAction(){
        onActionSubject.notify(MoreDetailsUiEvent.ViewFullArticle)
    }

    private fun updateArtistInfo(artistInfo: ArtistInfo) {
        updateUiState(artistInfo)
        updateArtistInfoDescription()
        updateLogoImage()
    }

    private fun updateUiState(artistInfo: ArtistInfo) {
        uiState = uiState.copy(
            artistInfoDescription = artistInfo.info,
            artistInfoUrl = artistInfo.url,
            actionsEnabled = true
        )
    }

    private fun updateArtistInfoDescription() {
        runOnUiThread {
            artistInfoView.text = Html.fromHtml(uiState.artistInfoDescription)
        }
    }

    private fun updateLogoImage() {
        runOnUiThread {
            imageLoader.loadImageIntoView(uiState.logoImageUrl, logoImageView)
        }
    }

    private fun createArtistName() {
        artistName = intent.getStringExtra(ARTIST_NAME_EXTRA)!!
    }
}
