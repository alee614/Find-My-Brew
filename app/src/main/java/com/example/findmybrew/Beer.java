package com.example.findmybrew;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

public class Beer implements Parcelable {
    private String name;
    private int abv;
    private String brewedDate;
    private String imageUrl;
    private String description;
    private String foodPairing;

    public static Creator<Beer> getCREATOR() {
        return CREATOR;
    }

    private String brewsterTips;

    public boolean isFavorite() {
        return favorite;
    }

    private boolean favorite;

    protected Beer(Parcel in) {
        name = in.readString();
        abv = in.readInt();
        brewedDate = in.readString();
        imageUrl = in.readString();
        description = in.readString();
        foodPairing = in.readString();
        brewsterTips = in.readString();
//        favorite = in.readByte() != 0;
    }

    public static final Creator<Beer> CREATOR = new Creator<Beer>() {
        @Override
        public Beer createFromParcel(Parcel in) {
            return new Beer(in);
        }

        @Override
        public Beer[] newArray(int size) {
            return new Beer[size];
        }
    };

    public String getName() {
        return name;
    }

    public int getAbv() {
        return abv;
    }

    public String getBrewedDate() {
        return brewedDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getFoodPairing() {
        return foodPairing;
    }

    public String getBrewsterTips() {
        return brewsterTips;
    }


    public Beer(String name, int abv, String brewedDate, String imageUrl, String description, String foodPairing, String brewsterTips){
        this.name = name;
        this.abv = abv;
        this.brewedDate = brewedDate;
        this.imageUrl = imageUrl;
        this.description = description;
        this.foodPairing = foodPairing;
        this.brewsterTips = brewsterTips;
        favorite = false;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(abv);
        dest.writeString(brewedDate);
        dest.writeString(imageUrl);
        dest.writeString(description);
        dest.writeString(foodPairing);
        dest.writeString(brewsterTips);


    }
}
