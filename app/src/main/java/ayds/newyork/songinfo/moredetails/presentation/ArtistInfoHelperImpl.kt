package ayds.newyork.songinfo.moredetails.presentation

import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import com.google.gson.JsonElement
import java.util.Locale

interface ArtistInfoHelper {
    fun getArtistInfoText(artistInfo: ArtistInfo): String?
    fun formatAbstractArtistInfo(
        documentAbstractArtistInfo: JsonElement?,
        artistName: String
    ): String
}

class ArtistInfoHelperImpl : ArtistInfoHelper {
    companion object {
        const val DEFAULT_ARTIST_INFO_RESULT_TEXT = "No Results"
        const val BEGIN_HTML = "<html><div width=400><font face=\"arial\">"
        const val END_HTML = "</font></div></html>"
        const val ESCAPED_NEW_LINE_TEXT = "\\n"
        const val ESCAPED_NEW_LINE = "\n"
        const val HTML_NEW_LINE = "<br>"
        const val HTML_OPEN_BOLD = "<b>"
        const val HTML_CLOSE_BOLD = "</b>"
    }

    override fun getArtistInfoText(artistInfo: ArtistInfo): String? {
        if(artistInfo.isLocallyStored){
            return "[*]${artistInfo.info}"
        }
        return artistInfo.info
    }

    override fun formatAbstractArtistInfo(
        documentAbstractArtistInfo: JsonElement?,
        artistName: String
    ): String {
        var formattedArtistInfo = DEFAULT_ARTIST_INFO_RESULT_TEXT
        if (documentAbstractArtistInfo != null) {
            formattedArtistInfo = documentAbstractArtistInfo.asString.replace(
                ESCAPED_NEW_LINE_TEXT,
                ESCAPED_NEW_LINE
            )
            formattedArtistInfo = textToHtml(formattedArtistInfo, artistName)
        }
        return formattedArtistInfo
    }

    private fun textToHtml(text: String, term: String?): String {
        return StringBuilder().apply {
            append(BEGIN_HTML)
            append(getBoldTextInHtml(text, term))
            append(END_HTML)
        }.toString()
    }

    private fun getBoldTextInHtml(text: String, term: String?): String {
        return text
            .replace("'", " ")
            .replace(ESCAPED_NEW_LINE, HTML_NEW_LINE)
            .replace(
                "(?i)$term".toRegex(),
                HTML_OPEN_BOLD + term!!.uppercase(Locale.getDefault()) + HTML_CLOSE_BOLD
            )
    }
}