package ayds.newyork.songinfo.home.model.entities

object SpotifySongReleaseDateStrategyInjector {
    private val yearStrategy : SpotifySongReleaseDateStrategy = SpotifySongReleaseDateByYearStrategy
    private val monthStrategy  : SpotifySongReleaseDateStrategy = SpotifySongReleaseDateByMonthStrategy
    private val dayStrategy : SpotifySongReleaseDateStrategy = SpotifySongReleaseDateByDayStrategy

    fun getStrategy(releaseDatePrecision : DatePrecision) : SpotifySongReleaseDateStrategy {
        return when (releaseDatePrecision){
            DatePrecision.YEAR -> yearStrategy
            DatePrecision.MONTH -> monthStrategy
            DatePrecision.DAY -> dayStrategy
        }
    }
}