package cz.tyckouni.poopio;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cz.tyckouni.poopio.base.entities.Poop;

public class PoopListAdapter extends RecyclerView.Adapter<PoopListAdapter.PoopViewHolder> {

    private final List<Poop> mPoopList;
    private LayoutInflater mInflater;

    public PoopListAdapter(Context context, List<Poop> poopList) {
        this.mPoopList = poopList;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PoopListAdapter.PoopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.pooplist_item, parent, false);
        return new PoopViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PoopListAdapter.PoopViewHolder holder, int position) {
        Poop current = mPoopList.get(position);
        holder.mPoopDateView.setText(String.valueOf(current.getDateAndTime()));
        holder.mPoopTitleView.setText(current.getType());
//        TODO: set color of item based on poop color?
//        holder.mBackgroundLayout.setBackgroundColor(Color.parseColor(current.getColor()));
    }

    @Override
    public int getItemCount() {
        return mPoopList.size();
    }

    class PoopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mPoopTitleView;
        private final TextView mPoopDateView;
        private final ViewGroup mBackgroundLayout;
        private final PoopListAdapter mAdapter;

        public PoopViewHolder(View itemView, PoopListAdapter adapter) {
            super(itemView);

            itemView.setOnClickListener(this);
            mPoopTitleView = itemView.findViewById(R.id.poop_title);
            mPoopDateView = itemView.findViewById(R.id.poop_date);
            mBackgroundLayout = itemView.findViewById(R.id.background_layout);

            this.mAdapter = adapter;
        }

        @Override
        public void onClick(View v) {
            Intent poopDetailIntent = new Intent(itemView.getContext(), PoopDetailActivity.class);
            poopDetailIntent.putExtra(OverviewActivity.EXTRA_POOP, mPoopList.get(getLayoutPosition()));

            itemView.getContext().startActivity(poopDetailIntent);
        }
    }
}
