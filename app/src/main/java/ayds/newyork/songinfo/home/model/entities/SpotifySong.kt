package ayds.newyork.songinfo.home.model.entities

import ayds.newyork.songinfo.home.view.DatePrecision

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
    ) : Song()
    object EmptySong : Song()
    
}