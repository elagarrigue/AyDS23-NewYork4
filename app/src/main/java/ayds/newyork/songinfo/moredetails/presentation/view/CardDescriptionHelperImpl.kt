package ayds.newyork.songinfo.moredetails.presentation.view

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import com.google.gson.JsonElement
import java.util.Locale

interface CardDescriptionHelper {
    fun getCardDescriptionText(card: Card): String
    fun formatAbstractArtist(
            documentAbstractArtist: JsonElement?,
            artistName: String
    ): String
}

class CardDescriptionHelperImpl : CardDescriptionHelper {
    companion object {
        const val DEFAULT_CARD_DESCRIPTION_RESULT_TEXT = "No Results"
        const val BEGIN_HTML = "<html><div width=400><font face=\"arial\">"
        const val END_HTML = "</font></div></html>"
        const val ESCAPED_NEW_LINE_TEXT = "\\n"
        const val ESCAPED_NEW_LINE = "\n"
        const val HTML_NEW_LINE = "<br>"
        const val HTML_OPEN_BOLD = "<b>"
        const val HTML_CLOSE_BOLD = "</b>"
        const val LOCALLY_STORED = "[*]"
        const val NOT_LOCALLY_STORED = ""
    }

    override fun getCardDescriptionText(card: Card): String {
        return if (card != null) {
            "${
                if (card.isLocallyStored)
                    LOCALLY_STORED
                else
                    NOT_LOCALLY_STORED
            }\n" +
                    if (card.description.isEmpty()){
                        DEFAULT_CARD_DESCRIPTION_RESULT_TEXT
                    }
                    else {
                        card.description
                    }
        }
        else {
            DEFAULT_CARD_DESCRIPTION_RESULT_TEXT
        }
    }

    override fun formatAbstractArtist(
            documentAbstractArtist: JsonElement?,
            artistName: String
    ): String {
        var formattedArtist = DEFAULT_CARD_DESCRIPTION_RESULT_TEXT
        if (documentAbstractArtist != null) {
            formattedArtist = documentAbstractArtist.asString.replace(
                    ESCAPED_NEW_LINE_TEXT,
                    ESCAPED_NEW_LINE
            )
            formattedArtist = textToHtml(formattedArtist, artistName)
        }
        return formattedArtist
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