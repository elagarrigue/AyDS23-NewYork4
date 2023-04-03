package ayds.newyork.songinfo.home.model.patterns.strategy.songs

class SpotifySongReleaseDateByYearStrategy: SpotifySongReleaseDateStrategy {

    override fun printReleaseDate(releaseDate: String): String {
        return releaseDate.split("-").first()
    }

}