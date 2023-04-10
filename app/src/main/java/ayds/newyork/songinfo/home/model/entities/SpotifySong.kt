package ayds.newyork.songinfo.home.model.entities

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
        private val releaseDatePrecisionStrategy : SpotifySongReleaseDateStrategy = when (releaseDatePrecision){
            DatePrecision.DAY -> SpotifySongReleaseDateByYearStrategy()
            DatePrecision.MONTH -> SpotifySongReleaseDateByMonthStrategy()
            DatePrecision.YEAR -> SpotifySongReleaseDateByDayStrategy()
        }
       fun printReleaseDate() = releaseDatePrecisionStrategy.printReleaseDate(releaseDate)
    }

    object EmptySong : Song()
}