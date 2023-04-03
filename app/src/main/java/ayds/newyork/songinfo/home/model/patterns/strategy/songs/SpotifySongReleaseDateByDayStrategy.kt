package ayds.newyork.songinfo.home.model.patterns.strategy.songs

class SpotifySongReleaseDateByDayStrategy: SpotifySongReleaseDateStrategy {

    override fun printReleaseDate(releaseDate: String): String {
        val info = releaseDate.split("-")
        return "${info[2]}/${info[1]}/${info[0]}"
    }

}