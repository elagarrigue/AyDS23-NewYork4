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
import java.util.Locale

class OtherInfoWindow : AppCompatActivity() {
    private lateinit var artist: ArtistInfo
    private lateinit var artistName: String
    private lateinit var artistInfoView: TextView
    private lateinit var logoImageView: ImageView
    private lateinit var openUrlButtonView: View
    private lateinit var nyTimesApi: NYTimesAPI
    private lateinit var database : DataBase

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
        const val JSON_OBJECT_RESPONSE = "response"
        const val JSON_OBJECT_DOCS = "docs"
        const val JSON_OBJECT_ABSTRACT = "abstract"
        const val JSON_OBJECT_WEB_URL = "web_url"
        const val NY_TIMES_API_URL = "https://api.nytimes.com/svc/search/v2/"
        const val NY_TIMES_LOGO_URL = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU"
        const val DEFAULT_ARTIST_INFO_RESULT_TEXT = "No Results"
        const val BEGIN_HTML = "<html><div width=400><font face=\"arial\">"
        const val END_HTML = "</font></div></html>"
        const val ESCAPED_NEW_LINE_TEXT = "\\n"
        const val ESCAPED_NEW_LINE = "\n"
        const val HTML_NEW_LINE = "<br>"
        const val HTML_OPEN_BOLD = "<b>"
        const val HTML_CLOSE_BOLD = "</b>"
        const val LOCALLY_STORED_PREFIX = "[*]"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initProperties()

        createArtistName()
        createNYTimesApi()
        createDatabase()
        createArtistInfo()

        setArtistInfoOnUi()
    }

    private fun initProperties() {
        artistInfoView = findViewById(R.id.textPane2)
        logoImageView = findViewById(R.id.imageView)
        openUrlButtonView = findViewById(R.id.openUrlButton)
    }

    private fun createArtistName() {
        artistName = intent.getStringExtra(ARTIST_NAME_EXTRA)!!
    }

    private fun createNYTimesApi() {
        nyTimesApi = retrofit().create(NYTimesAPI::class.java)
    }

    private fun retrofit() : Retrofit = Retrofit.Builder()
        .baseUrl(NY_TIMES_API_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private fun createDatabase() {
        database = DataBase(this)
    }

    private fun createArtistInfo() {
        artist = ArtistInfo()
    }

    private fun setArtistInfoOnUi() {
        Thread {
            setArtistInfoLogic()
        }.start()
    }

    private fun setArtistInfoLogic() {
        setArtistInfo()
        setArtistInfoIntoView()
        setNYTimesImageIntoView()
        setOpenUrlButtonListener()
    }

    private fun setArtistInfo() {
        artist.info = database.getArtistInfo(artistName)
        artist.info = if (artistInfoExists(artist.info)) {
            markArtistAsLocallyStored(artist.info)
        } else {
            getArtistInfoFromService()
        }
    }

    private fun artistInfoExists(artistInfo: String?) = artistInfo != null

    private fun markArtistAsLocallyStored(artistInfo: String?):String {
        artist.locallyStored = true
        return "${LOCALLY_STORED_PREFIX}$artistInfo"
    }

    private fun getArtistInfoFromService():String? {
        return getArtistInfo(getApiResponse(nyTimesApi, artistName))
    }

    private fun getArtistInfo(nyTimesApiResponse: Response<String>?):String? {
        var formattedArtistInfo: String? = null
        if (nyTimesApiResponse != null) {
            val responseInJson = apiResponseToJsonObject(nyTimesApiResponse)
            val documentAbstractArtistInfo = getDocumentAbstract(responseInJson)
            formattedArtistInfo = formatAbstractArtistInfo(documentAbstractArtistInfo, artistName)
            artist.url = getDocumentUrl(responseInJson).asString
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

    private fun setArtistInfoIntoView() {
        runOnUiThread {
            artistInfoView.text = Html.fromHtml(artist.info)
        }
    }

    private fun setNYTimesImageIntoView() {
        runOnUiThread {
            Picasso.get().load(NY_TIMES_LOGO_URL).into(logoImageView)
        }
    }

    private fun setOpenUrlButtonListener() {
        runOnUiThread {
            openUrlButtonView.setOnClickListener {
                startActivity(getOpenUrlButtonIntent())
            }
        }
    }

    private fun getOpenUrlButtonIntent(): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(artist.url)
        return intent
    }

    private fun formatAbstractArtistInfo(
        documentAbstractArtistInfo: JsonElement?,
        artistName: String
    ): String {
        var formattedArtistInfo = DEFAULT_ARTIST_INFO_RESULT_TEXT
        if (documentAbstractArtistInfo != null) {
            formattedArtistInfo = documentAbstractArtistInfo.asString.replace(ESCAPED_NEW_LINE_TEXT, ESCAPED_NEW_LINE)
            formattedArtistInfo = textToHtml(formattedArtistInfo, artistName)
            database.saveArtist(artistName, formattedArtistInfo)
        }
        return formattedArtistInfo
    }

    private fun textToHtml(text: String, term: String?): String {
        val builder = StringBuilder()
        builder.append(BEGIN_HTML)
        builder.append(getBoldTextInHtml(text, term))
        builder.append(END_HTML)
        return builder.toString()
    }

    private fun getBoldTextInHtml(text: String, term: String?): String {
        return text
            .replace("'", " ")
            .replace(ESCAPED_NEW_LINE, HTML_NEW_LINE)
            .replace(
                "(?i)$term".toRegex(),
                HTML_OPEN_BOLD + term!!.uppercase(Locale.getDefault()) + HTML_CLOSE_BOLD
            )
    }
}