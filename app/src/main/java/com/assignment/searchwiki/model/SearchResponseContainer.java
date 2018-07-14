package com.assignment.searchwiki.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class SearchResponseContainer implements Parcelable {

    @SerializedName("query")
    private QueryResponse queryResponse;

    protected SearchResponseContainer(Parcel in) {
        queryResponse = in.readParcelable(QueryResponse.class.getClassLoader());
    }

    public static final Creator<SearchResponseContainer> CREATOR = new Creator<SearchResponseContainer>() {
        @Override
        public SearchResponseContainer createFromParcel(Parcel in) {
            return new SearchResponseContainer(in);
        }

        @Override
        public SearchResponseContainer[] newArray(int size) {
            return new SearchResponseContainer[size];
        }
    };

    public QueryResponse getQueryResponse() {
        return queryResponse;
    }

    public void setQueryResponse(QueryResponse queryResponse) {
        this.queryResponse = queryResponse;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(queryResponse, i);
    }
}
