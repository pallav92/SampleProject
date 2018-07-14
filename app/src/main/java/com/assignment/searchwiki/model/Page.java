package com.assignment.searchwiki.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Page implements Parcelable {

    @SerializedName("pageid")
    private long pageid;

    @SerializedName("ns")
    private int ns;

    @SerializedName("title")
    private String title;

    @SerializedName("index")
    private String index;

    @SerializedName("thumbnail")
    private WikiThumbnail thumbnail;

    @SerializedName("terms")
    private Terms terms;

    protected Page(Parcel in) {
        pageid = in.readLong();
        ns = in.readInt();
        title = in.readString();
        index = in.readString();
        thumbnail = in.readParcelable(WikiThumbnail.class.getClassLoader());
        terms = in.readParcelable(Terms.class.getClassLoader());
    }

    public static final Creator<Page> CREATOR = new Creator<Page>() {
        @Override
        public Page createFromParcel(Parcel in) {
            return new Page(in);
        }

        @Override
        public Page[] newArray(int size) {
            return new Page[size];
        }
    };

    public long getPageid() {
        return pageid;
    }

    public void setPageid(long pageid) {
        this.pageid = pageid;
    }

    public int getNs() {
        return ns;
    }

    public void setNs(int ns) {
        this.ns = ns;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public WikiThumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(WikiThumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Terms getTerms() {
        return terms;
    }

    public void setTerms(Terms terms) {
        this.terms = terms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(pageid);
        parcel.writeInt(ns);
        parcel.writeString(title);
        parcel.writeString(index);
        parcel.writeParcelable(thumbnail, i);
        parcel.writeParcelable(terms, i);
    }
}
