package com.assignment.searchwiki.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assignment.searchwiki.R;

import java.util.ArrayList;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.RecentSearchViewHolder> {

    private Context context;
    private ArrayList<String> recentList;

    public RecentSearchAdapter(Context context, ArrayList<String> recentList) {
        this.context = context;
        this.recentList = recentList;
    }

    @NonNull
    @Override
    public RecentSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recent_search_itemview, parent, false);

        return new RecentSearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecentSearchViewHolder holder, int position) {
        String recentString = recentList.get(position);
        if (holder != null && holder.tvSearchText != null) {
            holder.tvSearchText.setText(recentString);
        }
    }

    @Override
    public int getItemCount() {
        return recentList.size();
    }

    public class RecentSearchViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSearchText;

        public RecentSearchViewHolder(View itemView) {
            super(itemView);
            tvSearchText = (TextView) itemView.findViewById(R.id.tvSearchtext);
        }
    }
}
