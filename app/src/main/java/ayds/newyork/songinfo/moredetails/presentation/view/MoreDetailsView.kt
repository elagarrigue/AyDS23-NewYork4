package ayds.newyork.songinfo.moredetails.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ayds.newyork.songinfo.R
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsUiState

class MoreDetailsView: AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter

    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initModule()
        initProperties()
        initObservers()

        updateArtist()
    }

    private fun initModule() {
        MoreDetailsViewInjector.init(this)
        moreDetailsPresenter = MoreDetailsViewInjector.getMoreDetailsPresenter()
    }

    private fun initProperties() {
        recyclerView = findViewById(R.id.moreDetailsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = RecyclerViewAdapter(emptyList(), this)
        recyclerView.adapter = adapter
    }

    private fun initObservers() {
        moreDetailsPresenter.uiStateObservable.subscribe {
            uiState -> updateUi(uiState)
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
        runOnUiThread {
            adapter = RecyclerViewAdapter(uiState.cards, this)
            recyclerView.adapter = adapter
        }
    }
}
