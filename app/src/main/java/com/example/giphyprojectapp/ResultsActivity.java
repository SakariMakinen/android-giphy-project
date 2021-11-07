package com.example.giphyprojectapp;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        //Save ourselves from nullpointer experience
        String[] gifUrls = {"https://media4.giphy.com/media/3o6Zt481isNVuQI1l6/200.gif?cid=9bc054f1izfx38d7hmhemr63rgacqi8m1nyw39mtce2ag9ve&rid=200.gif&ct=g"};
        Bundle myBundle = getIntent().getExtras();
        if (myBundle.getStringArray("urls") != null) {
            gifUrls = myBundle.getStringArray("urls");
        }
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(
            new ImageListAdapter(
                ResultsActivity.this,
                gifUrls
            )
        );



    }
    public class ImageListAdapter extends ArrayAdapter {
        private Context context;
        private LayoutInflater inflater;

        private String[] imageUrls;

        public ImageListAdapter(Context context, String[] imageUrls) {
            super(context, R.layout.listview_item_image, imageUrls);

            this.context = context;
            this.imageUrls = imageUrls;

            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView) {
                convertView = inflater.inflate(R.layout.listview_item_image, parent, false);
            }

            Glide
                    .with(context)
                    .load(imageUrls[position])
                    .into((ImageView) convertView);

            return convertView;
        }
    }




}