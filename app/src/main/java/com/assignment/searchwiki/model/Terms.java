package com.assignment.searchwiki.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


public class Terms implements Parcelable {

    @SerializedName("description")
    private ArrayList<String> descriptionlist;

    protected Terms(Parcel in) {
        descriptionlist = in.createStringArrayList();
    }

    public static final Creator<Terms> CREATOR = new Creator<Terms>() {
        @Override
        public Terms createFromParcel(Parcel in) {
            return new Terms(in);
        }

        @Override
        public Terms[] newArray(int size) {
            return new Terms[size];
        }
    };

    public ArrayList<String> getDescriptionlist() {
        return descriptionlist;
    }

    public void setDescriptionlist(ArrayList<String> descriptionlist) {
        this.descriptionlist = descriptionlist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(descriptionlist);
    }
}
