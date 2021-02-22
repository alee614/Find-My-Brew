package com.example.findmybrew;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemInfoActivity extends AppCompatActivity {

    TextView textView_name, textView_abv, textView_brewed, textView_description, textView_foodPairings, textView_brewster;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_info);
         // this page renders all the info sent thro by clicking on the specific image
        textView_name = findViewById(R.id.textView_itemName);
        textView_abv = findViewById(R.id.textView_abv);
        textView_brewed = findViewById(R.id.textView_brewed);
        textView_description = findViewById(R.id.textView_itemDescription);
        textView_foodPairings = findViewById(R.id.textView_foodPairings);
        textView_brewster = findViewById(R.id.textView_tips);
        imageView = findViewById(R.id.imageView_item);

        Intent intent = getIntent();
        Beer beer = intent.getParcelableExtra("beerItem");

        textView_name.setText(beer.getName());
        textView_abv.setText(getString(R.string.abv, beer.getAbv()));
        textView_brewed.setText(getString(R.string.firstBrewed, beer.getBrewedDate()));
        textView_description.setText(beer.getDescription());
        textView_foodPairings.setText(beer.getFoodPairing());
        textView_brewster.setText(beer.getBrewsterTips());

        Picasso.get().load(beer.getImageUrl()).into(imageView);


    }
}