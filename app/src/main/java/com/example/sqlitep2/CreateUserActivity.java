package com.example.sqlitep2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sqlitep2.data.dao.UserDao;
import com.example.sqlitep2.data.db.DatabaseManager;
import com.example.sqlitep2.data.model.User;

public class CreateUserActivity extends AppCompatActivity {

    private EditText etName, etEmail, etImageUrl;
    private Button btnCreateUser, btnBack;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user); // Asegúrate de tener este archivo XML

        DatabaseManager dbManager = DatabaseManager.getInstance(this);
        userDao = new UserDao(dbManager.openDatabase());

        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etImageUrl = findViewById(R.id.et_image_url);
        btnCreateUser = findViewById(R.id.btn_create_user);
        btnBack = findViewById(R.id.btn_back);

        btnCreateUser.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String email = etEmail.getText().toString();
            String imageUrl = etImageUrl.getText().toString();

            if (name.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Todos los campos deben ser llenados", Toast.LENGTH_SHORT).show();
            } else {
                User user = new User(name, email, imageUrl);
                long id = userDao.insert(user);
                if (id > 0) {
                    Toast.makeText(this, "Usuario creado con éxito", Toast.LENGTH_SHORT).show();
                    finish(); // Regresar a la actividad principal
                } else {
                    Toast.makeText(this, "Error al crear usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(v -> finish()); // Regresar a la actividad principal
    }
}
