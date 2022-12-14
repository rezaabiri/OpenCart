package com.webskygroup.opencart.adapters.HorizontalHomeCategory;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.webskygroup.opencart.R;
import com.webskygroup.opencart.databinding.LayoutHorizontalHomeCategoryBinding;
import com.webskygroup.opencart.models.HorizontalHomeCategory.Category;

import java.util.ArrayList;
import java.util.List;

public class HorizontalHomeCategoryAdapter extends RecyclerView.Adapter<HorizontalHomeCategoryAdapter.HorizontalHomeCategoryViewHolder> {

    LayoutInflater layoutInflater;
    List<Category> categories;

    public HorizontalHomeCategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }


    @NonNull
    @Override
    public HorizontalHomeCategoryAdapter.HorizontalHomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutHorizontalHomeCategoryBinding layoutHorizontalHomeCategoryBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_horizontal_home_category, parent, false);
        return new HorizontalHomeCategoryAdapter.HorizontalHomeCategoryViewHolder(layoutHorizontalHomeCategoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalHomeCategoryAdapter.HorizontalHomeCategoryViewHolder holder, int position) {
        holder.bind(categories.get(position));
        holder.layoutHorizontalHomeCategoryBinding.layoutCategory.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "pos: "+position, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public class HorizontalHomeCategoryViewHolder extends RecyclerView.ViewHolder{
        LayoutHorizontalHomeCategoryBinding layoutHorizontalHomeCategoryBinding;
        public HorizontalHomeCategoryViewHolder(@NonNull LayoutHorizontalHomeCategoryBinding layoutHorizontalHomeCategoryBinding) {
            super(layoutHorizontalHomeCategoryBinding.getRoot());
            this.layoutHorizontalHomeCategoryBinding=layoutHorizontalHomeCategoryBinding;
        }
        void bind(Category category){
            layoutHorizontalHomeCategoryBinding.categoryName.setText(category.category_name);
        }
    }
}
