package com.webskygroup.opencart.adapters.FirstHorProductAdapter;

import android.content.res.Configuration;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.webskygroup.opencart.CToast;
import com.webskygroup.opencart.R;
import com.webskygroup.opencart.databinding.LayoutProductItemsBinding;
import com.webskygroup.opencart.models.FirstHorProduct.FirstHorProductModel;
import com.webskygroup.opencart.viewmodels.AppViewModel;

import java.util.List;
import java.util.Locale;

public class FirstHorProductAdapter extends RecyclerView.Adapter<FirstHorProductAdapter.FirstHorProductViewHolder> {

    LayoutInflater layoutInflater;
    List<FirstHorProductModel> firstHorProductModels;


    public FirstHorProductAdapter(List<FirstHorProductModel> firstHorProductModels) {
        this.firstHorProductModels = firstHorProductModels;
    }

    @NonNull
    @Override
    public FirstHorProductAdapter.FirstHorProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutProductItemsBinding layoutProductItemsBinding = DataBindingUtil.inflate(layoutInflater, R.layout.layout_product_items, parent, false);
        return new FirstHorProductAdapter.FirstHorProductViewHolder(layoutProductItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FirstHorProductAdapter.FirstHorProductViewHolder holder, int position) {
        holder.bind(firstHorProductModels.get(position));
        holder.layoutProductItemsBinding.layoutProductsItem.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            Toast.makeText(v.getContext(), "id "+ firstHorProductModels.get(position).getId().toString(), Toast.LENGTH_SHORT).show();
            bundle.putString("product_id",firstHorProductModels.get(position).id);
            Navigation.findNavController(v).navigate(R.id.productDetailsFragment, bundle);
            //Navigation.findNavController(v).enableOnBackPressed(true);
            //Toast.makeText(v.getContext(), "pos: "+position, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return firstHorProductModels.size();
    }

    public class FirstHorProductViewHolder extends RecyclerView.ViewHolder {
        LayoutProductItemsBinding layoutProductItemsBinding;
        public FirstHorProductViewHolder(@NonNull LayoutProductItemsBinding layoutProductItemsBinding) {
            super(layoutProductItemsBinding.getRoot());
            this.layoutProductItemsBinding = layoutProductItemsBinding;

        }

        void bind(FirstHorProductModel firstHorProductModel){

            // load product image
            Picasso.get().load(firstHorProductModel.imageurl).into(layoutProductItemsBinding.productImage, new Callback() {
                @Override
                public void onSuccess() {
                    layoutProductItemsBinding.productImage.setAlpha(0f);
                    layoutProductItemsBinding.productImage.animate().setDuration(500).alpha(1f).start();
                }
                @Override
                public void onError(Exception e) {

                }
            });


            // set product name
            layoutProductItemsBinding.productName.setText(firstHorProductModel.getName().trim());
            // Draw a line on the original price
            SpannableString spannableString = new SpannableString(firstHorProductModel.price);
            spannableString.setSpan(new StrikethroughSpan(),0,firstHorProductModel.price.length(), Spanned.SPAN_MARK_MARK);
            // set product original price with line
            layoutProductItemsBinding.productMainPriceTxt.setText(spannableString);
            // set special price
            layoutProductItemsBinding.productSpecialPriceTxt.setText(firstHorProductModel.special_price.trim());
            //set percentage
            layoutProductItemsBinding.percentageTxt.setText(String.valueOf(firstHorProductModel.percentage) + "%".trim());


        }
    }
}
