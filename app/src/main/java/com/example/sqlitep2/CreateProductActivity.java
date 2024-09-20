package com.example.sqlitep2;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitep2.data.dao.ProductDao;
import com.example.sqlitep2.data.db.DatabaseManager;
import com.example.sqlitep2.data.model.Product;

public class CreateProductActivity extends AppCompatActivity {

    private EditText etProductName, etProductPrice;
    private Button btnCreateProduct, btnBack;
    private ProductDao productDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_product); // Asegúrate de tener este archivo XML

        DatabaseManager dbManager = DatabaseManager.getInstance(this);
        productDao = new ProductDao(dbManager.openDatabase());

        etProductName = findViewById(R.id.et_product_name);
        etProductPrice = findViewById(R.id.et_product_price);
        btnCreateProduct = findViewById(R.id.btn_create_product);
        btnBack = findViewById(R.id.btn_back);

        btnCreateProduct.setOnClickListener(v -> {
            String name = etProductName.getText().toString();
            String priceStr = etProductPrice.getText().toString();

            if (name.isEmpty() || priceStr.isEmpty()) {
                Toast.makeText(this, "Todos los campos deben ser llenados", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    double price = Double.parseDouble(priceStr);
                    Product product = new Product(name, price);
                    long id = productDao.insert(product);
                    if (id > 0) {
                        Toast.makeText(this, "Producto creado con éxito", Toast.LENGTH_SHORT).show();
                        finish(); // Regresar a la actividad principal
                    } else {
                        Toast.makeText(this, "Error al crear producto", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(this, "Precio debe ser un número válido", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(v -> finish()); // Regresar a la actividad principal
    }
}
