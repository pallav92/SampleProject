package com.assignment.searchwiki.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.assignment.searchwiki.R;
import com.assignment.searchwiki.model.Page;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultItemViewHolder> {

    private Context context;
    private ArrayList<Page> searchResultList;

    public SearchResultAdapter(Context context, ArrayList<Page> searchResultList) {
        this.context = context;
        this.searchResultList = searchResultList;
    }

    @NonNull
    @Override
    public SearchResultItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_result_item, parent, false);

        return new SearchResultItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultItemViewHolder holder, int position) {
        Page searchResult = searchResultList.get(position);
        if (searchResult != null) {
            holder.tvTitle.setText(searchResult.getTitle());
            if (searchResult.getTerms() != null
                    && searchResult.getTerms().getDescriptionlist() != null
                    && searchResult.getTerms().getDescriptionlist().size() > 0)
                holder.tvDescription.setText(searchResult.getTerms().getDescriptionlist().get(0));
            if (searchResult.getThumbnail() != null) {
                Glide.with(context)
                        .load(searchResult.getThumbnail().getSource())
                        .into(holder.ivImage);
            }
        }
    }

    @Override
    public int getItemCount() {
        return searchResultList.size();
    }

    public class SearchResultItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription;
        private ImageView ivImage;

        public SearchResultItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvDescription = (TextView) itemView.findViewById(R.id.tv_description);
            ivImage = (ImageView) itemView.findViewById(R.id.iv_image);
        }
    }
}
