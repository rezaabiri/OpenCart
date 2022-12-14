package com.webskygroup.opencart.adapters.SecondGridBannerAdapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.webskygroup.opencart.R;
import com.webskygroup.opencart.databinding.LayoutSecondGridBannerBinding;
import com.webskygroup.opencart.models.SecondGridBanner.SecondGridBannerModel;

import java.util.List;

public class SecondGridBannerAdapter extends RecyclerView.Adapter<SecondGridBannerAdapter.SecondGridBannerViewHolder> {
    LayoutInflater layoutInflater;
    List<SecondGridBannerModel> secondGridBannerModels;

    public SecondGridBannerAdapter(List<SecondGridBannerModel> secondGridBannerModels) {
        this.secondGridBannerModels = secondGridBannerModels;
    }

    @NonNull
    @Override
    public SecondGridBannerAdapter.SecondGridBannerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutSecondGridBannerBinding layoutSecondGridBannerBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_second_grid_banner,parent,false);
        return new SecondGridBannerAdapter.SecondGridBannerViewHolder(layoutSecondGridBannerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SecondGridBannerAdapter.SecondGridBannerViewHolder holder, int position) {
        holder.bind(secondGridBannerModels.get(position));
        holder.layoutSecondGridBannerBinding.secondGridBannerImage.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "pos : "+position, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    public class SecondGridBannerViewHolder extends RecyclerView.ViewHolder {
        LayoutSecondGridBannerBinding layoutSecondGridBannerBinding;
        public SecondGridBannerViewHolder(@NonNull LayoutSecondGridBannerBinding layoutSecondGridBannerBinding) {
            super(layoutSecondGridBannerBinding.getRoot());
            this.layoutSecondGridBannerBinding = layoutSecondGridBannerBinding;
        }

        void bind(SecondGridBannerModel secondGridBannerModel){
            // load product image
            Picasso.get().load(secondGridBannerModel.imageurl).into(layoutSecondGridBannerBinding.secondGridBannerImage, new Callback() {
                @Override
                public void onSuccess() {
                    layoutSecondGridBannerBinding.secondGridBannerImage.setAlpha(0f);
                    layoutSecondGridBannerBinding.secondGridBannerImage.animate().setDuration(500).alpha(1f).start();
                }
                @Override
                public void onError(Exception e) {

                }
            });

        }
    }
}
