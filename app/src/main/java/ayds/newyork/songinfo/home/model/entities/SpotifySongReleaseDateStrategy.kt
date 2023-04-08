package ayds.newyork.songinfo.home.model.patterns.strategy.songs

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.util.*

interface SpotifySongReleaseDateStrategy {

    fun printReleaseDate(releaseDate: String): String

}

class SpotifySongReleaseDateByDefaultStrategy: SpotifySongReleaseDateStrategy {

    override fun printReleaseDate(releaseDate: String): String {
        return ""
    }

}

class SpotifySongReleaseDateByYearStrategy: SpotifySongReleaseDateStrategy {

    override fun printReleaseDate(releaseDate: String): String {
        return releaseDate.split("-").first()
    }

}

class SpotifySongReleaseDateByMonthStrategy: SpotifySongReleaseDateStrategy {

    override fun printReleaseDate(releaseDate: String): String {
        val monthDate = SimpleDateFormat("MMMM, YYYY", Locale("en"))
        val info = releaseDate.split("-")
        return monthDate.format(Calendar.getInstance().set(info[0].toInt(), info[1].toInt(), info[2].toInt()))
    }

}

class SpotifySongReleaseDateByDayStrategy: SpotifySongReleaseDateStrategy {

    override fun printReleaseDate(releaseDate: String): String {
        val info = releaseDate.split("-")
        return "${info[2]}/${info[1]}/${info[0]}"
    }

}