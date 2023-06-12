package ayds.newyork.songinfo.moredetails.presentation.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ayds.newyork.songinfo.R
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsUiState

class MoreDetailsView: AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var noResultsTextView: TextView
    private lateinit var adapter: RecyclerViewCardAdapter

    private lateinit var moreDetailsPresenter: MoreDetailsPresenter

    companion object {
        const val NO_RESULTS_TEXT = "No Results"
        const val ARTIST_NAME_EXTRA = "artistName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_details)

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
        noResultsTextView = findViewById(R.id.noResultsTextView)
        recyclerView = findViewById(R.id.moreDetailsRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = RecyclerViewCardAdapter(emptyList())
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
            if(uiState.cards.isEmpty()){
                noResultsTextView.text = NO_RESULTS_TEXT
            }
            adapter = RecyclerViewCardAdapter(uiState.cards)
            recyclerView.adapter = adapter
        }
    }
}
