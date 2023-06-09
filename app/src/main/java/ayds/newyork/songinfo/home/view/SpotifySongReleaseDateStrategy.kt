package ayds.newyork.songinfo.home.view

import java.text.SimpleDateFormat
import java.util.*

interface SpotifySongReleaseDateStrategy {
    fun getFormattedReleaseDate(releaseDate: String): String
}

class SpotifySongReleaseDateByYearStrategy: SpotifySongReleaseDateStrategy {
    private fun isLeapYear(year: Int) = (year % 4 == 0) && (year % 100 != 0 || year % 400 == 0)

    override fun getFormattedReleaseDate(releaseDate: String): String {
        val year = releaseDate.toInt()
        return if(isLeapYear(year)){
            "$year (leap year)"
        } else {
            "$year (not a leap year)"
        }
    }
}

class SpotifySongReleaseDateByMonthStrategy: SpotifySongReleaseDateStrategy {
    override fun getFormattedReleaseDate(releaseDate: String): String {
        val monthDate = SimpleDateFormat("MMMM, yyyy", Locale("en"))
        val info = releaseDate.split("-")
        val calendar = Calendar.getInstance()
        calendar[Calendar.YEAR] = info[0].toInt()
        calendar[Calendar.MONTH] = info[1].toInt() - 1
        return monthDate.format(calendar.time)
    }
}

class SpotifySongReleaseDateByDayStrategy: SpotifySongReleaseDateStrategy {
    override fun getFormattedReleaseDate(releaseDate: String): String {
        val info = releaseDate.split("-")
        return "${info[2]}/${info[1]}/${info[0]}"
    }
}

class SpotifySongReleaseDateByDefaultStrategy: SpotifySongReleaseDateStrategy {
    override fun getFormattedReleaseDate(releaseDate: String): String {
        return "Date unknown"
    }
}
