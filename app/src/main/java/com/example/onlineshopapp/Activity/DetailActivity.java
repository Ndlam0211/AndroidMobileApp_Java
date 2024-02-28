package com.example.onlineshopapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.onlineshopapp.Domain.PopularDomain;
import com.example.onlineshopapp.Helper.ManagementCart;
import com.example.onlineshopapp.Model.Product;
import com.example.onlineshopapp.R;

public class DetailActivity extends AppCompatActivity {
    private Button addToCartBtn;
    private TextView titleTxt, feeTxt, descriptionTxt, reviewTxt, scoreTxt;
    private ImageView picItem,backBtn;
    private Product object;
    private int numberOrder = 1;
    private ManagementCart managementCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        managementCart = new ManagementCart(this);
        initView();
        getBundle();
    }

    private void getBundle() {
        if (getIntent().getExtras() != null){
            object = (Product) getIntent().getExtras().get("object");

            titleTxt.setText(object.getTitle());
            feeTxt.setText("$"+object.getPrice());
            descriptionTxt.setText(object.getDescription());
            reviewTxt.setText(object.getRating().getCount()+"");
            scoreTxt.setText(object.getRating().getRate()+"");
        }
        //int drawableResourceId = this.getResources().getIdentifier(object.getImage(), "drawable", this.getPackageName());

        Glide.with(this)
                .load(object.getImage())
                .into(picItem);

        addToCartBtn.setOnClickListener(view -> {
            object.setNumberInCart(numberOrder);
            managementCart.insertFood(object);
        });

        backBtn.setOnClickListener(view -> {
            finish();
        });
    }

    private void initView() {
        addToCartBtn = findViewById(R.id.addToCartBtn);
        titleTxt = findViewById(R.id.titleTxt);
        feeTxt = findViewById(R.id.priceTxt);
        descriptionTxt = findViewById(R.id.decriptionTxt);
        picItem = findViewById(R.id.pic);
        reviewTxt = findViewById(R.id.reviewTxt);
        scoreTxt = findViewById(R.id.scoreTxt);
        backBtn = findViewById(R.id.backBtn);
    }
}