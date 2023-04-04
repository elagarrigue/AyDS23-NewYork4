package ayds.newyork.songinfo.home.model.patterns.strategy.songs

interface SpotifySongReleaseDateStrategy {

    fun printReleaseDate(releaseDate: String): String

}