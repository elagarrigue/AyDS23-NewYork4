package ayds.newyork.songinfo.moredetails.presentation

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.R
import ayds.newyork.songinfo.moredetails.domain.entities.Artist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.EmptyArtist
import ayds.newyork.songinfo.utils.UtilsInjector
import ayds.newyork.songinfo.utils.navigation.NavigationUtils
import ayds.newyork.songinfo.utils.view.ImageLoader

interface MoreDetailsView

class MoreDetailsActivity: AppCompatActivity(), MoreDetailsView {
    private lateinit var artistView: TextView
    private lateinit var logoImageView: ImageView
    private lateinit var fullArticleButtonView: View
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    private val imageLoader: ImageLoader = UtilsInjector.imageLoader

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
    }

    private fun initObservers() {
        moreDetailsPresenter.uiStateObservable.subscribe {
            uiState -> updateUi(uiState)
        }
    }

    private fun initListeners() {
        runOnUiThread {
            fullArticleButtonView.setOnClickListener {
                moreDetailsPresenter.onButtonClicked(this)
            }
        }
    }

    private fun updateArtist() {
        Thread {
            intent.getStringExtra(ARTIST_NAME_EXTRA)?.let {
                moreDetailsPresenter.updateArtist(it)
            }
        }.start()
    }

    private fun updateUi(uiState: MoreDetailsUiState){
        updateArtistDescription(uiState)
        updateLogoImage(uiState)
        updateFullArticleState(uiState)
    }

    private fun updateArtistDescription(uiState: MoreDetailsUiState) {
        runOnUiThread {
            artistView.text = Html.fromHtml(uiState.artistDescription)
        }
    }

    private fun updateLogoImage(uiState: MoreDetailsUiState) {
        runOnUiThread {
            imageLoader.loadImageIntoView(uiState.logoImageUrl, logoImageView)
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