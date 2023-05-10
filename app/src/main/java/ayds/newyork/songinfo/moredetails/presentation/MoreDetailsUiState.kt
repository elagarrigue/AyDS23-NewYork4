package ayds.newyork.songinfo.moredetails.presentation

data class MoreDetailsUiState(
    val artistDescription: String? = "",
    val artistUrl: String? = null,
    val logoImageUrl: String = NY_TIMES_LOGO_URL,
    val actionsEnabled: Boolean = false,
) {
    companion object {
        const val NY_TIMES_LOGO_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU"
    }
}