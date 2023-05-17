package ayds.newyork.songinfo.moredetails.presentation

import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.utils.UtilsInjector.navigationUtils
import com.squareup.picasso.Picasso
import io.mockk.*
import junit.framework.TestCase.*
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class MoreDetailsPresenterTest {
    private val activity: AppCompatActivity = mockk()
    private lateinit var presenter: MoreDetailsPresenter

    @Before
    fun setUp(){
        every { Picasso.get() } returns mockk()
        MoreDetailsViewInjector.init(activity)
        presenter = MoreDetailsViewInjector.getMoreDetailsPresenter()
    }

    @Test
    fun `onButtonClicked should open external URL if available`() {
        val activity: AppCompatActivity = mockk()

        val captor = slot<String>()
        every { navigationUtils.openExternalUrl(activity, capture(captor)) } just Runs

        presenter.onButtonClicked(activity)

        val capturedUrl = captor.captured
        assertNotNull(capturedUrl)
    }

    @Test
    fun `onButtonClicked should not open external URL if not available`() {
        val activity: AppCompatActivity = mockk()

        val captor = slot<String>()
        every { navigationUtils.openExternalUrl(activity, capture(captor)) } just Runs

        presenter.onButtonClicked(activity)

        val capturedUrl = captor.captured
        assertNull(capturedUrl)
    }

    @Test
    fun `updateArtist should notify observers with NYTimesArtist`() {
        val artistName = "John Doe"

        presenter.uiStateObservable.subscribe{
            uiState->Assert.assertNotNull(uiState)
        }
        presenter.updateArtist(artistName)

    }

    @Test
    fun `updateArtist should notify observers with EmptyArtist`() {
        val artistName = "No results found"

        presenter.uiStateObservable.subscribe { uiState ->
            Assert.assertNotNull(uiState)
        }
        presenter.updateArtist(artistName)
    }
}
