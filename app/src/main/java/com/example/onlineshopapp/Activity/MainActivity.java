package com.example.onlineshopapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopapp.Adapter.PopularListAdapter;
import com.example.onlineshopapp.Adapter.SlideAdapter;
import com.example.onlineshopapp.Api.ProductService;
import com.example.onlineshopapp.Domain.SlideItem;
import com.example.onlineshopapp.Model.User;
import com.example.onlineshopapp.R;
import com.example.onlineshopapp.Model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private PopularListAdapter popularListAdapter;
    private RecyclerView recyclerViewPopular;
    private List<Product> items;
    private TextView txtUserName;
    private EditText edtSearch;
    private User userApi;
    private ViewPager2 viewPager2;
    private Runnable sliderRunnable;
    private Handler sliderHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            userApi = (User) getIntent().getSerializableExtra("user");
            TextView txtUserName = findViewById(R.id.txtUser);
            txtUserName.setText(getIntent().getExtras().getString("userName"));
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        initRecycleView();
        initViewPager();
        initSearchView();
        bottom_navigation();
    }
    private void initViewPager() {
        viewPager2 = findViewById(R.id.view_pager);

        List<SlideItem> slideItems = new ArrayList<>();
        slideItems.add(new SlideItem(R.drawable.slide1));
        slideItems.add(new SlideItem(R.drawable.slide2));
        slideItems.add(new SlideItem(R.drawable.slide3));
        slideItems.add(new SlideItem(R.drawable.slide4));
        slideItems.add(new SlideItem(R.drawable.slide5));
        slideItems.add(new SlideItem(R.drawable.slide6));
        slideItems.add(new SlideItem(R.drawable.slide7));

        viewPager2.setAdapter(new SlideAdapter(slideItems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(7);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r*0.15f);
            }
        });

        viewPager2.setPageTransformer(compositePageTransformer);

        sliderHandler = new Handler();

        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                viewPager2.setCurrentItem(viewPager2.getCurrentItem() + 1);
            }
        };

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

                sliderHandler.removeCallbacks(sliderRunnable);
                sliderHandler.postDelayed(sliderRunnable,3000);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    private void initSearchView() {
        edtSearch = findViewById(R.id.edt_search);
        edtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(getApplicationContext(),SearchActivity.class);
                startActivity(it);
            }
        });
    }

    private void bottom_navigation() {
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout profileBtn = findViewById(R.id.profileBtn);

        homeBtn.setOnClickListener(view -> startActivity(new Intent(MainActivity.this,MainActivity.class)));

        cartBtn.setOnClickListener(v -> {
            Intent it = new Intent(getApplicationContext(), CartActivity.class);
            startActivity(it);
        });

        profileBtn.setOnClickListener(v -> {
            Intent it = new Intent(getApplicationContext(), ProfileActivity.class);
            if (userApi != null){
                it.putExtra("user", userApi);
            }
            startActivity(it);
        });
    }

    private void initRecycleView(){
        items = new ArrayList<>();
//        items.add(new PopularDomain("Macbook Pro M3 12 Chip","For now, you can check out the 2022 MacBook Pro 13 using the link below. Apple will open pre-orders on June 17th on its online store, and it will be fully available on June 24th. Apple MacBook Pro 13-inch (2022) The MacBook 13 (2022) comes with an Apple M2 chip promising even more CPU and GPU performance, in the same design as before.","pic1", 15,4,400));
//        items.add(new PopularDomain("PS5 Digital","For now, you can check out the 2022 MacBook Pro 13 using the link below. Apple will open pre-orders on June 17th on its online store, and it will be fully available on June 24th. Apple MacBook Pro 13-inch (2022) The MacBook 13 (2022) comes with an Apple M2 chip promising even more CPU and GPU performance, in the same design as before.","pic2", 10,4.8,500));
//        items.add(new PopularDomain("Iphone 14","For now, you can check out the 2022 MacBook Pro 13 using the link below. Apple will open pre-orders on June 17th on its online store, and it will be fully available on June 24th. Apple MacBook Pro 13-inch (2022) The MacBook 13 (2022) comes with an Apple M2 chip promising even more CPU and GPU performance, in the same design as before.","pic3", 13,4.5,800));
//        items.add(new PopularDomain("Sony Headphone","For now, you can check out the 2022 MacBook Pro 13 using the link below. Apple will open pre-orders on June 17th on its online store, and it will be fully available on June 24th. Apple MacBook Pro 13-inch (2022) The MacBook 13 (2022) comes with an Apple M2 chip promising even more CPU and GPU performance, in the same design as before.","pic4", 18,4.6,200));

        recyclerViewPopular = findViewById(R.id.view1);
        recyclerViewPopular.setLayoutManager(new GridLayoutManager(this,2));

        callApiGetProducts();
    }

    private void callApiGetProducts(){
        Call<List<Product>> call = ProductService.apiService.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    items = response.body();
                    popularListAdapter = new PopularListAdapter(items);
                    recyclerViewPopular.setAdapter(popularListAdapter);
                } else {
                    Toast.makeText(MainActivity.this,"Request failed with code: " + response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"onFailure",Toast.LENGTH_SHORT).show();
            }
        });
    }

}