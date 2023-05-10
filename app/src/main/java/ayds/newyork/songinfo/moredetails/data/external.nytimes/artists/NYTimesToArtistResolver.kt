package ayds.newyork.songinfo.moredetails.data.external.nytimes.artists

import ayds.newyork.songinfo.moredetails.domain.entities.Artist.NYTimesArtist
import ayds.newyork.songinfo.moredetails.presentation.ArtistInfoHelper
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

interface NYTimesToArtistResolver {
    fun getArtistFromExternalData(serviceData: String?, artistName: String): NYTimesArtist?
}

internal class JsonToArtistResolver(
    private val artistHelper: ArtistInfoHelper
) : NYTimesToArtistResolver {
    companion object {
        const val JSON_OBJECT_DOCS = "docs"
        const val JSON_OBJECT_WEB_URL = "web_url"
        const val JSON_OBJECT_RESPONSE = "response"
        const val JSON_OBJECT_ABSTRACT = "abstract"
    }

    override fun getArtistFromExternalData(serviceData: String?, artistName: String): NYTimesArtist? =
        try {
            val responseInJson = apiResponseToJsonObject(serviceData)
            val documentAbstractArtist = getDocumentAbstract(responseInJson)
            NYTimesArtist(
                getArtistUrl(responseInJson),
                artistHelper.formatAbstractArtist(documentAbstractArtist, artistName)
            )
        } catch (e: Exception){
            null
        }

    private fun apiResponseToJsonObject(serviceData: String?): JsonObject {
        val jsonObject = Gson().fromJson(serviceData, JsonObject::class.java)
        return jsonObject[JSON_OBJECT_RESPONSE].asJsonObject
    }

    private fun getDocumentAbstract(responseInJson: JsonObject): JsonElement {
        return getDocument(responseInJson)[JSON_OBJECT_ABSTRACT]
    }

    private fun getDocument(responseInJson: JsonObject): JsonObject {
        val documents = responseInJson[JSON_OBJECT_DOCS].asJsonArray
        return documents[0].asJsonObject
    }

    private fun getArtistUrl(responseInJson: JsonObject?):String? {
        return if (responseInJson != null) {
            getDocumentUrl(responseInJson).asString
        } else {
            null
        }
    }

    private fun getDocumentUrl(responseInJson: JsonObject): JsonElement {
        return getDocument(responseInJson)[JSON_OBJECT_WEB_URL]
    }
}