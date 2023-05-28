package ayds.newyork.songinfo.moredetails.presentation
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
        val card: Card = Card(
                "Great artist info",
                "https://thepepe.com/",
                 Source.NYTimes,
                "",
                 false
        )

        val result = cardDescriptionHelper.getCardDescriptionText(card)

        val expected =
            "[*]Great artist info"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local song it should return the description`() {
        val card: Card = Card(
                "Great artist info",
                "https://thepepe.com/",
                 Source.NYTimes,
                "",
                false
        )

        val result = cardDescriptionHelper.getCardDescriptionText(card)

        val expected =
            "Great artist info"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non spotify song it should return the song not found description`() {
        val card: Card = mockk()

        val result = cardDescriptionHelper.getCardDescriptionText(card)

        val expected = "No Results"

        Assert.assertEquals(expected, result)
    }
}