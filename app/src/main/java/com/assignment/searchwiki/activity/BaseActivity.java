package com.assignment.searchwiki.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.assignment.searchwiki.R;
import com.assignment.searchwiki.network.WikiAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class BaseActivity extends AppCompatActivity {

    public static final String SEARCHDATA = "searchdata";
    public static final String MYPREFENCE = "mypref";
    public static final String WIKI_URL = "wiki_url";
    public static final String WIKI_PAGE_TITLE = "wiki_page_title";
    public static final String RECENT_SEARCH_LIST = "recentsearchList";
    public static final String WIKI_BASE_URL_FOR_WEBVIEW = "https://en.wikipedia.org/?curid=";
    public WikiAPI wikiAPI;
    public ProgressDialog progress;

    public void createStackoverflowAPI() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        //Used for Http logging of requets and response.
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(wikiAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClient.build())
                .build();

        wikiAPI = retrofit.create(WikiAPI.class);
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showNetworkUnvailableMessage(View view) {
        Snackbar.make(view, getResources().getString(R.string.networkNotAvailablemessage), Snackbar.LENGTH_SHORT).show();
    }

    public void showEmptySearchErrorMessage(View view){
        Snackbar.make(view, getResources().getString(R.string.emptySearchMessage), Snackbar.LENGTH_SHORT).show();
    }

    public void showProgressDialog() {
        progress = new ProgressDialog(this);
        progress.setTitle(getResources().getString(R.string.loading_title));
        progress.setMessage(getResources().getString(R.string.loading_text));
        progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
        progress.show();
//
    }

    public void hideProgressDialog() {
        if (progress.isShowing()) {
            progress.dismiss();
        }
    }

    public void showBadURLErrorOnWebView(View view){
        Snackbar.make(view, getResources().getString(R.string.bad_url_error),Snackbar.LENGTH_SHORT).show();
    }
}
