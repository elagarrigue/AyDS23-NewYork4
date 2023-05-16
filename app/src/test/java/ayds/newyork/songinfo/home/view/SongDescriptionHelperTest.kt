package ayds.newyork.songinfo.home.view

import ayds.newyork.songinfo.home.model.entities.Song
import ayds.newyork.songinfo.home.model.entities.Song.SpotifySong
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

class SongDescriptionHelperTest {

    private val strategyFactory: SpotifySongReleaseDateStrategyFactory = SpotifySongReleaseDateStrategyFactory()
    private val songDescriptionHelper by lazy {
        SongDescriptionHelperImpl(strategyFactory)
    }

    @Test
    fun `given a local song it should return the description with day precision`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1992-01-01",
            "day",
            "url",
            "imageUrl",
            true,
        )
        val result = songDescriptionHelper.getSongDescriptionText(song)
        val expected =
            "Song: Plush [*]\n" +
            "Artist: Stone Temple Pilots\n" +
            "Album: Core\n" +
            "Release date: 01/01/1992\n"
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a local song it should return the description with month precision`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1992-01",
            "month",
            "url",
            "imageUrl",
            true,
        )
        val result = songDescriptionHelper.getSongDescriptionText(song)
        val expected =
            "Song: Plush [*]\n" +
            "Artist: Stone Temple Pilots\n" +
            "Album: Core\n" +
            "Release date: January, 1992\n"
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a local song it should return the description with year precision (leap)`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1992",
            "year",
            "url",
            "imageUrl",
            true,
        )
        val result = songDescriptionHelper.getSongDescriptionText(song)
        val expected =
            "Song: Plush [*]\n" +
            "Artist: Stone Temple Pilots\n" +
            "Album: Core\n" +
            "Release date: 1992 (leap year)\n"
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a local song it should return the description with year precision (not leap)`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1991",
            "year",
            "url",
            "imageUrl",
            true,
        )
        val result = songDescriptionHelper.getSongDescriptionText(song)
        val expected =
            "Song: Plush [*]\n" +
            "Artist: Stone Temple Pilots\n" +
            "Album: Core\n" +
            "Release date: 1991 (not a leap year)\n"
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local song it should return the description with day precision`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1992-01-01",
            "day",
            "url",
            "imageUrl",
            false,
        )
        val result = songDescriptionHelper.getSongDescriptionText(song)
        val expected =
            "Song: Plush \n" +
            "Artist: Stone Temple Pilots\n" +
            "Album: Core\n" +
            "Release date: 01/01/1992\n"
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local song it should return the description with month precision`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1992-01",
            "month",
            "url",
            "imageUrl",
            false,
        )
        val result = songDescriptionHelper.getSongDescriptionText(song)
        val expected =
            "Song: Plush \n" +
            "Artist: Stone Temple Pilots\n" +
            "Album: Core\n" +
            "Release date: January, 1992\n"
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local song it should return the description with year precision (leap)`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1992",
            "year",
            "url",
            "imageUrl",
            false,
        )
        val result = songDescriptionHelper.getSongDescriptionText(song)
        val expected =
            "Song: Plush \n" +
            "Artist: Stone Temple Pilots\n" +
            "Album: Core\n" +
            "Release date: 1992 (leap year)\n"
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non local song it should return the description with year precision (not leap)`() {
        val song: Song = SpotifySong(
            "id",
            "Plush",
            "Stone Temple Pilots",
            "Core",
            "1991",
            "year",
            "url",
            "imageUrl",
            false,
        )
        val result = songDescriptionHelper.getSongDescriptionText(song)
        val expected =
            "Song: Plush \n" +
            "Artist: Stone Temple Pilots\n" +
            "Album: Core\n" +
            "Release date: 1991 (not a leap year)\n"
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `given a non spotify song it should return the song not found description`() {
        val song: Song = mockk()
        val result = songDescriptionHelper.getSongDescriptionText(song)
        val expected = "Song not found"
        Assert.assertEquals(expected, result)
    }
}