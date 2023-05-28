package ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb

import android.database.Cursor
import ayds.newyork.songinfo.moredetails.domain.entities.Card
import ayds.newyork.songinfo.moredetails.domain.entities.Source

interface CursorToCardMapper {
    fun map(cursor: Cursor): List<Card>
}

internal class CursorToCardMapperImpl : CursorToCardMapper {

    override fun map(cursor: Cursor): List<Card> {
        val cards: MutableList<Card> = mutableListOf()
        while (cursor.moveToNext()) {
            val card = Card(
                    description = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INFO)),
                    source = Source.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOURCE))),
                    sourceLogoUrl = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOURCE_LOGO)),
            )
            cards.add(card)
        }
        cursor.close()

        return cards
    }
}