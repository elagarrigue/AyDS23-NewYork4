package ayds.newyork.songinfo.moredetails.fulllogic;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import ayds.newyork.songinfo.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import java.io.IOException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class OtherInfoWindow extends AppCompatActivity {
  public final static String ARTIST_NAME_EXTRA = "artistName";
  private TextView ArtistInfoView;
  private DataBase dataBase;
  public OtherInfoWindow (){
    dataBase = null;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_other_info);

    ArtistInfoView = findViewById(R.id.textPane2);

    open(getIntent().getStringExtra("artistName"));
  }

  private Retrofit getRetrofit(){
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/svc/search/v2/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();
    return retrofit;
  }

  private NYTimesAPI createNYTimesApi(){
    Retrofit retrofit = getRetrofit();
    NYTimesAPI nyTimesApi = retrofit.create(NYTimesAPI.class);
    return nyTimesApi;
  }

  private boolean artistInfoExists(String artistInfo){
    boolean exists = false;
    if (artistInfo != null) {
      exists = true;
    }
    return exists;
  }

  private String markArtistAsLocallyStored(String artistInfo){
    artistInfo = "[*]" + artistInfo;
    return artistInfo;
  }

  private Response<String> getApiResponse(NYTimesAPI nyTimesApi, String artistName){
    Response<String> callResponse = null;
    try {
      callResponse = nyTimesApi.getArtistInfo(artistName).execute();
    } catch (IOException e1) {
      Log.e("TAG", "Error " + e1);
      e1.printStackTrace();
    }
    return callResponse;
  }

  private JsonObject apiResponseToJsonObject(Response<String> apiCallResponse){
    Gson gson = new Gson();
    JsonObject jsonObject = gson.fromJson(apiCallResponse.body(), JsonObject.class);
    JsonObject jsonObjectResponse = jsonObject.get("response").getAsJsonObject();
    return jsonObjectResponse;
  }

  private void setOpenUrlButtonListener(JsonElement jsonUrl){
    final String urlInFinalString = jsonUrl.getAsString();
    findViewById(R.id.openUrlButton).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(urlInFinalString));
        startActivity(intent);
      }
    });
  }

  private void setNYTimesImageIntoView(){
    String imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRVioI832nuYIXqzySD8cOXRZEcdlAj3KfxA62UEC4FhrHVe0f7oZXp3_mSFG7nIcUKhg&usqp=CAU";
    android.view.View view = findViewById(R.id.imageView);

    Log.e("TAG","Get Image from " + imageUrl);

    runOnUiThread( () -> Picasso.get().load(imageUrl).into((ImageView) view) );
  }

  private void setArtistInfoIntoView(String artistInfo){
    final String finalArtistInfo = artistInfo;
    runOnUiThread( () -> ArtistInfoView.setText(Html.fromHtml(finalArtistInfo)) );
  }

  private JsonElement getDocumentAbstract(JsonObject apiCallResponseInJson){
    JsonElement documentAbstract = apiCallResponseInJson.get("docs").getAsJsonArray().get(0).getAsJsonObject().get("abstract");
    return documentAbstract;
  }

  private JsonElement getDocumentUrl(JsonObject apiCallResponseInJson){
    JsonElement url = apiCallResponseInJson.get("docs").getAsJsonArray().get(0).getAsJsonObject().get("web_url");
    return url;
  }

  private String formatAbstractArtistInfo(JsonElement documentAbstractArtistInfo, String artistName){
    String formattedArtistInfo;

    if (documentAbstractArtistInfo == null){
      formattedArtistInfo = "No Results";
    }
    else {
      formattedArtistInfo = documentAbstractArtistInfo.getAsString().replace("\\n", "\n");
      formattedArtistInfo = textToHtml(formattedArtistInfo, artistName);
      DataBase.saveArtist(dataBase, artistName, formattedArtistInfo);
    }
    return formattedArtistInfo;
  }

  private String getArtistInfoFromService(NYTimesAPI nyTimesApi, String artistName){
    Response<String> nyTimesApiResponse = getApiResponse(nyTimesApi, artistName);

    String formattedArtistInfo = null;

    if (nyTimesApiResponse != null){ // si se obtuvo respuesta
      Log.e("TAG","JSON " + nyTimesApiResponse.body());
      JsonObject responseInJson = apiResponseToJsonObject(nyTimesApiResponse);

      JsonElement documentAbstractArtistInfo = getDocumentAbstract(responseInJson);
      formattedArtistInfo = formatAbstractArtistInfo(documentAbstractArtistInfo, artistName);

      JsonElement documentUrl = getDocumentUrl(responseInJson);
      setOpenUrlButtonListener(documentUrl);
    }

    return formattedArtistInfo;
  }

  private Thread createThread(NYTimesAPI nyTimesApi, String artistName){
    Thread thread = new Thread(new Runnable() {
      @Override
      public void run() {
        String artistInfo = DataBase.getInfo(dataBase, artistName);

        if (artistInfoExists(artistInfo)) {
          artistInfo = markArtistAsLocallyStored(artistInfo);
        }
        else {
          artistInfo = getArtistInfoFromService(nyTimesApi, artistName);
        }

        setArtistInfoIntoView(artistInfo);
        setNYTimesImageIntoView();
      }
    });
    return thread;
  }

  public void getArtistInfo(String artistName) {
    NYTimesAPI nyTimesApi = createNYTimesApi();

    Log.e("TAG","artistName " + artistName);

    Thread thread = createThread(nyTimesApi, artistName);
    thread.start();
  }

  private void open(String artist) {
    dataBase = new DataBase(this);
    getArtistInfo(artist);
  }

  public static String textToHtml(String text, String term) {
    StringBuilder builder = new StringBuilder();

    builder.append("<html><div width=400> <font face=\"arial\">");

    String textWithBoldInHtml = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replaceAll("(?i)" + term, "<b>" + term.toUpperCase() + "</b>");

    builder.append(textWithBoldInHtml);
    builder.append("</font></div></html>");
    return builder.toString();
  }
}
