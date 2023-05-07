package ayds.newyork.songinfo.moredetails.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ayds.newyork.songinfo.R
import ayds.newyork.songinfo.moredetails.data.DataBase
import ayds.newyork.songinfo.moredetails.data.NYTimesAPI
import ayds.newyork.songinfo.moredetails.domain.ArtistInfo
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
    private lateinit var artistName: String
    private lateinit var artistInfoView: TextView
    private lateinit var logoImageView: ImageView
    private lateinit var openUrlButtonView: View
    private lateinit var nyTimesApi: NYTimesAPI
    private var database = DataBase(this)

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

    private fun setArtistInfoOnUi() {
        Thread {
            setArtistInfoLogic()
        }.start()
    }

    private fun setArtistInfoLogic() {
        val artistInfo = getArtistInfo(artistName)
        setArtistInfoIntoView(artistInfo)
        setNYTimesImageIntoView()
        setOpenUrlButtonListener(artistInfo.url)
    }

    private fun getArtistInfo(artistName: String): ArtistInfo {
        var artistInfo = database.getArtistInfo(artistName)
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

    private fun setArtistInfoIntoView(artistInfo: ArtistInfo) {
        runOnUiThread {
            if(artistInfo.isLocallyStored){
                artistInfo.info = "$LOCALLY_STORED_PREFIX${artistInfo.info}"
            }
            artistInfoView.text = Html.fromHtml(artistInfo.info)
        }
    }

    private fun setNYTimesImageIntoView() {
        runOnUiThread {
            Picasso.get().load(NY_TIMES_LOGO_URL).into(logoImageView)
        }
    }

    private fun setOpenUrlButtonListener(url: String?) {
        runOnUiThread {
            openUrlButtonView.setOnClickListener {
                startActivity(getOpenUrlButtonIntent(url))
            }
        }
    }

    private fun getOpenUrlButtonIntent(url: String?): Intent {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        return intent
    }

    private fun getApiResponse(artistName: String): JsonObject? {
        var responseInJson: JsonObject? = null
        try {
            val callResponse = nyTimesApi.getArtistInfo(artistName).execute()
            responseInJson = apiResponseToJsonObject(callResponse)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return responseInJson
    }

    private fun getArtistInfoFromService(nyTimesApiResponse: JsonObject?, artistName: String):String? {
        return getArtistInfo(nyTimesApiResponse, artistName)
    }

    private fun getArtistUrlFromService(nyTimesApiResponse: JsonObject?):String? {
        return getArtistUrl(nyTimesApiResponse)
    }

    private fun getArtistInfo(nyTimesApiResponse: JsonObject?, artistName: String):String? {
        return if (nyTimesApiResponse != null) {
            val documentAbstractArtistInfo = getDocumentAbstract(nyTimesApiResponse)
            formatAbstractArtistInfo(documentAbstractArtistInfo, artistName)
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

    private fun formatAbstractArtistInfo(
        documentAbstractArtistInfo: JsonElement?,
        artistName: String
    ): String {
        var formattedArtistInfo = DEFAULT_ARTIST_INFO_RESULT_TEXT
        if (documentAbstractArtistInfo != null) {
            formattedArtistInfo = documentAbstractArtistInfo.asString.replace(
                ESCAPED_NEW_LINE_TEXT,
                ESCAPED_NEW_LINE
            )
            formattedArtistInfo = textToHtml(formattedArtistInfo, artistName)
            database.saveArtist(artistName, formattedArtistInfo)
        }
        return formattedArtistInfo
    }

    private fun textToHtml(text: String, term: String?): String {
        return StringBuilder().apply {
            append(BEGIN_HTML)
            append(getBoldTextInHtml(text, term))
            append(END_HTML)
        }.toString()
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
