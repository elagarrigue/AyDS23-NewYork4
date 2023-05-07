package ayds.newyork.songinfo.moredetails.domain

data class ArtistInfo(
    var url: String? = null,
    var info: String? = null,
    var isLocallyStored: Boolean = false,
)