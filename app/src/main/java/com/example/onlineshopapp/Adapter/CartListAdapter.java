package com.example.onlineshopapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.example.onlineshopapp.Domain.PopularDomain;
import com.example.onlineshopapp.Helper.ChangeNumberItemsListener;
import com.example.onlineshopapp.Helper.ManagementCart;
import com.example.onlineshopapp.Model.Product;
import com.example.onlineshopapp.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    ArrayList<Product> listItemSelected;
    private ManagementCart managementCart;
    ChangeNumberItemsListener changeNumberItemsListener;

    public CartListAdapter(ArrayList<Product> listItemSelected, Context context, ChangeNumberItemsListener changeNumberItemsListener) {
        this.listItemSelected = listItemSelected;
        this.managementCart = new ManagementCart(context);
        this.changeNumberItemsListener = changeNumberItemsListener;
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart,parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, int position) {
        holder.title.setText(listItemSelected.get(position).getTitle());
        holder.feeEachItem.setText("$"+listItemSelected.get(position).getPrice());
        holder.totalEachItem.setText("$"+Math.round((listItemSelected.get(position).getNumberInCart()*listItemSelected.get(position).getPrice())));
        holder.num.setText(String.valueOf(listItemSelected.get(position).getNumberInCart()));

//        int drawableResourcesId = holder.itemView.getContext().getResources()
//                .getIdentifier(listItemSelected.get(position).getImage(),"drawable",holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(listItemSelected.get(position).getImage())
                .transform(new GranularRoundedCorners(30,30,30,30))
                .into(holder.pic);

        holder.plusItem.setOnClickListener(view -> {
            managementCart.plusNumber(listItemSelected, position, new ChangeNumberItemsListener() {
                @Override
                public void change() {
                    notifyDataSetChanged();
                    changeNumberItemsListener.change();
                }
            });
        });

        holder.minusItem.setOnClickListener(view -> {
            managementCart.minusNumber(listItemSelected, position, new ChangeNumberItemsListener() {
                @Override
                public void change() {
                    notifyDataSetChanged();
                    changeNumberItemsListener.change();
                }
            });
        });
    }

    @Override
    public int getItemCount() {
        return listItemSelected.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title,feeEachItem,plusItem,minusItem;
        ImageView pic;
        TextView totalEachItem, num;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTxt);
            pic = itemView.findViewById(R.id.pic);
            feeEachItem = itemView.findViewById(R.id.feeEachItemTxt);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
            totalEachItem = itemView.findViewById(R.id.totalEachItemTxt);
            num = itemView.findViewById(R.id.numberItemTxt);
        }
    }
}
