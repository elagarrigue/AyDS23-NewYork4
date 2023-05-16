package ayds.newyork.songinfo.moredetails.presentation

import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist
import ayds.newyork.songinfo.moredetails.domain.entities.Artist
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class ArtistInfoHelperTest {

    private val artistInfoHelper by lazy { ArtistInfoHelperImpl() }

    @Test
    fun `given a local artist it should return the description`() {
        val artist: Artist = NYTimesArtist(
            "https://thepepe.com/",
            "Great artist info",
            true,
        )

        val result = artistInfoHelper.getArtistText(artist)

        val expected =
            "[*]Great artist info"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local song it should return the description`() {
        val artist: Artist = NYTimesArtist(
            "https://thepepe.com/",
            "Great artist info",
            false,
        )

        val result = artistInfoHelper.getArtistText(artist)

        val expected =
            "Great artist info"

        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non spotify song it should return the song not found description`() {
        val artist: Artist = mockk()

        val result = artistInfoHelper.getArtistText(artist)

        val expected = "Artist not found"

        Assert.assertEquals(expected, result)
    }
}