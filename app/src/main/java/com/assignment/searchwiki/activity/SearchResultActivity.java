package com.assignment.searchwiki.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.assignment.searchwiki.R;
import com.assignment.searchwiki.adapter.SearchResultAdapter;
import com.assignment.searchwiki.model.Page;
import com.assignment.searchwiki.util.ClickListener;
import com.assignment.searchwiki.util.RecyclerTouchListener;

import java.util.ArrayList;

public class SearchResultActivity extends BaseActivity {

    private SearchResultAdapter searchResultAdapter;
    private RecyclerView rvSearchResults;
    private ArrayList<Page> pageList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        initUI();
        if(getIntent()!=null &&
                getIntent().getParcelableArrayListExtra(SEARCHDATA)!=null &&
                !getIntent().getParcelableArrayListExtra(SEARCHDATA).isEmpty()){
            pageList = getIntent().getParcelableArrayListExtra(SEARCHDATA);
            searchResultAdapter = new SearchResultAdapter(this, pageList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            rvSearchResults.setLayoutManager(mLayoutManager);
            rvSearchResults.setItemAnimator(new DefaultItemAnimator());
            DividerItemDecoration itemDecorator = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
            itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.custom_divider));
            rvSearchResults.addItemDecoration(itemDecorator);
            rvSearchResults.setAdapter(searchResultAdapter);
        }
        setUpClickListnerForRecyclerViewItems();
    }

    private void setUpClickListnerForRecyclerViewItems() {
        rvSearchResults.addOnItemTouchListener(new RecyclerTouchListener
                (this, rvSearchResults, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                redirectToWebView(pageList.get(position));
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
    }

    private void redirectToWebView(Page page) {
        if(page!=null && page.getPageid()>0l) {
            Intent intent = new Intent(this,WebViewActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString(WIKI_PAGE_TITLE,page.getTitle());
            bundle.putString(WIKI_URL,page.getPageid()+"");
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void initUI() {
        rvSearchResults = (RecyclerView) findViewById(R.id.rv_search_result);
        pageList=  new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.searchPageTitle));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
