package cz.tyckouni.poopio.ui.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import cz.tyckouni.poopio.R;
import cz.tyckouni.poopio.base.entities.PoopType;

public class PoopTypesAdapter extends RecyclerView.Adapter<PoopTypesAdapter.PoopTypeHolder> {

    private GradientDrawable mGradientDrawable;
    private ArrayList<PoopType> mPoopTypesData;
    private Context mContext;

    public PoopTypesAdapter(Context mContext, ArrayList<PoopType> mPoopTypesData) {
        this.mPoopTypesData = mPoopTypesData;
        this.mContext = mContext;

        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setColor(Color.GRAY);

        //Make the placeholder same size as the images
        //TODO?
    }

    @NonNull
    @Override
    public PoopTypeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PoopTypeHolder(mContext, LayoutInflater.from(mContext)
                .inflate(R.layout.poop_type_list_item, parent, false), mGradientDrawable);
    }

    @Override
    public void onBindViewHolder(@NonNull PoopTypeHolder holder, int position) {
        PoopType currentPoopType = mPoopTypesData.get(position);

        holder.bindTo(currentPoopType);
    }

    @Override
    public int getItemCount() {
        return mPoopTypesData.size();
    }

    static class PoopTypeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView mPoopTypeImage;
        private Context mContext;
        private GradientDrawable mGradientDrawable;

        PoopTypeHolder(Context context, View itemView, GradientDrawable gradientDrawable) {
            super(itemView);

            mContext = context;
            mPoopTypeImage = itemView.findViewById(R.id.poop_type_image);
            mGradientDrawable = gradientDrawable;
        }

        @Override
        public void onClick(View v) {
            // TODO
        }

        void bindTo(PoopType currentPoopType) {
            Glide.with(mContext)
                    .load(currentPoopType.getResourceId())
                    .placeholder(mGradientDrawable)
                    .into(mPoopTypeImage);
        }
    }
}
