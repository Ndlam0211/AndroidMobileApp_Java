package com.example.onlineshopapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.L;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.onlineshopapp.Activity.DetailActivity;
import com.example.onlineshopapp.R;
import com.example.onlineshopapp.Model.Product;

import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PopularListAdapter extends RecyclerView.Adapter<PopularListAdapter.Viewholder> implements Filterable {
    List<Product> items;
    List<Product> itemsOld;
    Context context;

    public PopularListAdapter(List<Product> items) {
        this.items = items;
        this.itemsOld = items;
    }

    @NonNull
    @Override
    public PopularListAdapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pop_list,parent,false);
        context = parent.getContext();
        return new Viewholder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularListAdapter.Viewholder holder, int position) {
        Product product = items.get(position);
        if (product == null){
            return;
        }

        holder.titleTxt.setText(items.get(position).getTitle());
        holder.feeTxt.setText("$"+items.get(position).getPrice());
        holder.scoreTxt.setText(""+items.get(position).getRating().getRate());
        holder.reviewTxt.setText(""+items.get(position).getRating().getCount());

//        int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getImage(),
//                "drawable",holder.itemView.getContext().getPackageName());
        
        Glide.with(holder.itemView.getContext())
                .load(product.getImage())
                .transform(new GranularRoundedCorners(30,30,0,0))
                .into(holder.pic);

        holder.itemView.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("object", items.get(position));

            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);
            intent.putExtras(bundle);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        if (items != null){
            return items.size();
        }
        return 0;
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        final TextView titleTxt,feeTxt,scoreTxt, reviewTxt;
        final ImageView pic;
        public Viewholder(@NonNull View itemView) {
            super(itemView);

            titleTxt = itemView.findViewById(R.id.titleTxt);
            feeTxt = itemView.findViewById(R.id.feeTxt);
            scoreTxt = itemView.findViewById(R.id.scoreTxt);
            pic = itemView.findViewById(R.id.pic);
            reviewTxt = itemView.findViewById(R.id.reviewTxt);
        }
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String strSearch = constraint.toString();
                if (strSearch.isEmpty()){
                    items = itemsOld;
                }else {
                    List<Product> list = new ArrayList<>();
                    for (Product product : itemsOld){
                        if (product.getTitle().toLowerCase().contains(strSearch.toLowerCase())){
                            list.add(product);
                        }
                    }
                    items = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = items;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                items = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
