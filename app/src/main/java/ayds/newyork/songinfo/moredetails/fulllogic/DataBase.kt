package ayds.newyork.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DataBase(context: Context): SQLiteOpenHelper(context, SQLITE_OPEN_HELPER_NAME, null, 1) {
    companion object {
        const val ARTISTS_TABLE_NAME = "artists"
        const val COLUMN_ID = "id"
        const val COLUMN_ARTIST = "artist"
        const val COLUMN_SOURCE = "source"
        const val COLUMN_INFO = "info"
        const val SELECTION_FILTER = "$COLUMN_ARTIST = ?"
        const val SELECTION_ORDER_BY = "$COLUMN_ARTIST DESC"
        const val SQLITE_OPEN_HELPER_NAME = "dictionary.db"
        const val SOURCE_VALUE = 1
        const val CREATE_ARTISTS_TABLE_SQL_QUERY = "create table $ARTISTS_TABLE_NAME ($COLUMN_ID integer primary key autoincrement, $COLUMN_ARTIST string, $COLUMN_INFO string, $COLUMN_SOURCE integer)"
    }

    override fun onCreate(database: SQLiteDatabase) {
        database.execSQL(CREATE_ARTISTS_TABLE_SQL_QUERY)
    }

    override fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

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

    fun getArtistInfo(artistName: String): ArtistInfo? {
        val info = getInfoColumn(getArtist(artistName))
        return if(info != null){
            return ArtistInfo(info)
        } else {
            null
        }
    }

    private fun getArtist(artist: String): Cursor {
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

    private fun getInfoColumn(query: Cursor): String? {
        var info: String? = null
        if (query.moveToNext()) {
            info = query.getString(query.getColumnIndexOrThrow(COLUMN_INFO))
        }
        query.close()
        return info
    }
}