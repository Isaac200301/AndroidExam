package com.example.sqlitep2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitep2.data.adapter.ProductAdapter;
import com.example.sqlitep2.data.dao.ProductDao;
import com.example.sqlitep2.data.db.DatabaseManager;
import com.example.sqlitep2.data.model.Product;

import java.util.List;

public class ListProductActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private ProductDao productDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_product); // Asegúrate de tener este archivo XML

        DatabaseManager dbManager = DatabaseManager.getInstance(this);
        productDao = new ProductDao(dbManager.openDatabase());

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Product> productList = productDao.getAllProducts();
        adapter = new ProductAdapter(productList);
        recyclerView.setAdapter(adapter);
    }
}
