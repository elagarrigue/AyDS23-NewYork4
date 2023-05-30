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
    private lateinit var artistView: TextView
    private lateinit var logoImageView: ImageView
    private lateinit var fullArticleButtonView: View
    private lateinit var sourceLabel: TextView

    private lateinit var artistViewWinchester: TextView
    private lateinit var logoImageViewWinchester: ImageView
    private lateinit var fullArticleButtonViewWinchester: View
    private lateinit var sourceLabelWinchester: TextView

    private lateinit var artistViewLast: TextView
    private lateinit var logoImageViewLast: ImageView
    private lateinit var fullArticleButtonViewLast: View
    private lateinit var sourceLabelLast: TextView

    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    private var artistUrl: String? = null
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
        artistView = findViewById(R.id.textPane2)
        logoImageView = findViewById(R.id.imageView)
        fullArticleButtonView = findViewById(R.id.openUrlButton)
        sourceLabel = findViewById(R.id.sourceTextView)

        artistViewWinchester = findViewById(R.id.textPanelWiki)
        logoImageViewWinchester = findViewById(R.id.imageViewWiki)
        fullArticleButtonViewWinchester = findViewById(R.id.openUrlButtonWiki)
        sourceLabelWinchester = findViewById(R.id.sourceTextViewWiki)

        artistViewLast = findViewById(R.id.textPanelLF)
        logoImageViewLast = findViewById(R.id.imageViewLF)
        fullArticleButtonViewLast = findViewById(R.id.openUrlButtonLF)
        sourceLabelLast = findViewById(R.id.sourceTextViewLF)
    }

    private fun initObservers() {
        moreDetailsPresenter.uiStateObservable.subscribe {
            uiState -> updateUi(uiState)
        }
    }

    private fun initListeners() {
        runOnUiThread {
            fullArticleButtonView.setOnClickListener {
                artistUrl?.let {
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

    private fun updateUi(uiState: MoreDetailsUiState){
        updateArtistDescription(uiState)
        updateArtistUrl(uiState)
        updateLogoImage(uiState)
        updateFullArticleState(uiState)
    }

    private fun updateArtistDescription(uiState: MoreDetailsUiState) {
        runOnUiThread {
            artistView.text = Html.fromHtml(uiState.cardDescription)
        }
    }

    private fun updateArtistUrl(uiState: MoreDetailsUiState) {
        runOnUiThread {
            artistUrl = uiState.cardUrl
        }
    }

    private fun updateLogoImage(uiState: MoreDetailsUiState) {
        runOnUiThread {
            imageLoader.loadImageIntoView(uiState.logoImageUrl!!, logoImageView)
        }
    }

    private fun updateFullArticleState(uiState: MoreDetailsUiState) {
        enableActions(uiState.actionsEnabled)
    }

    private fun enableActions(enable: Boolean) {
        runOnUiThread {
            fullArticleButtonView.isEnabled = enable
        }
    }
}
