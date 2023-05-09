package ayds.newyork.songinfo.moredetails.data.local.nytimes

import ayds.newyork.songinfo.moredetails.data.external.nytimes.NYTimesAPI
import ayds.newyork.songinfo.moredetails.data.local.nytimes.sqlitedb.DataBase
import ayds.newyork.songinfo.moredetails.domain.entities.ArtistInfo
import ayds.newyork.songinfo.moredetails.domain.repository.ArtistInfoRepository
import ayds.newyork.songinfo.moredetails.presentation.ArtistInfoHelper
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException

internal class ArtistInfoRepositoryImpl(
    private val artistLocalStorage: DataBase,
): ArtistInfoRepository {
    private lateinit var artistInfoHelper: ArtistInfoHelper// = MoreDetailsViewInjector.artistInfoHelper
    private var nyTimesService: NYTimesAPI = retrofit().create(
        NYTimesAPI::class.java)

    companion object {
        const val JSON_OBJECT_RESPONSE = "response"
        const val JSON_OBJECT_DOCS = "docs"
        const val JSON_OBJECT_ABSTRACT = "abstract"
        const val JSON_OBJECT_WEB_URL = "web_url"
        const val NY_TIMES_API_URL = "https://api.nytimes.com/svc/search/v2/"
    }

    private fun retrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(NY_TIMES_API_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    override fun getArtistInfo(artistName: String): ArtistInfo {
        var artistInfo = artistLocalStorage.getArtistInfo(artistName)
        when {
            artistInfo != null -> markArtistAsLocal(artistInfo)
            else -> {
                val apiResponse = getApiResponse(artistName)
                artistInfo = ArtistInfo(
                    getArtistUrlFromService(apiResponse),
                    getArtistInfoFromService(apiResponse, artistName)
                )
            }
        }
        return artistInfo
    }

    private fun markArtistAsLocal(artistInfo: ArtistInfo) {
        artistInfo.isLocallyStored = true
    }

    private fun getApiResponse(artistName: String): JsonObject? {
        var responseInJson: JsonObject? = null
        try {
            val callResponse = nyTimesService.getArtistInfo(artistName).execute()
            responseInJson = apiResponseToJsonObject(callResponse)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return responseInJson
    }

    private fun getArtistUrlFromService(nyTimesApiResponse: JsonObject?):String? {
        return getArtistUrl(nyTimesApiResponse)
    }

    private fun getArtistInfoFromService(nyTimesApiResponse: JsonObject?, artistName: String):String? {
        return getArtistInfo(nyTimesApiResponse, artistName)
    }

    private fun getArtistInfo(nyTimesApiResponse: JsonObject?, artistName: String):String? {
        return if (nyTimesApiResponse != null) {
            val documentAbstractArtistInfo = getDocumentAbstract(nyTimesApiResponse)
            val formattedArtistInfo = artistInfoHelper.formatAbstractArtistInfo(documentAbstractArtistInfo, artistName)
            artistLocalStorage.saveArtist(artistName, formattedArtistInfo)
            formattedArtistInfo
        } else {
            null
        }
    }

    private fun getArtistUrl(nyTimesApiResponse: JsonObject?):String? {
        return if (nyTimesApiResponse != null) {
            getDocumentUrl(nyTimesApiResponse).asString
        } else {
            null
        }
    }

    private fun apiResponseToJsonObject(apiCallResponse: Response<String>): JsonObject {
        val jsonObject = Gson().fromJson(apiCallResponse.body(), JsonObject::class.java)
        return jsonObject[JSON_OBJECT_RESPONSE].asJsonObject
    }

    private fun getDocumentAbstract(apiCallResponseInJson: JsonObject): JsonElement {
        return getDocument(apiCallResponseInJson)[JSON_OBJECT_ABSTRACT]
    }

    private fun getDocument(apiCallResponseInJson: JsonObject): JsonObject {
        val documents = apiCallResponseInJson[JSON_OBJECT_DOCS].asJsonArray
        return documents[0].asJsonObject
    }

    private fun getDocumentUrl(apiCallResponseInJson: JsonObject): JsonElement {
        return getDocument(apiCallResponseInJson)[JSON_OBJECT_WEB_URL]
    }
}