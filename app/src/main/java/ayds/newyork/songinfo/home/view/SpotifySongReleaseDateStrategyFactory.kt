package ayds.newyork.songinfo.home.view

private const val SPOTIFY_DAY = "day"
private const val SPOTIFY_MONTH = "month"
private const val SPOTIFY_YEAR = "year"

object SpotifySongReleaseDateStrategyFactory {
    fun getStrategy(releaseDatePrecision : String) : SpotifySongReleaseDateStrategy {
        return when (releaseDatePrecision){
            SPOTIFY_YEAR -> SpotifySongReleaseDateByYearStrategy()
            SPOTIFY_MONTH -> SpotifySongReleaseDateByMonthStrategy()
            SPOTIFY_DAY -> SpotifySongReleaseDateByDayStrategy()
            else -> SpotifySongReleaseDateByDefaultStrategy()
        }
    }
}