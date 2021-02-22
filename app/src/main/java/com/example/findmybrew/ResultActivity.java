package com.example.findmybrew;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.SearchView;
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
    private BeerAdapter adapter;

    private SearchView searchView;


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
        adapter = new BeerAdapter(beersInfo, this);

        Log.d("adapter", String.valueOf(adapter.getItemCount()));


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.searchView);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){

            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                results.setText(getString(R.string.returnResults, adapter.getItemCount()));
                Log.d("adapter size", String.valueOf(adapter.getItemCount()));
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                results.setText(getString(R.string.returnResults, adapter.getItemCount()));
                Log.d("adapter size", String.valueOf(adapter.getItemCount()));
                return false;
            }
        });
    }

    public void onBackPress(){
        if(!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }
}
