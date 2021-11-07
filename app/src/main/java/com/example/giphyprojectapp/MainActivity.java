package com.example.giphyprojectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    Bundle myBundle = new Bundle();
    String searchTerm = "Cat";
    String searchAmount = "1";
    String searchOffset = "0";



    public void getData(View view) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //Search parameters and apiKey
        TextView textSearch = findViewById(R.id.TextSearch);
        searchTerm = textSearch.getText().toString();
        TextView textAmount = findViewById(R.id.TextAmount);
        searchAmount = textAmount.getText().toString();
        TextView textOffset = findViewById(R.id.TextOffset);
        searchOffset = textOffset.getText().toString();

        //Print error message instead of title if something goes wrong
        TextView titleText = findViewById(R.id.textView);


        final String apiKey = "k6qyWOwuxriDplZ7dSVFX5ldXgu3d1bi";
        //final String limit = "1";
        String url = "https://api.giphy.com/v1/gifs/search?api_key="
                + apiKey +
                "&limit=" + searchAmount +
                "&offset=" + searchOffset +
                "&q=" + searchTerm;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url,
                null,
                response -> {
                    JSONObject jsonObject = response;
                    JSONObject jsonTemp;
                    JSONObject jsonTemp2;
                    JSONArray jsonArray = new JSONArray();
                    try {
                        jsonArray = jsonObject.getJSONArray("data");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    String[] arrURL = new String[jsonArray.length()];
                    try {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonTemp = jsonArray.getJSONObject(i);
                            jsonTemp2 = jsonTemp.getJSONObject("images");
                            jsonTemp = jsonTemp2.getJSONObject("fixed_height");
                            arrURL[i] = jsonTemp.getString("url");
                            Log.e(arrURL[i], "test");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    myBundle.putStringArray("urls", arrURL);

                },
                error -> titleText.setText(R.string.errorMSG));

        queue.add(jsonObjectRequest);
    }
    //Change view with another button
    public void changeView(View view) {
        Intent changeViewIntent = new Intent(this, ResultsActivity.class);
        changeViewIntent.putExtras(myBundle);
        startActivity(changeViewIntent);
    }
}