package com.example.onlineshopapp.Helper;

import android.content.Context;
import android.widget.Toast;

import com.example.onlineshopapp.Domain.PopularDomain;
import com.example.onlineshopapp.Model.Product;

import java.util.ArrayList;

public class ManagementCart {
    private Context context;
    private TinyDB tinyDB;

    public ManagementCart(Context context) {
        this.context = context;
        this.tinyDB = new TinyDB(context);
    }

    public void insertFood(Product item){
        ArrayList<Product> listPop = getListCart();
        boolean existAlready = false;
        int n=0;
        for (int i=0; i < listPop.size(); i++){
           if (listPop.get(i).getTitle().equals(item.getTitle())){
               existAlready=true;
               n=i;
               break;
           }
        }

        if (existAlready){
            listPop.get(n).setNumberInCart(item.getNumberInCart());
        }else {
            listPop.add(item);
        }

        tinyDB.putListObject("CartList", listPop);
        Toast.makeText(context,"Added to your Cart",Toast.LENGTH_SHORT).show();
    }

    public ArrayList<Product> getListCart() {
        return tinyDB.getListObject("CartList");
    }

    public void minusNumber(ArrayList<Product> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener){
        if (listItem.get(position).getNumberInCart()==1){
            listItem.remove(position);
        }else {
            listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart()-1);
        }
        tinyDB.putListObject("CartList", listItem);
        changeNumberItemsListener.change();
    }

    public void plusNumber(ArrayList<Product> listItem, int position, ChangeNumberItemsListener changeNumberItemsListener){
        listItem.get(position).setNumberInCart(listItem.get(position).getNumberInCart()+1);
        tinyDB.putListObject("CartList", listItem);
        changeNumberItemsListener.change();
    }

    public double getTotalFee(){
        ArrayList<Product> listItem = getListCart();
        double fee = 0;
        for (int i=0; i < listItem.size();i++){
            fee= fee+ (listItem.get(i).getPrice() * listItem.get(i).getNumberInCart());
        }
        return fee;
    }
}
