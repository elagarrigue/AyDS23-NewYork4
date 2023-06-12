package ayds.newyork.songinfo.moredetails.presentation.presenter

import ayds.newyork.songinfo.moredetails.domain.entities.Card
import java.util.Locale

interface CardDescriptionHelper {
    fun getCardDescriptionText(card: Card, artistName: String): String
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

    override fun getCardDescriptionText(card: Card, artistName: String): String {
        card.description = when {
            card.isLocallyStored -> LOCALLY_STORED
            else -> NOT_LOCALLY_STORED
        } + card.description
        return when {
            card.description.isNotEmpty() -> formatAbstractArtist(card.description, artistName)
            else -> DEFAULT_CARD_DESCRIPTION_RESULT_TEXT
        }
    }

    private fun formatAbstractArtist(
        documentAbstractArtist: String,
        artistName: String
    ): String {
        val formattedArtist = documentAbstractArtist.replace(
            ESCAPED_NEW_LINE_TEXT,
            ESCAPED_NEW_LINE
        )
        return textToHtml(formattedArtist, artistName)
    }

    private fun textToHtml(text: String, term: String?): String = StringBuilder().apply {
        append(BEGIN_HTML)
        append(getBoldTextInHtml(text, term))
        append(END_HTML)
    }.toString()

    private fun getBoldTextInHtml(text: String, term: String?): String = text
        .replace("'", " ")
        .replace(ESCAPED_NEW_LINE, HTML_NEW_LINE)
        .replace(
            "(?i)$term".toRegex(),
            HTML_OPEN_BOLD + term!!.uppercase(Locale.getDefault()) + HTML_CLOSE_BOLD
        )
}