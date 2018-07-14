package com.assignment.searchwiki.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class WikiThumbnail implements Parcelable {

    @SerializedName("source")
    private String source;

    @SerializedName("width")
    private int width;

    @SerializedName("height")
    private int height;

    protected WikiThumbnail(Parcel in) {
        source = in.readString();
        width = in.readInt();
        height = in.readInt();
    }

    public static final Creator<WikiThumbnail> CREATOR = new Creator<WikiThumbnail>() {
        @Override
        public WikiThumbnail createFromParcel(Parcel in) {
            return new WikiThumbnail(in);
        }

        @Override
        public WikiThumbnail[] newArray(int size) {
            return new WikiThumbnail[size];
        }
    };

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(source);
        parcel.writeInt(width);
        parcel.writeInt(height);
    }
}
