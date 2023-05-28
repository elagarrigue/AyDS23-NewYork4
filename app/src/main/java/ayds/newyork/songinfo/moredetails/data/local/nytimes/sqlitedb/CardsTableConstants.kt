package ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb

const val ARTISTS_TABLE_NAME = "artists"
const val COLUMN_ID = "id"
const val COLUMN_ARTIST = "artist"
const val COLUMN_SOURCE = "source"
const val COLUMN_SOURCE_LOGO = "source_logo"
const val COLUMN_INFO = "info"
const val SELECTION_FILTER_FOR_NAME = "$COLUMN_ARTIST = ?"
const val SELECTION_ORDER_BY = "$COLUMN_ARTIST DESC"
const val SQLITE_OPEN_HELPER_NAME = "dictionary.db"
const val SOURCE_VALUE = 1
const val CREATE_ARTISTS_TABLE_SQL_QUERY: String =
    "create table $ARTISTS_TABLE_NAME ($COLUMN_ID integer primary key autoincrement, " +
            "$COLUMN_ARTIST string, $COLUMN_INFO string, $COLUMN_SOURCE string, $COLUMN_SOURCE_LOGO string)"