package ayds.newyork.songinfo.moredetails.presentation.presenter

import ayds.newyork.songinfo.moredetails.domain.entities.Source

data class MoreDetailsUiState(
    val cardDescription: String? = "",
    val cardUrl: String? = null,
    val source: Source? = null,
    val logoImageUrl: String? = "",
    val actionsEnabled: Boolean = false,
)