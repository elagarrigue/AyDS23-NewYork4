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
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INFO)),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_URL)),
                Source.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SOURCE))),
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOGO)),
                true
            )
            cards.add(card)
        }
        cursor.close()

        return cards
    }
}