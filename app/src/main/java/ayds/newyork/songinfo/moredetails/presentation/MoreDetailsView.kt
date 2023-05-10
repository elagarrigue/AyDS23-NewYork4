package ayds.newyork.songinfo.moredetails.presentation

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.R
import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import ayds.newyork.songinfo.utils.UtilsInjector
import ayds.newyork.songinfo.utils.view.ImageLoader

interface MoreDetailsView {
    val uiState: MoreDetailsUiState

    fun updateUiState(artistInfo: ArtistInfo)
    fun updateArtistInfoDescription()
    fun updateLogoImage()
    fun updateFullArticleState()
}

class MoreDetailsActivity: AppCompatActivity(), MoreDetailsView {
    private lateinit var artistInfoView: TextView
    private lateinit var logoImageView: ImageView
    private lateinit var fullArticleButtonView: View
    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    private val imageLoader: ImageLoader = UtilsInjector.imageLoader

    override var uiState: MoreDetailsUiState = MoreDetailsUiState()

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initProperties()
        initListeners()

        updateArtistInfo()
    }

    private fun initModule() {
        MoreDetailsViewInjector.init(this)
        moreDetailsPresenter = MoreDetailsViewInjector.getMoreDetailsPresenter()
    }

    private fun initProperties() {
        artistInfoView = findViewById(R.id.textPane2)
        logoImageView = findViewById(R.id.imageView)
        fullArticleButtonView = findViewById(R.id.openUrlButton)
    }

    private fun initListeners() {
        runOnUiThread {
            fullArticleButtonView.setOnClickListener {
                moreDetailsPresenter.onButtonClicked(uiState.artistInfoUrl!!)
            }
        }
    }

    private fun updateArtistInfo() {
        moreDetailsPresenter.updateArtistInfo(intent.getStringExtra(ARTIST_NAME_EXTRA)!!)
    }

    override fun updateUiState(artistInfo: ArtistInfo) {
        uiState = uiState.copy(
            artistInfoDescription = artistInfo.info,
            artistInfoUrl = artistInfo.url,
            actionsEnabled = artistInfo.url != null
        )
    }

    override fun updateArtistInfoDescription() {
        runOnUiThread {
            artistInfoView.text = Html.fromHtml(uiState.artistInfoDescription)
        }
    }

    override fun updateLogoImage() {
        runOnUiThread {
            imageLoader.loadImageIntoView(uiState.logoImageUrl, logoImageView)
        }
    }

    override fun updateFullArticleState() {
        enableActions(uiState.actionsEnabled)
    }

    private fun enableActions(enable: Boolean) {
        runOnUiThread {
            fullArticleButtonView.isEnabled = enable
        }
    }
}
