package com.example.findmybrew;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    private Button go;
    private TextInputEditText inputBefore;
    private TextInputEditText inputAfter;
    private TextInputEditText inputName;
    private Switch highPoint;

    private String search;
    private String date_before;
    private String date_after;
    private Boolean abv = false;

    private ArrayList<Beer> beers;

    private static final String api_url = "https://api.punkapi.com/v2/beers";
    AsyncHttpClient client = new AsyncHttpClient();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        go = findViewById(R.id.button_search);

        inputBefore = findViewById(R.id.textInput_before);
        inputAfter = findViewById(R.id.textInput_after);
        inputName = findViewById(R.id.textInput_name);
        highPoint = findViewById(R.id.switch1);
        beers = new ArrayList<>();

        highPoint.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked){
                abv = true;
                Log.d("clicked: ", String.valueOf(abv));
            }
            else{
                abv = false;
                Log.d("clicked", String.valueOf(abv));
            }

        });

        go.setOnClickListener(v -> {
            Log.d("clicked:", "this button has been clicked");
            // check if either or date exists;
            // if it exists then pass it thro to make sure its in correct format with method
            // check for both before and after separately
            // if they both exist then if checkBeer and true -> searchBeer(v)
            String before = Objects.requireNonNull(inputBefore.getText()).toString();
            String after = Objects.requireNonNull(inputAfter.getText()).toString();


            if ((!before.isEmpty()) && (!after.isEmpty())){
                if (checkDate(before) && checkDate(after)){
                    if (compareDate(after, before)){
                        searchBeer(v);
                    }
                    else{
                        wrongRange(v);
                    }
                }
            }
            if ((!before.isEmpty()) && (after.isEmpty())){
                if (checkDate(before)) {
                    searchBeer(v);
                }
                else{
                    wrongDate(v);
                }
            }
            if ((!after.isEmpty()) && before.isEmpty()) {
                if (checkDate(after)) {
                    searchBeer(v);
                }
                else{
                    wrongDate(v);
                }
            }
            if (after.isEmpty() && before.isEmpty()){
                Log.d("dates", "are empty");
                searchBeer(v);
            }

            //Log.d("abv/d: ", String.valueOf(abv));
        });


        // check that I am grabbing everything correctly
        // if its inputted then grab and add as a parameter
        // lowercase the name
        // check format and order for dates
        //
    }
    public void searchBeer(View view){
        Intent intent = new Intent(SearchActivity.this, ResultActivity.class);
        RequestParams params = new RequestParams();
        search =  Objects.requireNonNull(inputName.getText()).toString();
        date_before = Objects.requireNonNull(inputBefore.getText()).toString();
        date_after = Objects.requireNonNull(inputAfter.getText()).toString();


        // check if the inputName is not null, then grab and add to param as beer_name
        if (!search.isEmpty()){
            //Log.d("search string: ", search);
            params.put("beer_name", search);
        }
        // check if the abv is true, add 3.99 to abv_gt
        if (abv){
            params.put("abv_gt", 3.99);
        }
        // check if the input before is not null, set it to brewed_beofre
        if (!date_before.isEmpty()){
            params.put("brewed_before", date_before);
        }
        if (!date_after.isEmpty()){
            params.put("brewed_after", date_after);
        }

        client.get(api_url, params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try{
                    JSONArray list = new JSONArray(new String(responseBody));
                    for (int i = 0; i < list.length(); i++){
                        JSONObject json = list.getJSONObject(i);

                        String name = json.getString("name");
                        //Log.d("name", name);
                        int abv = json.getInt("abv");
                        //Log.d("abv", String.valueOf(abv));
                        String brewedDate = json.getString("first_brewed");
                        //Log.d("brewedDate", brewedDate);
                        String imageUrl = json.getString("image_url");
                        //Log.d("imageUrl", imageUrl);
                        String description = json.getString("description");
                        //Log.d("description", description);

                        JSONArray foodPairingsList = json.getJSONArray("food_pairing");
                        String foodPairings = "";
                        for (int j = 0; j < foodPairingsList.length(); j++) {
                            if (j == foodPairingsList.length() - 1){
                                foodPairings = foodPairings + foodPairingsList.get(j);
                            }
                            else{
                                foodPairings = foodPairings + foodPairingsList.get(j) + ", ";
                            }
                        }
                        //Log.d("food pairings", foodPairings);

                        String brewersTips = json.getString("brewers_tips");
                        //Log.d("brewer's tips", brewersTips);

                        Beer beerObject = new Beer(name, abv, brewedDate, imageUrl, description, foodPairings, brewersTips);
                        beers.add(beerObject);
                    }
                    intent.putParcelableArrayListExtra("value", beers);
                    startActivity(intent);
                }
                catch (JSONException e){
                    Log.e("error", String.valueOf(e));
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("error", "could not go onto the next activity");
            }
        });
    }

    public boolean checkDate(String date){
        Pattern p = Pattern.compile("(0[1-9]|1[0-2])/[12][09][0-9][0-9]");
        Matcher m = p.matcher(date);
        boolean b = m.matches();
        Log.d("matches", String.valueOf(b));
        return b;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean compareDate(String dateAfter, String dateBefore){
        // parse thro each into correct date format
        // compareTo

        // what is correct "date" format first
        // 09/2020
        int month_after = Integer.parseInt(dateAfter.substring(0, 2));
        Log.d("month", String.valueOf(month_after));
        int year_after = Integer.parseInt(dateAfter.substring(3));
        Log.d("year", String.valueOf(year_after));

        int month_before = Integer.parseInt(dateBefore.substring(0,2));
        int year_before = Integer.parseInt(dateBefore.substring(3));
        YearMonth after = YearMonth.of(year_after, month_after);
        YearMonth before = YearMonth.of(year_before, month_before);
        int output = after.compareTo(before);
        Log.d("output", String.valueOf(output));
        if (output < 0){
            return true;
        }
        else{
            return false;
        }
    }

    public void wrongRange(View view){
        Toast.makeText(this, "Your range is invalid.", Toast.LENGTH_SHORT).show();
    }

    public void wrongDate(View view){
        Toast.makeText(this, "Please fix your date", Toast.LENGTH_SHORT).show();

    }


}