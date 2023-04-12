package ayds.newyork.songinfo.home.view

object SpotifySongReleaseDateStrategyFactory {
    fun getStrategy(releaseDatePrecision : DatePrecision) : SpotifySongReleaseDateStrategy {
        return when (releaseDatePrecision){
            DatePrecision.YEAR -> SpotifySongReleaseDateByYearStrategy
            DatePrecision.MONTH -> SpotifySongReleaseDateByMonthStrategy
            DatePrecision.DAY -> SpotifySongReleaseDateByDayStrategy
        }
    }
}