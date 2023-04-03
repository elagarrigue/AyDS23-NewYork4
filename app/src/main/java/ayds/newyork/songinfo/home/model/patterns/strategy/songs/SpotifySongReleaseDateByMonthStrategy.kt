package ayds.newyork.songinfo.home.model.patterns.strategy.songs

import java.text.SimpleDateFormat
import java.util.*

class SpotifySongReleaseDateByMonthStrategy: SpotifySongReleaseDateStrategy {

    override fun printReleaseDate(releaseDate: String): String {
        val monthDate = SimpleDateFormat("MMMM, YYYY", Locale("en"))
        val info = releaseDate.split("-")
        return monthDate.format(Calendar.getInstance().set(info[0].toInt(), info[1].toInt(), info[2].toInt()))
    }

}