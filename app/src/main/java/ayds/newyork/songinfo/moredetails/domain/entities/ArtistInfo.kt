package ayds.newyork.songinfo.moredetails.domain.entities

data class ArtistInfo(
    var url: String? = "",
    var info: String? = "",
    var isLocallyStored: Boolean = false,
)