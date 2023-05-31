package ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb

const val ARTISTS_TABLE_NAME = "artists"
const val COLUMN_ID = "id"
const val COLUMN_ARTIST = "artist"
const val COLUMN_SOURCE = "source"
const val COLUMN_INFO = "info"
const val COLUMN_URL = "url"
const val COLUMN_LOGO = "logo"
const val SELECTION_FILTER_FOR_NAME = "$COLUMN_ARTIST = ?"
const val SELECTION_ORDER_BY = "$COLUMN_ARTIST DESC"
const val SQLITE_OPEN_HELPER_NAME = "dictionary.db"
const val CREATE_ARTISTS_TABLE_SQL_QUERY: String =
    "create table " +
    "$ARTISTS_TABLE_NAME " +
    "(" +
        "$COLUMN_ID integer primary key autoincrement, " +
        "$COLUMN_ARTIST string, " +
        "$COLUMN_INFO string, " +
        "$COLUMN_SOURCE string, " +
        "$COLUMN_URL string, " +
        "$COLUMN_LOGO string" +
    ")"