package ayds.newyork.songinfo.home.view

import ayds.newyork.songinfo.home.model.entities.Song.EmptySong
import ayds.newyork.songinfo.home.model.entities.Song
import ayds.newyork.songinfo.home.model.entities.Song.SpotifySong

interface SongDescriptionHelper {
    fun getSongDescriptionText(song: Song = EmptySong): String
    fun getSongReleaseDate(song: SpotifySong): String
}

internal class SongDescriptionHelperImpl(private val songReleaseDateStrategyFactory : SpotifySongReleaseDateStrategyFactory) : SongDescriptionHelper {
    override fun getSongDescriptionText(song: Song): String {
        return when (song) {
            is SpotifySong ->
                "${
                    "Song: ${song.songName} " +
                            if (song.isLocallyStored) "[*]" else ""
                }\n" +
                        "Artist: ${song.artistName}\n" +
                        "Album: ${song.albumName}\n" +
                        "Release date: ${getSongReleaseDate(song)}\n"
            else -> "Song not found"
        }
    }
    override fun getSongReleaseDate(song: SpotifySong) : String = songReleaseDateStrategyFactory.getStrategy(song.releaseDatePrecision).getFormattedReleaseDate(song.releaseDate)
}