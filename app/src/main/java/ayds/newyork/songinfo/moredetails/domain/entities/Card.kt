package ayds.newyork.songinfo.moredetails.domain.entities

import com.google.gson.JsonElement

data class Card(
    var description: JsonElement,
    var infoUrl: String? = null,
    var source: Source,
    var sourceLogoUrl: String = "",
    var isLocallyStored: Boolean = false,
)

enum class Source{
    LastFM,
    NYTimes,
    Wikipedia
}