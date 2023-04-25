package ayds.newyork.songinfo.moredetails.fulllogic

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor
import android.util.Log

private const val ARTISTS_TABLE_NAME = "artists"
private const val ARTIST_NAME_COLUMN = "artist"
private const val INFO_COLUMN = "info"
private const val ID_COLUMN = "id"
private const val WHERE_COLUMN = "$ARTIST_NAME_COLUMN = ?"
private const val SOURCE_COLUMN = "source"
private const val SOURCE_VALUE = 1

class DataBase(context: Context?) : SQLiteOpenHelper(context, "dictionary.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "create table artists (id INTEGER PRIMARY KEY AUTOINCREMENT, artist string, info string, source integer)"
        )
        Log.i("DB", "DB created")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    fun saveArtist(artist: String, info: String) {
        val values = createContentValues(artist,info)
        this.writableDatabase?.insert(ARTISTS_TABLE_NAME, null, values)
    }

    private fun createContentValues(artist: String?, info:String?): ContentValues {
        val values = ContentValues().apply {
            values.put(ARTIST_NAME_COLUMN, artist)
            values.put(INFO_COLUMN, info)
            values.put(SOURCE_COLUMN, SOURCE_VALUE)
        }
        return values
    }

    fun getInfo(artist: String): String? {
        val cursor = createCursor(artist)
        val items = addInfoToList(cursor)
        return items.firstOrNull()
    }

    private fun createCursor(artist: String): Cursor {
        val cursor = this.readableDatabase.query(
            ARTISTS_TABLE_NAME,
            arrayOf(
                  ID_COLUMN,
                  ARTIST_NAME_COLUMN,
                  INFO_COLUMN
            ),
            WHERE_COLUMN,
            arrayOf(artist),
            null,
            null,
            "$ARTIST_NAME_COLUMN DESC"
        )
        return cursor;
    }

    private fun addInfoToList(cursor: Cursor): MutableList<String>{
            val items: MutableList<String> = ArrayList()
            while (cursor.moveToNext()) {
                val info = cursor.getString(
                    cursor.getColumnIndexOrThrow("info")
                )
                items.add(info)
            }
            cursor.close()
            return items;
    }
}