package ayds.newyork.songinfo.home.model.entities

sealed class Song {

    data class SpotifySong(
        val id: String,
        val songName: String,
        val artistName: String,
        val albumName: String,
        val releaseDate: String,
        val releaseDatePrecision: String,
        val spotifyUrl: String,
        val imageUrl: String,
        var isLocallyStored: Boolean = false,
    ) : Song() {
        val year: String = releaseDate.split("-").first()

        private val releaseDatePrecisionStrategy : SpotifySongReleaseDateStrategy = when (releaseDatePrecision){
           "year" -> SpotifySongReleaseDateByYearStrategy()
           "month" -> SpotifySongReleaseDateByMonthStrategy()
           "day" -> SpotifySongReleaseDateByDayStrategy()
           else -> SpotifySongReleaseDateByDefaultStrategy()
       }

       fun printReleaseDate() = releaseDatePrecisionStrategy.printReleaseDate(releaseDate)
    }

    object EmptySong : Song()
}