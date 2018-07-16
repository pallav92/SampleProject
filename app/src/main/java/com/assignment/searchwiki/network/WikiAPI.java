package com.assignment.searchwiki.network;

import com.assignment.searchwiki.model.SearchResponseContainer;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WikiAPI {

    String BASE_URL = "http://en.wikipedia.org";

    @GET("w/api.php?")
    Call<List<Object>> getSuggestions(@Query("action") String action,
                              @Query("search") String searchText,
                              @Query("limit") int limit,
                              @Query("namespace") int namespace,
                              @Query("format") String format);

    @GET("/w/api.php?")
    Call<SearchResponseContainer> getSearchResults(@Query("action") String action,
                                                   @Query("format") String format,
                                                   @Query("prop") String prop,
                                                   @Query("generator") String generator,
                                                   @Query("redirects") int redirects,
                                                   @Query("formatversion") int formatVersion,
                                                   @Query("piprop") String piprop,
                                                   @Query("pithumbsize") int pithumbsize,
                                                   @Query("pilimit") int pilimit,
                                                   @Query("wbptterms") String wbptterms,
                                                   @Query("gpssearch") String searchtext,
                                                   @Query("gpslimit") int gpslimit);


}
