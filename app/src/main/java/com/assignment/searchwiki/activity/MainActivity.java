package com.assignment.searchwiki.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.assignment.searchwiki.R;
import com.assignment.searchwiki.adapter.RecentSearchAdapter;
import com.assignment.searchwiki.model.SearchResponseContainer;
import com.assignment.searchwiki.util.ClickListener;
import com.assignment.searchwiki.util.RecyclerTouchListener;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView rvRecentSearches;
    private AutoCompleteTextView actSearchBox;
    private LinearLayout llRecents;
    private Button btnSearch;
    private RelativeLayout rlMain;
    private SharedPreferences sharedpreferences;
    private ArrayList<String> autoSuggestList;
    private ArrayAdapter<String> autosuggestAdapter;
    private RecentSearchAdapter recentSearchAdapter;
    private ArrayList<String> recentSearchList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();
        createStackoverflowAPI();
        setTextChangeListnerOnSearchBox();
        setClickListnerForSearchButton();
        sharedpreferences = getSharedPreferences(MYPREFENCE,
                Context.MODE_PRIVATE);
        showRecentSearchesFromPreferences();
        setClickListnerOnRecentSearchedItems();
    }


    private void showRecentSearchesFromPreferences() {
        String recentString = sharedpreferences.getString(RECENT_SEARCH_LIST, "");
        recentSearchList = new ArrayList<>(Arrays.asList(recentString.split(",")));
        LinkedHashSet hs = new LinkedHashSet();
        hs.addAll(recentSearchList);
        recentSearchList.clear();
        recentSearchList.addAll(hs);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this,3);
        rvRecentSearches.setLayoutManager(mLayoutManager);
        rvRecentSearches.setItemAnimator(new DefaultItemAnimator());
        recentSearchAdapter = new RecentSearchAdapter(this, recentSearchList);
        rvRecentSearches.setAdapter(recentSearchAdapter);
        if (recentSearchList != null && recentSearchList.size() > 0
                && !recentSearchList.get(0).equalsIgnoreCase("")) {
            llRecents.setVisibility(View.VISIBLE);
            recentSearchAdapter.notifyDataSetChanged();
        } else {
            llRecents.setVisibility(View.GONE);
        }
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.home_page_title));
        rvRecentSearches = (RecyclerView) findViewById(R.id.rv_recent_searches);
        actSearchBox = (AutoCompleteTextView) findViewById(R.id.act_search_box);
        btnSearch = (Button) findViewById(R.id.btn_search);
        rlMain = (RelativeLayout) findViewById(R.id.rl_main);
        llRecents = (LinearLayout) findViewById(R.id.ll_recents);
        autoSuggestList = new ArrayList<>();
        autosuggestAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, autoSuggestList);
        actSearchBox.setAdapter(autosuggestAdapter);


    }

    private void setClickListnerOnRecentSearchedItems() {
        rvRecentSearches.addOnItemTouchListener(new RecyclerTouchListener
                (this, rvRecentSearches, new ClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        triggerWikiSearchbasedOnKeyword(recentSearchList.get(position));
                    }

                    @Override
                    public void onLongClick(View view, int position) {

                    }
                }));
    }

    private void setTextChangeListnerOnSearchBox() {
        actSearchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    wikiAPI.getSuggestions("opensearch",
                            charSequence.toString(),
                            8,
                            0,
                            "json").enqueue(autoSuggestCallback);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setClickListnerForSearchButton() {
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                triggerWikiSearchbasedOnKeyword(actSearchBox.getText().toString());
            }
        });
    }

    private void triggerWikiSearchbasedOnKeyword(String keyword) {
        if (isNetworkAvailable()) {
            if(!TextUtils.isEmpty(keyword)) {
                showProgressDialog();
                wikiAPI.getSearchResults("query",
                        "json",
                        "pageimages|pageterms",
                        "prefixsearch",
                        1,
                        2,
                        "thumbnail",
                        50,
                        10,
                        "description",
                        keyword.trim(),
                        10).enqueue(searchCallback);
            }else{
                showEmptySearchErrorMessage(rlMain);
            }
        } else {
            showNetworkUnvailableMessage(rlMain);
        }
    }

    Callback<JSONArray> autoSuggestCallback = new Callback<JSONArray>() {
        @Override
        public void onResponse(Call<JSONArray> call, Response<JSONArray> response) {
            Log.d(TAG, response.raw().toString());
            if (response.isSuccessful()) {
                JSONArray jArray = null;
                try {
                    jArray = new JSONArray(response);
                    if (jArray != null) {
                        for (int i = 0; i < jArray.getJSONArray(1).length(); i++) {
                            String SuggestKey = jArray.getJSONArray(1).getString(i);
                            autoSuggestList.add(SuggestKey);
                        }
                    }
                    autosuggestAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {
                Log.d("AutoSuggestCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<JSONArray> call, Throwable t) {
            t.printStackTrace();
        }
    };

    Callback<SearchResponseContainer> searchCallback = new Callback<SearchResponseContainer>() {
        @Override
        public void onResponse(Call<SearchResponseContainer> call, Response<SearchResponseContainer> response) {
            if (response.isSuccessful()) {
                hideProgressDialog();
                SearchResponseContainer searchResponseContainer = response.body();
                String recentsearches = sharedpreferences.getString(RECENT_SEARCH_LIST, "");
                if(!TextUtils.isEmpty(actSearchBox.getText())) {
                    if (recentsearches.length() == 0) {
                        recentsearches += actSearchBox.getText();
                    } else {
                        recentsearches = actSearchBox.getText() + "," + recentsearches;
                    }
                }
                if (searchResponseContainer != null && searchResponseContainer.getQueryResponse() != null) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putString(RECENT_SEARCH_LIST, recentsearches);
                    editor.commit();
                    actSearchBox.setText("");
                    showRecentSearchesFromPreferences();
                    Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    intent.putExtra(SEARCHDATA, searchResponseContainer.getQueryResponse().getPages());
                    startActivity(intent);
                } else {
                    Snackbar.make(rlMain, "No results to show", Snackbar.LENGTH_SHORT).show();
                }

            } else {
                Log.d("searchCallback", "Code: " + response.code() + " Message: " + response.message());
            }
        }

        @Override
        public void onFailure(Call<SearchResponseContainer> call, Throwable t) {
            t.printStackTrace();
        }
    };

}
