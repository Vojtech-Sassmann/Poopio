package cz.tyckouni.poopio;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;
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
        holder.mPoopDateView.setText(String.valueOf(current.getCalendar().get(Calendar.DATE)));
        holder.mPoopTitleView.setText(current.getType());
    }

    @Override
    public int getItemCount() {
        return mPoopList.size();
    }

    class PoopViewHolder extends RecyclerView.ViewHolder {
        private final TextView mPoopTitleView;
        private final TextView mPoopDateView;
        private final PoopListAdapter mAdapter;

        public PoopViewHolder(View itemView, PoopListAdapter adapter) {
            super(itemView);

            mPoopTitleView = itemView.findViewById(R.id.poop_title);
            mPoopDateView = itemView.findViewById(R.id.poop_date);

            this.mAdapter = adapter;
        }
    }
}
