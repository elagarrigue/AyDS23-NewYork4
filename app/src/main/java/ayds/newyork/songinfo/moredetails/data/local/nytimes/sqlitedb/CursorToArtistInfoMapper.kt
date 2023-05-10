package ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb

import android.database.Cursor
import ayds.newyork.songinfo.moredetails.domain.entities.Artist
import java.sql.SQLException

interface CursorToArtistMapper {
    fun map(cursor: Cursor): Artist?
}

internal class CursorToArtistMapperImpl : CursorToArtistMapper {

    override fun map(cursor: Cursor): Artist? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    Artist(
                        info = getString(getColumnIndexOrThrow(COLUMN_INFO)),
                        isLocallyStored = true,
                    )
                } else {
                    null
                }
            }
        } catch (e: SQLException) {
            e.printStackTrace()
            null
        }
}