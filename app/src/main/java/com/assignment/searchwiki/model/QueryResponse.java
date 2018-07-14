package com.assignment.searchwiki.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class QueryResponse implements Parcelable {

    @SerializedName("pages")
    private ArrayList<Page> pages;

    protected QueryResponse(Parcel in) {
        pages = in.createTypedArrayList(Page.CREATOR);
    }

    public static final Creator<QueryResponse> CREATOR = new Creator<QueryResponse>() {
        @Override
        public QueryResponse createFromParcel(Parcel in) {
            return new QueryResponse(in);
        }

        @Override
        public QueryResponse[] newArray(int size) {
            return new QueryResponse[size];
        }
    };

    public ArrayList<Page> getPages() {
        return pages;
    }

    public void setPages(ArrayList<Page> pages) {
        this.pages = pages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(pages);
    }
}
