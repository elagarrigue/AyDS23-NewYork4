package ayds.newyork.songinfo.home.model.entities

import ayds.newyork.songinfo.home.model.patterns.strategy.songs.*

sealed class Song {

    data class SpotifySong(
        val id: String,
        val songName: String,
        val artistName: String,
        val albumName: String,
        val releaseDate: String,
        val releaseDatePrecision: DatePrecision,
        val spotifyUrl: String,
        val imageUrl: String,
        var isLocallyStored: Boolean = false,
    ) : Song() {
        val year: String = releaseDate.split("-").first()


       fun printReleaseDate(){
           val actualStrategy = SpotifySongReleaseDateStrategyInjector.getStrategy(releaseDatePrecision)
           actualStrategy.printReleaseDate(releaseDate)
       }
    }

    object EmptySong : Song()
}