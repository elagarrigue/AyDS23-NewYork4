package ayds.newyork.songinfo.moredetails.presentation
import ayds.newYork4.artist.external.entities.NY_TIMES_LOGO_URL
import ayds.newyork.songinfo.moredetails.presentation.view.CardDescriptionHelperImpl
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class CardDescriptionHelperTest {

    private val cardDescriptionHelper by lazy { CardDescriptionHelperImpl() }

    @Test
    fun `given a local artist it should return the description`() {
        val card = Card(
            "Great artist info about Frank Sinatra",
            "https://thepepe.com/",
            Source.NYTimes,
            NY_TIMES_LOGO_URL,
            true
        )

        val result = cardDescriptionHelper.getCardDescriptionText(card, "Frank Sinatra")
        val expected = "<html><div width=400><font face=\"arial\">[*]Great artist info about <b>FRANK SINATRA</b></font></div></html>"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local artist it should return the description`() {
        val card = Card(
            "Great artist info about Frank Sinatra",
            "https://thepepe.com/",
            Source.NYTimes,
            NY_TIMES_LOGO_URL
        )

        val result = cardDescriptionHelper.getCardDescriptionText(card, "Frank Sinatra")
        val expected = "<html><div width=400><font face=\"arial\">Great artist info about <b>FRANK SINATRA</b></font></div></html>"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given no artist info found, it returns the artist not found description`() {
        val card = Card(
            "",
            null,
            Source.NYTimes,
            NY_TIMES_LOGO_URL
        )

        val result = cardDescriptionHelper.getCardDescriptionText(card, "Frank Sinatra")
        val expected = "No Results"

        Assert.assertEquals(expected, result)
    }
}