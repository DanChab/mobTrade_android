package com.jot.shoprite;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by D4n on 9/4/2016.
 */
public class SnapAdapter extends RecyclerView.Adapter<SnapAdapter.ViewHolder> {

    public static final int VERTICAL = 0;
    public static final int HORIZOTAL = 1;

    private ArrayList<Snap> mSnaps;
    // Disable touch detection for parent recyclerView if we use vertical nested recyclerViews
    private View.OnTouchListener mTouchListner = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return false;
        }
    };

    public SnapAdapter() {
        mSnaps = new ArrayList<>();
    }

    public void addSnap(Snap snap) {
        mSnaps.add(snap);
    }

    public int getItemView(int position) {
        Snap snap = mSnaps.get(position);
        switch (snap.getGravity()) {
            case Gravity.CENTER_VERTICAL:
                return VERTICAL;
            case Gravity.CENTER_HORIZONTAL:
                return HORIZOTAL;
            case Gravity.START:
                return HORIZOTAL;
            case Gravity.TOP:
                return VERTICAL;
            case Gravity.END:
                return HORIZOTAL;
            case Gravity.BOTTOM:
                return VERTICAL;
        }
        return HORIZOTAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = viewType == VERTICAL ? LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_snap_vertical, parent, false)
                : LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_snap, parent, false);

        if (viewType == VERTICAL) {
            view.findViewById(R.id.recyclerView).setOnTouchListener(mTouchListner);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SnapAdapter.ViewHolder holder, int position) {
        Snap snap = mSnaps.get(position);
        holder.snapTextView.setText(snap.getText());



       if(snap.getGravity()== Gravity.CENTER_HORIZONTAL
                || snap.getGravity()==Gravity.CENTER_VERTICAL
                || snap.getGravity()==Gravity.CENTER){
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(holder.recyclerView.getContext(),snap.getGravity()==Gravity.CENTER_HORIZONTAL ? LinearLayoutManager.HORIZONTAL:LinearLayoutManager.VERTICAL,false));
            holder.recyclerView.setOnFlingListener(null);
            new LinearSnapHelper().attachToRecyclerView(holder.recyclerView);
        }

        holder.recyclerView.setAdapter(new Adapter(snap.getGravity()==Gravity.START || snap.getGravity()==Gravity.END
        || snap.getGravity()==Gravity.CENTER_HORIZONTAL, snap.getApps()));
    }

    @Override
    public int getItemCount() {

        return mSnaps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView snapTextView;
        public RecyclerView recyclerView;

        public ViewHolder(View itemView) {
            super(itemView);
            snapTextView = (TextView) itemView.findViewById(R.id.snapTextView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }
}
