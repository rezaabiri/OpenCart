package com.webskygroup.opencart.adapters.SliderAdapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.webskygroup.opencart.R;
import com.webskygroup.opencart.databinding.LayoutSliderItemsBinding;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {

    LayoutInflater layoutInflater;
    ArrayList<String> arrayList;
    ViewPager2 viewPager2;

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutSliderItemsBinding layoutSliderItemsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_slider_items, parent, false);
        return new SliderViewHolder(layoutSliderItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.setImage(arrayList.get(position));
        if (position == arrayList.size() - 2){
            viewPager2.post(sliderRunnable);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public SliderAdapter(ArrayList<String> arrayList, ViewPager2 viewPager2) {
        this.arrayList = arrayList;
        this.viewPager2 = viewPager2;
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder{
        LayoutSliderItemsBinding layoutSliderItemsBinding;
        SliderViewHolder(@NonNull LayoutSliderItemsBinding layoutSliderItemsBinding) {
            super(layoutSliderItemsBinding.getRoot());
            this.layoutSliderItemsBinding=layoutSliderItemsBinding;
        }
        void setImage(String photo){
            Glide.with(layoutSliderItemsBinding.getRoot().getContext()).load(photo)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(layoutSliderItemsBinding.imageSlide);
            layoutSliderItemsBinding.executePendingBindings();
        }
    }

    private void OnClick(){

    }

    private  Runnable sliderRunnable = new Runnable() {
        @Override
        public void run() {
            arrayList.addAll(arrayList);
            notifyDataSetChanged();
        }
    };
}
