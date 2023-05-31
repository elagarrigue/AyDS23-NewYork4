package ayds.newyork.songinfo.moredetails.presentation.view

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.R
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsUiState
import ayds.newyork.songinfo.utils.UtilsInjector
import ayds.newyork.songinfo.utils.navigation.NavigationUtils
import ayds.newyork.songinfo.utils.view.ImageLoader

class MoreDetailsView: AppCompatActivity() {
    private lateinit var newYorkTimesDescriptionView: TextView
    private lateinit var newYorkTimesLogoView: ImageView
    private lateinit var newYorkTimesUrlView: View

    private lateinit var wikipediaDescriptionView: TextView
    private lateinit var wikipediaLogoView: ImageView
    private lateinit var wikipediaUrlView: View

    private lateinit var lastFmDescriptionView: TextView
    private lateinit var lastFmLogoView: ImageView
    private lateinit var lastFmUrlView: View

    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    private var newYorkTimesUrl: String? = null
    private var wikipediaUrl: String? = null
    private var lastFmUrl: String? = null
    private val imageLoader: ImageLoader = UtilsInjector.imageLoader
    private val navigationUtils: NavigationUtils = UtilsInjector.navigationUtils

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

        updateArtist()
    }

    private fun initModule() {
        MoreDetailsViewInjector.init(this)
        moreDetailsPresenter = MoreDetailsViewInjector.getMoreDetailsPresenter()
    }

    private fun initProperties() {
        newYorkTimesLogoView = findViewById(R.id.imageViewNYT)
        newYorkTimesDescriptionView = findViewById(R.id.textPanelNYT)
        newYorkTimesUrlView = findViewById(R.id.openUrlButtonNYT)

        wikipediaLogoView = findViewById(R.id.imageViewWiki)
        wikipediaDescriptionView = findViewById(R.id.textPanelWiki)
        wikipediaUrlView = findViewById(R.id.openUrlButtonWiki)

        lastFmLogoView = findViewById(R.id.imageViewLF)
        lastFmDescriptionView = findViewById(R.id.textPanelLF)
        lastFmUrlView = findViewById(R.id.openUrlButtonLF)
    }

    private fun initObservers() {
        moreDetailsPresenter.newYorkTimesUiStateObservable.subscribe {
            uiState -> updateNewYorkTimesUi(uiState)
        }
        moreDetailsPresenter.wikipediaUiStateObservable.subscribe {
            uiState -> updateWikipediaUi(uiState)
        }
        moreDetailsPresenter.lastFmUiStateObservable.subscribe {
            uiState -> updateLastFmUi(uiState)
        }
    }

    private fun initListeners() {
        runOnUiThread {
            newYorkTimesUrlView.setOnClickListener {
                newYorkTimesUrl?.let {
                    navigationUtils.openExternalUrl(this, it)
                }
            }
            wikipediaUrlView.setOnClickListener {
                wikipediaUrl?.let {
                    navigationUtils.openExternalUrl(this, it)
                }
            }
            lastFmUrlView.setOnClickListener {
                lastFmUrl?.let {
                    navigationUtils.openExternalUrl(this, it)
                }
            }
        }
    }

    private fun updateArtist() {
        Thread {
            intent.getStringExtra(ARTIST_NAME_EXTRA)?.let {
                moreDetailsPresenter.updateArtistCards(it)
            }
        }.start()
    }

    private fun updateNewYorkTimesUi(uiState: MoreDetailsUiState){
        updateNewYorkTimesDescription(uiState)
        updateNewYorkTimesUrl(uiState)
        updateNewYorkTimesLogo(uiState)
        updateNewYorkTimesFullArticleState(uiState)
    }

    private fun updateNewYorkTimesDescription(uiState: MoreDetailsUiState) {
        runOnUiThread {
            newYorkTimesDescriptionView.text = Html.fromHtml(uiState.cardDescription)
        }
    }

    private fun updateNewYorkTimesUrl(uiState: MoreDetailsUiState) {
        runOnUiThread {
            newYorkTimesUrl = uiState.cardUrl
        }
    }

    private fun updateNewYorkTimesLogo(uiState: MoreDetailsUiState) {
        runOnUiThread {
            imageLoader.loadImageIntoView(uiState.logoImageUrl, newYorkTimesLogoView)
        }
    }

    private fun updateNewYorkTimesFullArticleState(uiState: MoreDetailsUiState) {
        enableNewYorkTimesActions(uiState.actionsEnabled)
    }

    private fun enableNewYorkTimesActions(enable: Boolean) {
        runOnUiThread {
            newYorkTimesUrlView.isEnabled = enable
        }
    }

    private fun updateWikipediaUi(uiState: MoreDetailsUiState){
        updateWikipediaDescription(uiState)
        updateWikipediaUrl(uiState)
        updateWikipediaLogo(uiState)
        updateWikipediaFullArticleState(uiState)
    }

    private fun updateWikipediaDescription(uiState: MoreDetailsUiState) {
        runOnUiThread {
            wikipediaDescriptionView.text = Html.fromHtml(uiState.cardDescription)
        }
    }

    private fun updateWikipediaUrl(uiState: MoreDetailsUiState) {
        runOnUiThread {
            wikipediaUrl = uiState.cardUrl
        }
    }

    private fun updateWikipediaLogo(uiState: MoreDetailsUiState) {
        runOnUiThread {
            imageLoader.loadImageIntoView(uiState.logoImageUrl, wikipediaLogoView)
        }
    }

    private fun updateWikipediaFullArticleState(uiState: MoreDetailsUiState) {
        enableWikipediaActions(uiState.actionsEnabled)
    }

    private fun enableWikipediaActions(enable: Boolean) {
        runOnUiThread {
            wikipediaUrlView.isEnabled = enable
        }
    }

    private fun updateLastFmUi(uiState: MoreDetailsUiState){
        updateLastFmDescription(uiState)
        updateLastFmUrl(uiState)
        updateLastFmLogo(uiState)
        updateLastFmFullArticleState(uiState)
    }

    private fun updateLastFmDescription(uiState: MoreDetailsUiState) {
        runOnUiThread {
            lastFmDescriptionView.text = Html.fromHtml(uiState.cardDescription)
        }
    }

    private fun updateLastFmUrl(uiState: MoreDetailsUiState) {
        runOnUiThread {
            lastFmUrl = uiState.cardUrl
        }
    }

    private fun updateLastFmLogo(uiState: MoreDetailsUiState) {
        runOnUiThread {
            imageLoader.loadImageIntoView(uiState.logoImageUrl, lastFmLogoView)
        }
    }

    private fun updateLastFmFullArticleState(uiState: MoreDetailsUiState) {
        enableLastFmActions(uiState.actionsEnabled)
    }

    private fun enableLastFmActions(enable: Boolean) {
        runOnUiThread {
            lastFmUrlView.isEnabled = enable
        }
    }
}
