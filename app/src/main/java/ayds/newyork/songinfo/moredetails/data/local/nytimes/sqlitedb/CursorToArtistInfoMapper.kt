package ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb

import android.database.Cursor
import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import java.sql.SQLException

interface CursorToArtistInfoMapper {
    fun map(cursor: Cursor): ArtistInfo?
}

internal class CursorToArtistInfoMapperImpl : CursorToArtistInfoMapper {

    override fun map(cursor: Cursor): ArtistInfo? =
        try {
            with(cursor) {
                if (moveToNext()) {
                    ArtistInfo(
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