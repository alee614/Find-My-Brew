package com.example.findmybrew;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    private TextView results;
    private RecyclerView recyclerView;
    private ArrayList<Beer> beersInfo;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        recyclerView = findViewById(R.id.recyclerView);
        results = findViewById(R.id.textView_result);

        beersInfo = new ArrayList<>();

        Intent intent = getIntent();
        beersInfo = intent.getParcelableArrayListExtra("value");
        Log.d("beersInfo", String.valueOf(beersInfo.size()));

        String size = Integer.toString(beersInfo.size());

        results.setText(getString(R.string.returnResults, beersInfo.size()));
        BeerAdapter adapter = new BeerAdapter(beersInfo, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
}
