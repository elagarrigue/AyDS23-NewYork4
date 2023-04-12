package ayds.newyork.songinfo.home.view

object SpotifySongReleaseDateStrategyFactory {
    fun getStrategy(releaseDatePrecision : DatePrecision) : SpotifySongReleaseDateStrategy {
        return when (releaseDatePrecision){
            DatePrecision.YEAR -> YearStrategyCreator().create()
            DatePrecision.MONTH -> MonthStrategyCreator().create()
            DatePrecision.DAY -> DayStrategyCreator().create()
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

