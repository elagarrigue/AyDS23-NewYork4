package ayds.newyork.songinfo.home.view

private const val SPOTIFY_DAY = "day"
private const val SPOTIFY_MONTH = "month"
private const val SPOTIFY_YEAR = "year"

object SpotifySongReleaseDateStrategyFactory {
    fun getStrategy(releaseDatePrecision : String) : SpotifySongReleaseDateStrategy {
        return when (releaseDatePrecision){
            SPOTIFY_YEAR -> YearStrategyCreator().create()
            SPOTIFY_MONTH -> MonthStrategyCreator().create()
            SPOTIFY_DAY -> DayStrategyCreator().create()
            else -> DefaultStrategyCreator().create()
        }
    }
}
interface SpotifySongReleaseDateStrategyCreator {
    fun create() : SpotifySongReleaseDateStrategy
}

class YearStrategyCreator : SpotifySongReleaseDateStrategyCreator {
    override fun create() : SpotifySongReleaseDateStrategy {
         return SpotifySongReleaseDateByYearStrategy()
    }
}

class MonthStrategyCreator : SpotifySongReleaseDateStrategyCreator {
    override fun create() : SpotifySongReleaseDateStrategy {
        return SpotifySongReleaseDateByMonthStrategy()
    }
}

class DayStrategyCreator : SpotifySongReleaseDateStrategyCreator {
    override fun create() : SpotifySongReleaseDateStrategy {
        return SpotifySongReleaseDateByDayStrategy()
    }
}

class DefaultStrategyCreator : SpotifySongReleaseDateStrategyCreator {
    override fun create() : SpotifySongReleaseDateStrategy {
        return SpotifySongReleaseDateByDefaultStrategy()
    }
}