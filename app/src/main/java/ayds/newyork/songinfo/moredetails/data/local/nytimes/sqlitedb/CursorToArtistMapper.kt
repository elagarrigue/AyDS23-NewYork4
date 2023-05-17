package ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb

import android.database.Cursor
import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist
import java.sql.SQLException

interface CursorToArtistMapper {
    fun map(cursor: Cursor): NYTimesArtist?
}

internal class CursorToArtistMapperImpl : CursorToArtistMapper {

    override fun map(cursor: Cursor): NYTimesArtist? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    NYTimesArtist(
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