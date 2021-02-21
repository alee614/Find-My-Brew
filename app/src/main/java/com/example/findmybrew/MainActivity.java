package com.example.findmybrew;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // to string method
    // parcelable
    //
    // pass the obejct over to the
    // create the intent

    // set oncliick on the adapter
    // Object

    private ArrayList<Beer> beerArrayList;

    private Button home;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.imageView);
        Picasso.get().load("file:///android_asset/beer.png").into(image);

        home = findViewById(R.id.button_Home);
        home.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            startActivity(intent);
        });
    }
}