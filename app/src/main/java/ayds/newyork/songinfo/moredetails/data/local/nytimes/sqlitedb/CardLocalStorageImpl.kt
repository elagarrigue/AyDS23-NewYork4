package ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.newyork.songinfo.moredetails.data.local.nytimes.CardLocalStorage
import ayds.newyork.songinfo.moredetails.domain.entities.Source
import ayds.newyork.songinfo.moredetails.domain.entities.Card

internal class CardLocalStorageImpl(
    context: Context,
    private val cursorToCardMapper: CursorToCardMapper
): SQLiteOpenHelper(context, SQLITE_OPEN_HELPER_NAME, null, 1), CardLocalStorage {
    private val projection = arrayOf(
        COLUMN_ID,
        COLUMN_ARTIST,
        COLUMN_INFO,
        COLUMN_SOURCE,
        COLUMN_SOURCE_LOGO
    )

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(CREATE_ARTISTS_TABLE_SQL_QUERY)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    private fun createContentValues(artistName: String, cardInfo: String, source: Source, sourceLogoUrl: String): ContentValues {
        return ContentValues().apply {
            put(COLUMN_ARTIST, artistName)
            put(COLUMN_INFO, cardInfo)
            put(COLUMN_SOURCE, source.name)
            put(COLUMN_SOURCE_LOGO, sourceLogoUrl)
        }
    }

    override fun saveCard(artistName: String, card: Card) {
        writableDatabase.insert(
            ARTISTS_TABLE_NAME,
            null,
            createContentValues(artistName, card.infoUrl!!, card.source, card.sourceLogoUrl)
        )
    }

    override fun getCards(artistName: String): List<Card> {
        val artistCursor = readableDatabase.query(
            ARTISTS_TABLE_NAME,
            projection,
            SELECTION_FILTER_FOR_NAME,
            arrayOf(artistName),
            null,
            null,
            SELECTION_ORDER_BY
        )

        return cursorToCardMapper.map(artistCursor)
    }
}