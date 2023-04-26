package ayds.newyork.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val LOG_TAG = "DB"
private const val ARTISTS_TABLE_NAME = "artists"
private const val COLUMN_ID = "id"
private const val COLUMN_ARTIST = "artist"
private const val COLUMN_SOURCE = "source"
private const val COLUMN_INFO = "info"
private const val SELECTION_FILTER = "$COLUMN_ARTIST = ?"
private const val SELECTION_ORDER_BY = "$COLUMN_ARTIST = DESC"
private const val SQLITE_OPEN_HELPER_NAME = "dictionary.db"
private const val SOURCE_VALUE = 1

class DataBase(context: Context): SQLiteOpenHelper(context, SQLITE_OPEN_HELPER_NAME, null, 1) {

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL("create table $ARTISTS_TABLE_NAME ($COLUMN_ID integer primary key autoincrement, $COLUMN_ARTIST string, $COLUMN_INFO string, $COLUMN_SOURCE integer)")
        Log.i(LOG_TAG, "'$ARTISTS_TABLE_NAME' database created")
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO: make sure we do not actually need to perform any actions.
    }

    fun saveArtist(artist: String, info: String) {
        writableDatabase.insert(
            ARTISTS_TABLE_NAME,
            null,
            createContentValues(artist, info)
        )
    }

    private fun createContentValues(artist: String, info: String): ContentValues {
        return ContentValues().apply {
            put(COLUMN_ARTIST, artist)
            put(COLUMN_INFO, info)
            put(COLUMN_SOURCE, SOURCE_VALUE)
        }
    }

    fun getArtistInfo(artist: String): String? {
        var artistInfo: String? = null
        val cursor = createCursor(artist)
        if (cursor.moveToNext()) {
            artistInfo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INFO))
        }
        cursor.close()
        return artistInfo
    }

    private fun createCursor(artist: String): Cursor {
        return readableDatabase.query(
            ARTISTS_TABLE_NAME,
            arrayOf(
                COLUMN_ID,
                COLUMN_ARTIST,
                COLUMN_INFO
            ),
            SELECTION_FILTER,
            arrayOf(artist),
            null,
            null,
            SELECTION_ORDER_BY
        )
    }

}