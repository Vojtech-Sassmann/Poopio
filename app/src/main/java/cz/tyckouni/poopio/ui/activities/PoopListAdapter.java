package cz.tyckouni.poopio.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import cz.tyckouni.poopio.R;
import cz.tyckouni.poopio.base.entities.Poop;
import cz.tyckouni.poopio.core.dao.FBPoopsDaoImpl;
import cz.tyckouni.poopio.core.dao.PoopsDao;

public class PoopListAdapter extends RecyclerView.Adapter<PoopListAdapter.PoopViewHolder> {

    private static final String TAG = "PoopListAdapter";

    private final List<Poop> mPoopList;
    private LayoutInflater mInflater;
    private Context mContext;

    public PoopListAdapter(Context context, List<Poop> poopList) {
        this.mContext = context;
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
    public void onBindViewHolder(@NonNull final PoopListAdapter.PoopViewHolder holder, int position) {
        final Poop current = mPoopList.get(position);
        holder.mPoopDateView.setText(String.valueOf(current.getDate()));
        holder.mPoopTitleView.setText(current.getType());

        holder.mMenuTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //creating a popup menu
                PopupMenu popup = new PopupMenu(mContext, holder.mMenuTextView);
                //inflating menu from xml resource
                popup.inflate(R.menu.menu_poop_item);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_delete:

                                deletePoop(current, FirebaseAuth.getInstance().getCurrentUser());
                                return true;
                            default:
                                return false;
                        }
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }

    private void deletePoop(Poop poop, FirebaseUser currentUser) {
        new FBPoopsDaoImpl().delete(poop, currentUser, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e(TAG, "onComplete: failed to delete poop, reason: " +
                            databaseError.getMessage());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPoopList.size();
    }

    class PoopViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView mPoopTitleView;
        private final TextView mPoopDateView;
        private final TextView mMenuTextView;
        private final ViewGroup mBackgroundLayout;
        private final PoopListAdapter mAdapter;

        public PoopViewHolder(View itemView, PoopListAdapter adapter) {
            super(itemView);

            itemView.setOnClickListener(this);
            mPoopTitleView = itemView.findViewById(R.id.poop_title);
            mPoopDateView = itemView.findViewById(R.id.poop_date);
            mBackgroundLayout = itemView.findViewById(R.id.background_layout);
            mMenuTextView = itemView.findViewById(R.id.menu);

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
