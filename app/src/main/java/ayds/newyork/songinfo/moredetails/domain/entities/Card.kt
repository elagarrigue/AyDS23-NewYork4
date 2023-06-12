package ayds.newyork.songinfo.moredetails.domain.entities

data class Card(
    var description: String,
    var infoUrl: String,
    var source: Source,
    var sourceLogoUrl: String,
    var isLocallyStored: Boolean = false,
)

enum class Source{
    LastFM,
    NYTimes,
    Wikipedia
}