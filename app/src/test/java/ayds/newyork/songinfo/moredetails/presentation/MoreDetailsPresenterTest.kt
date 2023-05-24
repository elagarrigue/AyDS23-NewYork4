package ayds.newyork.songinfo.moredetails.presentation


import ayds.newyork.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenterImpl
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsUiState
import com.test.artist.external.entities.Artist
import com.test.artist.external.entities.ArtistInfoHelper

import io.mockk.*
import org.junit.Test

class MoreDetailsPresenterTest {
    private val repository: ArtistRepository = mockk()
    private val artistHelper: ArtistInfoHelper= mockk()
    private val presenter: MoreDetailsPresenter by lazy {
        MoreDetailsPresenterImpl(repository,artistHelper)
    }

    @Test
    fun `updateArtist should notify observers with NYTimesArtist`() {

        val artistName = "ArtistName"
        val artist = Artist.NYTimesArtist("url", "info", true)
        val expectedUiState = MoreDetailsUiState(
            "ArtistName",
            artist.url,
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU",
            true
        )
        val uiStateTester: (MoreDetailsUiState) -> Unit = mockk(relaxed =true)
        presenter.uiStateObservable.subscribe { uiStateTester(it) }

        every { repository.getArtist(artistName) } returns artist
        every { artistHelper.getArtistText(artist) } returns "ArtistName"

        presenter.updateArtist(artistName)

        verify { uiStateTester(expectedUiState) }
    }

}