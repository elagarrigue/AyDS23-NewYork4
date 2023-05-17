package ayds.newyork.songinfo.moredetails.presentation

import ayds.newyork.songinfo.moredetails.data.ArtistRepositoryImpl
import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesArtistService
import ayds.newyork.songinfo.moredetails.data.local.nytimes.NYTimesLocalStorage
import ayds.newyork.songinfo.moredetails.domain.entities.Artist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistRepository
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenter
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsPresenterImpl
import ayds.newyork.songinfo.moredetails.presentation.presenter.MoreDetailsUiState
import ayds.newyork.songinfo.moredetails.presentation.view.ArtistInfoHelper
import ayds.newyork.songinfo.moredetails.presentation.view.ArtistInfoHelperImpl
import io.mockk.*
import org.junit.Assert
import org.junit.Test

class MoreDetailsPresenterTest {
    private val nyTimesLocalStorage: NYTimesLocalStorage = mockk(relaxUnitFun = true)
    private val nyTimesArtistService: NYTimesArtistService = mockk(relaxUnitFun = true)
    private val repository: ArtistRepository by lazy {
        ArtistRepositoryImpl(nyTimesLocalStorage, nyTimesArtistService)
    }
    private val uiState: MoreDetailsUiState = MoreDetailsUiState()
    private val artistInfoHelper: ArtistInfoHelper = ArtistInfoHelperImpl()
    private val presenter: MoreDetailsPresenter = MoreDetailsPresenterImpl(repository, uiState, artistInfoHelper)

    @Test
    fun `updateArtist should notify observers with NYTimesArtist`() {
        val artist = NYTimesArtist(null, "info", false)
        every {
            nyTimesLocalStorage.getArtistByName("artistName")
        } returns artist

        presenter.updateArtist("artistName")

        Assert.assertNull(presenter.uiState.artistUrl)
        Assert.assertEquals("[*]info", presenter.uiState.artistDescription)
        Assert.assertFalse(presenter.uiState.actionsEnabled)
    }

    @Test
    fun `updateArtist should notify observers with EmptyArtist`() {
        every { nyTimesLocalStorage.getArtistByName("artistName") } returns null
        every { nyTimesArtistService.getArtist("artistName") } returns null

        presenter.updateArtist("artistName")
        val result = repository.getArtist("artistName")

        Assert.assertEquals(Artist.EmptyArtist, result)
        Assert.assertNull(presenter.uiState.artistUrl)
        Assert.assertEquals("Artist not found", presenter.uiState.artistDescription)
        Assert.assertFalse(presenter.uiState.actionsEnabled)
    }
}