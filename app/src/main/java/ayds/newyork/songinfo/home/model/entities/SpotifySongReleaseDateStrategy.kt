package ayds.newyork.songinfo.home.model.entities

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

interface SpotifySongReleaseDateStrategy {
    fun printReleaseDate(releaseDate: String): String
}

object SpotifySongReleaseDateByYearStrategy: SpotifySongReleaseDateStrategy {
    override fun printReleaseDate(releaseDate: String): String {
        return releaseDate.split("-").first()
    }
}

object SpotifySongReleaseDateByMonthStrategy: SpotifySongReleaseDateStrategy {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun printReleaseDate(releaseDate: String): String {
        val monthDate = SimpleDateFormat("MMMM, YYYY", Locale("en"))
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