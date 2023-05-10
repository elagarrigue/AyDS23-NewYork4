package ayds.newyork.songinfo.moredetails.domain.entities

data class Artist(
    var url: String? = null,
    var info: String? = "",
    var isLocallyStored: Boolean = false,
)