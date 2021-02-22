package com.example.findmybrew;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.ViewHolder> implements Filterable {

    private List<Beer> Beers;
    private List<Beer> favorites;
    private List<Beer> beerListFiltered;

    public BeerAdapter(ArrayList<Beer> Beers, Context context){
        this.Beers = Beers;
        this.favorites = new ArrayList<>();
        this.beerListFiltered = new ArrayList<>();

    }

    // inner class to specify the custom viewHolder
    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView_name;
        TextView textView_description;
        ImageView imageView_beer;
        ImageButton imageButton;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView_name = itemView.findViewById(R.id.textView_name);
            textView_description = itemView.findViewById(R.id.textView_description);
            imageView_beer = itemView.findViewById(R.id.imageView_beer);
            imageButton = itemView.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // when imageButton is clicked, add to the arrayList
            int selected = getAdapterPosition();
            Beer selectedB = Beers.get(selected);

            if (favorites.contains(selectedB)){
                favorites.remove(selectedB);
            }
            else{
                favorites.add(selectedB);
            }
            notifyDataSetChanged();
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
        if (favorites.contains(beer)){
            holder.imageButton.setPressed(true);
            Log.d("this is the button", Beers.get(position).getName());
        }
        else{
            holder.imageButton.setPressed(false);
        }

        holder.imageView_beer.setOnClickListener(v -> {
            Context context = v.getContext();
            Intent intent = new Intent(context, ItemInfoActivity.class);
            intent.putExtra("beerItem", beer);
            Log.d("beer image click", beer.getName());
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return Beers.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter(){
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if (charString.isEmpty()) {
                    beerListFiltered = Beers;
                } else {
                    List<Beer> filteredList = new ArrayList<>();
                    for (Beer row : Beers) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }
                    beerListFiltered = filteredList;
                }
                FilterResults results = new FilterResults();
                results.values = beerListFiltered;
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                Beers = (ArrayList<Beer>) results.values;

                notifyDataSetChanged();
            }
        };
    }

}
