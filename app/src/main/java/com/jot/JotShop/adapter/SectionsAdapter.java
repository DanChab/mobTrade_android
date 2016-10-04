package com.jot.JotShop.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jot.JotShop.R;
import com.jot.JotShop.Section;

import java.util.List;

/**
 * Created by D4n on 10/1/2016.
 */
public class SectionsAdapter extends RecyclerView.Adapter<SectionsAdapter.MyViewHolder> {

    private List<Section> sectionsList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mSectionName, mDescription;

        public MyViewHolder(View view) {
            super(view);
            mSectionName = (TextView) view.findViewById(R.id.section_name);
            mDescription = (TextView) view.findViewById(R.id.description);
        }
    }


    public SectionsAdapter(List<Section> moviesList) {
        this.sectionsList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.section_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Section section = sectionsList.get(position);
        holder.mSectionName.setText(section.getName());
        holder.mDescription.setText(section.getDescription());

    }

    @Override
    public int getItemCount() {
        return sectionsList.size();
    }
}