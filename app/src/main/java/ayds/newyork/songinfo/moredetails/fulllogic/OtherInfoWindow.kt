package ayds.newyork.songinfo.moredetails.fulllogic

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.R
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.io.IOException
import java.util.*

class OtherInfoWindow(private var database : DataBase? = null) : AppCompatActivity() {
    private var artistInfoView: TextView? = null

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)
        artistInfoView = findViewById(R.id.textPane2)
        open(intent.getStringExtra(ARTIST_NAME_EXTRA)!!)
    }

    private fun open(artistName: String) {
        database = DataBase(this)
        getArtistInfo(artistName)
    }

    private fun getArtistInfo(artistName: String) {
        val nyTimesApi = createNYTimesApi()
        Log.e("TAG", "$ARTIST_NAME_EXTRA $artistName")
        val thread = createThread(nyTimesApi, artistName)
        thread.start()
    }

    private fun createNYTimesApi(): NYTimesAPI {
        return retrofit().create(NYTimesAPI::class.java)
    }

    private fun retrofit() : Retrofit = Retrofit.Builder()
        .baseUrl("https://api.nytimes.com/svc/search/v2/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private fun createThread(
        nyTimesApi: NYTimesAPI,
        artistName: String
    ): Thread {
        return Thread {
            var artistInfo = database!!.getArtistInfo(artistName)
            artistInfo = if (artistInfoExists(artistInfo)) {
                markArtistAsLocallyStored(artistInfo)
            } else {
                getArtistInfoFromService(nyTimesApi, artistName)
            }
            setArtistInfoIntoView(artistInfo)
            setNYTimesImageIntoView()
        }
    }

    private fun artistInfoExists(artistInfo: String?) = artistInfo != null

    private fun markArtistAsLocallyStored(artistInfo: String?) = "[*]$artistInfo"

    private fun getArtistInfoFromService(nyTimesApi: NYTimesAPI, artistName: String): String? {
        val nyTimesApiResponse = getApiResponse(nyTimesApi, artistName)
        var formattedArtistInfo: String? = null
        if (nyTimesApiResponse != null) {
            Log.e("TAG", "JSON " + nyTimesApiResponse.body())
            val responseInJson = apiResponseToJsonObject(nyTimesApiResponse)
            val documentAbstractArtistInfo = getDocumentAbstract(responseInJson)
            formattedArtistInfo = formatAbstractArtistInfo(documentAbstractArtistInfo, artistName)
            val documentUrl = getDocumentUrl(responseInJson)
            setOpenUrlButtonListener(documentUrl)
        }
        return formattedArtistInfo
    }

    private fun getApiResponse(nyTimesApi: NYTimesAPI, artistName: String): Response<String>? {
        var callResponse: Response<String>? = null
        try {
            callResponse = nyTimesApi.getArtistInfo(artistName).execute()
        } catch (e1: IOException) {
            Log.e("TAG", "Error $e1")
            e1.printStackTrace()
        }
        return callResponse
    }

    private fun apiResponseToJsonObject(apiCallResponse: Response<String>): JsonObject {
        val gson = Gson()
        val jsonObject = gson.fromJson(apiCallResponse.body(), JsonObject::class.java)
        return jsonObject["response"].asJsonObject
    }

    private fun setOpenUrlButtonListener(jsonUrl: JsonElement) {
        findViewById<View>(R.id.openUrlButton).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(jsonUrl.asString)
            startActivity(intent)
        }
    }

    private fun setNYTimesImageIntoView() {
        val imageUrl =
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU"
        val view = findViewById<View>(R.id.imageView)
        Log.e("TAG", "Get Image from $imageUrl")
        runOnUiThread { Picasso.get().load(imageUrl).into(view as ImageView) }
    }

    private fun setArtistInfoIntoView(artistInfo: String?) {
        runOnUiThread { artistInfoView?.text = Html.fromHtml(artistInfo) }
    }

    private fun getDocumentAbstract(apiCallResponseInJson: JsonObject): JsonElement {
        return apiCallResponseInJson["docs"].asJsonArray[0].asJsonObject["abstract"]
    }

    private fun formatAbstractArtistInfo(
        documentAbstractArtistInfo: JsonElement?,
        artistName: String
    ): String {
        var formattedArtistInfo = "No Results"
        if (documentAbstractArtistInfo != null) {
            formattedArtistInfo = documentAbstractArtistInfo.asString.replace("\\n", "\n")
            formattedArtistInfo = textToHtml(formattedArtistInfo, artistName)
            database!!.saveArtist(artistName, formattedArtistInfo)
        }
        return formattedArtistInfo
    }

    private fun getDocumentUrl(apiCallResponseInJson: JsonObject): JsonElement {
        return apiCallResponseInJson["docs"].asJsonArray[0].asJsonObject["web_url"]
    }

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append("<html><div width=400> <font face=\"arial\">")
        val textWithBoldInHtml = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)$term".toRegex(),
                "<b>" + term!!.uppercase(Locale.getDefault()) + "</b>"
            )
        builder.append(textWithBoldInHtml)
        builder.append("</font></div></html>")
        return builder.toString()
    }
}