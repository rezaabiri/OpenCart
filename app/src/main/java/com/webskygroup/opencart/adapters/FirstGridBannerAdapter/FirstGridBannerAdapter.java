package com.webskygroup.opencart.adapters.FirstGridBannerAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.webskygroup.opencart.R;
import com.webskygroup.opencart.databinding.LayoutGridBannerBinding;
import com.webskygroup.opencart.models.FirstGridBanner.FirstGridBannerModel;

import java.util.List;

public class FirstGridBannerAdapter extends RecyclerView.Adapter<FirstGridBannerAdapter.GridBannerViewHolder> {

    LayoutInflater layoutInflater;
    List<FirstGridBannerModel> gridBannerModels;
    Context context;

    public FirstGridBannerAdapter(List<FirstGridBannerModel> gridBannerModels, Context context) {
        this.gridBannerModels = gridBannerModels;
        this.context = context;
    }

    @NonNull
    @Override
    public FirstGridBannerAdapter.GridBannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutGridBannerBinding layoutGridBannerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_grid_banner, parent, false);
        return new FirstGridBannerAdapter.GridBannerViewHolder(layoutGridBannerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FirstGridBannerAdapter.GridBannerViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.bind(gridBannerModels.get(position));
        holder.layoutGridBannerBinding.gridBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(layoutInflater.getContext(), "pos: "+String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return gridBannerModels.size();
    }

    public class GridBannerViewHolder extends RecyclerView.ViewHolder {
        LayoutGridBannerBinding layoutGridBannerBinding;
        public GridBannerViewHolder(@NonNull LayoutGridBannerBinding layoutGridBannerBinding) {
            super(layoutGridBannerBinding.getRoot());
            this.layoutGridBannerBinding = layoutGridBannerBinding;
        }

        public void bind(FirstGridBannerModel gridBannerModel){

            Picasso.get().load(gridBannerModel.imageurl).into(layoutGridBannerBinding.gridBanner, new Callback() {
                @Override
                public void onSuccess() {
                    //layoutGridBannerBinding.gridBanner.setAlpha(0f);
                    //Picasso.get().load(gridBannerModel.imageurl).into(layoutGridBannerBinding.gridBanner);
                    //layoutGridBannerBinding.gridBanner.animate().setDuration(500).alpha(1f).start();
                }
                @Override
                public void onError(Exception e) {

                }
            });

        }
    }


}
