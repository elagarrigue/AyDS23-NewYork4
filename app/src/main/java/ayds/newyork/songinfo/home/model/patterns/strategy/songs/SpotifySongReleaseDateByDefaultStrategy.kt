package ayds.newyork.songinfo.home.model.patterns.strategy.songs

class SpotifySongReleaseDateByDefaultStrategy: SpotifySongReleaseDateStrategy {

    override fun printReleaseDate(releaseDate: String): String {
        return ""
    }

}