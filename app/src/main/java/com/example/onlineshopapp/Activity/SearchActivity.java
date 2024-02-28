package com.example.onlineshopapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlineshopapp.Adapter.PopularListAdapter;
import com.example.onlineshopapp.Api.ProductService;
import com.example.onlineshopapp.Model.Product;
import com.example.onlineshopapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    PopularListAdapter popularListAdapter;
    List<Product> productsListSearch;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        setVariable();
    }

    private void initView() {
        searchView = findViewById(R.id.edtSearch);
        recyclerView = findViewById(R.id.rycyclerview_search);
        backBtn = findViewById(R.id.backBtn);

        callApiGetProducts();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                popularListAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                popularListAdapter.getFilter().filter(newText);
                return false;
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void callApiGetProducts(){
        Call<List<Product>> call = ProductService.apiService.getAllProducts();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    productsListSearch = response.body();
                    popularListAdapter = new PopularListAdapter(productsListSearch);
                    recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
                    recyclerView.setAdapter(popularListAdapter);
                } else {
                    Toast.makeText(SearchActivity.this,"Request failed with code: " + response.code(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Toast.makeText(SearchActivity.this,"onFailure",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setVariable() {
        backBtn.setOnClickListener(view -> finish());
    }
}