package ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ayds.newyork.songinfo.moredetails.data.local.nytimes.NYTimesLocalStorage
import com.test.artist.external.entities.Artist.NYTimesArtist

internal class NYTimesLocalStorageImpl(
    context: Context,
    private val cursorToArtistMapper: CursorToArtistMapper
): SQLiteOpenHelper(context, SQLITE_OPEN_HELPER_NAME, null, 1), NYTimesLocalStorage {
    private val projection = arrayOf(
        COLUMN_ID,
        COLUMN_ARTIST,
        COLUMN_INFO
    )

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(CREATE_ARTISTS_TABLE_SQL_QUERY)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    private fun createContentValues(artistName: String, artistDescription: String): ContentValues {
        return ContentValues().apply {
            put(COLUMN_ARTIST, artistName)
            put(COLUMN_INFO, artistDescription)
            put(COLUMN_SOURCE, SOURCE_VALUE)
        }
    }

    override fun insertArtist(artistName: String, artistDescription: String) {
        writableDatabase.insert(
            ARTISTS_TABLE_NAME,
            null,
            createContentValues(artistName, artistDescription)
        )
    }

    override fun getArtistByName(artistName: String): NYTimesArtist? {
        val artistCursor = readableDatabase.query(
            ARTISTS_TABLE_NAME,
            projection,
            SELECTION_FILTER_FOR_NAME,
            arrayOf(artistName),
            null,
            null,
            SELECTION_ORDER_BY
        )

        return cursorToArtistMapper.map(artistCursor)
    }
}