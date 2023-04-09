package ayds.newyork.songinfo.home.model.entities

import ayds.newyork.songinfo.home.model.patterns.strategy.songs.*

object SpotifySongReleaseDateStrategyInjector {
    private val yearStrategy : SpotifySongReleaseDateStrategy = SpotifySongReleaseDateByYearStrategy
    private val monthStrategy  : SpotifySongReleaseDateStrategy = SpotifySongReleaseDateByMonthStrategy
    private val dayStrategy : SpotifySongReleaseDateStrategy = SpotifySongReleaseDateByDayStrategy
    private val defaultStrategy : SpotifySongReleaseDateStrategy = SpotifySongReleaseDateByDefaultStrategy

    fun getStrategy(releaseDatePrecision : DatePrecision) : SpotifySongReleaseDateStrategy{
        return when (releaseDatePrecision){
            DatePrecision.DAY -> yearStrategy
            DatePrecision.MONTH -> monthStrategy
            DatePrecision.YEAR -> dayStrategy
            else -> defaultStrategy
        }
    }
}