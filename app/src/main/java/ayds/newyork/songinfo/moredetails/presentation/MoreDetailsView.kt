package ayds.newyork.songinfo.moredetails.presentation

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.R
import ayds.newyork.songinfo.moredetails.domain.entities.Artist
import ayds.newyork.songinfo.utils.UtilsInjector
import ayds.newyork.songinfo.utils.view.ImageLoader

interface MoreDetailsView {
    val uiState: MoreDetailsUiState

    fun updateUiState(artist: Artist)
    fun updateArtistDescription()
    fun updateLogoImage()
    fun updateFullArticleState()
}

class MoreDetailsActivity: AppCompatActivity(), MoreDetailsView {
    private lateinit var artistView: TextView
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

    private fun initListeners() {
        runOnUiThread {
            fullArticleButtonView.setOnClickListener {
                moreDetailsPresenter.onButtonClicked(uiState.artistUrl!!)
            }
        }
    }

    private fun updateArtist() {
        Thread {
            moreDetailsPresenter.updateArtist(intent.getStringExtra(ARTIST_NAME_EXTRA)!!)
        }.start()
    }

    override fun updateUiState(artist: Artist) {
        uiState = uiState.copy(
            artistDescription = artist.info,
            artistUrl = artist.url,
            actionsEnabled = artist.url != null
        )
    }

    override fun updateArtistDescription() {
        runOnUiThread {
            artistView.text = Html.fromHtml(uiState.artistDescription)
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
