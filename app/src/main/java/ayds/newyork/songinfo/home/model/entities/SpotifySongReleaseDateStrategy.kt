package ayds.newyork.songinfo.home.model.entities

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

interface SpotifySongReleaseDateStrategy {
    fun printReleaseDate(releaseDate: String): String
}

object SpotifySongReleaseDateByYearStrategy: SpotifySongReleaseDateStrategy {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun printReleaseDate(releaseDate: String): String {
        val year = releaseDate.split("-").first().toInt()
        return if(Calendar.isGregorianLeapYear(year)){
            "$year (leap year)"
        } else {
            "$year (not a leap year)"
        }
    }
}

object SpotifySongReleaseDateByMonthStrategy: SpotifySongReleaseDateStrategy {
    override fun printReleaseDate(releaseDate: String): String {
        val monthDate = SimpleDateFormat("MMMMM, yyyy", Locale("en"))
        val info = releaseDate.split("-")
        return monthDate.format(Calendar.getInstance().set(info[0].toInt(), info[1].toInt(), info[2].toInt()))
    }
}

object SpotifySongReleaseDateByDayStrategy: SpotifySongReleaseDateStrategy {
    override fun printReleaseDate(releaseDate: String): String {
        val info = releaseDate.split("-")
        return "${info[2]}/${info[1]}/${info[0]}"
    }
}