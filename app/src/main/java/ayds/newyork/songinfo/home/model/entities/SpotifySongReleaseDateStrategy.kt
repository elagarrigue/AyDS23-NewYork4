package ayds.newyork.songinfo.home.model.entities

import android.icu.util.GregorianCalendar
import java.text.SimpleDateFormat
import java.util.*

interface SpotifySongReleaseDateStrategy {
    fun printReleaseDate(releaseDate: String): String
}

class ReleaseDateCalendar: GregorianCalendar() {

    companion object {
        fun isLeapYear(year: Int): Boolean = isGregorianLeapYear(year)
    }

}

object SpotifySongReleaseDateByYearStrategy: SpotifySongReleaseDateStrategy {
    override fun printReleaseDate(releaseDate: String): String {
        val year = releaseDate.toInt()
        return if(ReleaseDateCalendar.isLeapYear(year)){
            "$year (leap year)"
        } else {
            "$year (not a leap year)"
        }
    }
}

object SpotifySongReleaseDateByMonthStrategy: SpotifySongReleaseDateStrategy {
    override fun printReleaseDate(releaseDate: String): String {
        val monthDate = SimpleDateFormat("MMMM, yyyy", Locale("en"))
        val info = releaseDate.split("-")
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = info[0].toInt()
        calendar[Calendar.MONTH] = info[1].toInt() - 1
        return monthDate.format(calendar.time)
    }
}

object SpotifySongReleaseDateByDayStrategy: SpotifySongReleaseDateStrategy {
    override fun printReleaseDate(releaseDate: String): String {
        val info = releaseDate.split("-")
        return "${info[2]}/${info[1]}/${info[0]}"
    }
}