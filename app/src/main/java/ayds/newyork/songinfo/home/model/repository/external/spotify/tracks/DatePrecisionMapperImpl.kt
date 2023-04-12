package ayds.newyork.songinfo.home.model.repository.external.spotify.tracks

import ayds.newyork.songinfo.home.view.DatePrecision
import java.lang.Exception

private const val SPOTIFY_DAY = "day"
private const val SPOTIFY_MONTH = "month"
private const val SPOTIFY_YEAR = "year"

interface DatePrecisionMapper{
    fun getDatePrecision(spotifyDatePrecision: String) : DatePrecision
}

internal class DatePrecisionMapperImpl : DatePrecisionMapper {
    override fun getDatePrecision(spotifyDatePrecision: String): DatePrecision =
            when(spotifyDatePrecision){
                SPOTIFY_DAY -> DatePrecision.DAY
                SPOTIFY_MONTH -> DatePrecision.MONTH
                SPOTIFY_YEAR -> DatePrecision.YEAR
                else -> throw Exception("Precision received unsupported.")
            }
}