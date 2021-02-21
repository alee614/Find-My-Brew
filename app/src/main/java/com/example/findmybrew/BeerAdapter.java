package com.example.findmybrew;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> {

    private ArrayList<Beer> Beers;
    public BeerAdapter(ArrayList<Beer> Beers, Context context){
        this.Beers = Beers;
    }

    // inner class to specify the custom viewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView_name;
        TextView textView_description;
        ImageView imageView_beer;
        ToggleButton toggleButton;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_description = itemView.findViewById(R.id.textView_description);
            imageView_beer = itemView.findViewById(R.id.imageView_beer);


        }

        @Override
        public void onClick(View v) {

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflate the custom layout
        View beerView = inflater.inflate(R.layout.item_beer, parent, false);
        // return the new Viewholder
        ViewHolder viewHolder = new ViewHolder(beerView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull BeerAdapter.ViewHolder holder, int position) {
        Beer beer = Beers.get(position);
        holder.textView_name.setText(beer.getName());
        holder.textView_description.setText(beer.getDescription());
        Picasso.get().load(beer.getImageUrl()).into(holder.imageView_beer);
        // set up intent


    }

    @Override
    public int getItemCount() {
        return Beers.size();
    }
}
