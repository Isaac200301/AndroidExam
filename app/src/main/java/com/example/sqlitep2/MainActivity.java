package com.example.sqlitep2;

import android.content.Intent; // Import para los intents
import android.os.Bundle;
import android.util.Log;
import android.widget.Button; // Import para los botones
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlitep2.data.adapter.UserAdapter;
import com.example.sqlitep2.data.dao.ProductDao;
import com.example.sqlitep2.data.dao.UserDao;
import com.example.sqlitep2.data.db.DatabaseManager;
import com.example.sqlitep2.data.model.Product;
import com.example.sqlitep2.data.model.User;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "OUT1";
    private DatabaseManager dbManager;  // Declarar globalmente
    private UserDao userDao;
    private ProductDao productDao;

    private RecyclerView recyclerView;
    private UserAdapter adapter;
    private List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);  // Configuración de vista

        // Inicializar dbManager
        dbManager = DatabaseManager.getInstance(this);
        userDao = new UserDao(dbManager.openDatabase());
        productDao = new ProductDao(dbManager.openDatabase());

        // Inicializar botones
        Button btnCreateUser = findViewById(R.id.btn_create_user);
        Button btnListUsers = findViewById(R.id.btn_list_users);
        Button btnCreateProduct = findViewById(R.id.btn_create_product);
        Button btnListProducts = findViewById(R.id.btn_list_products);

        // Configurar los listeners para iniciar las actividades correspondientes
        btnCreateUser.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateUserActivity.class);
            startActivity(intent);
        });

        btnListUsers.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListUserActivity.class);
            startActivity(intent);
        });

        btnCreateProduct.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateProductActivity.class);
            startActivity(intent);
        });

        btnListProducts.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ListProductActivity.class);
            startActivity(intent);
        });

        // Inicialización de RecyclerView para usuarios
        recyclerView = findViewById(R.id.recyclerView);
        userList = userDao.getAllUsers();  // Obtener todos los usuarios
        adapter = new UserAdapter(userList);
        adapter.setOnItemClickListener(user -> {
            Toast.makeText(this, "Usuario seleccionado: " + user.getUsername(), Toast.LENGTH_SHORT).show();
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Ejemplo de uso de UserDao
        exampleUserCrud();
    }

    private void exampleUserCrud() {
        // CREATE - Insertar usuarios
        User user1 = new User("Carlos Rodríguez", "carlos.rodriguez@gmail.com", "https://icons.iconarchive.com/icons/icons-land/vista-people/72/Office-Customer-Male-Light-icon.png");
        User user2 = new User("María López", "maria.lopez@hotmail.com", "https://icons.iconarchive.com/icons/custom-icon-design/pretty-office-2/72/man-icon.png");
        User user3 = new User("Andrés Gómez", "andres.gomez@outlook.com", "https://icons.iconarchive.com/icons/hopstarter/sleek-xp-basic/72/Administrator-icon.png");

        long userId1 = userDao.insert(user1);
        long userId2 = userDao.insert(user2);
        long userId3 = userDao.insert(user3);

        Log.d(TAG, "Insertados usuarios con IDs: " + userId1 + ", " + userId2 + ", " + userId3);

        // READ - Obtener todos los usuarios
        List<User> allUsers = userDao.getAllUsers();
        for (User user : allUsers) {
            Log.d(TAG, "Usuario: " + user.getUsername() + ", Correo: " + user.getEmail());
        }

        // UPDATE - Actualizar un usuario
        User userToUpdate = userDao.getUserById(userId2);
        if (userToUpdate != null) {
            userToUpdate.setEmail("maria.lopez.nueva@gmail.com");
            int updatedRows = userDao.update(userToUpdate);
            Log.d(TAG, "Actualizados " + updatedRows + " usuario(s)");
        }

        // DELETE - Eliminar un usuario
        int deletedRows = userDao.delete(userId3);
        Log.d(TAG, "Eliminados " + deletedRows + " usuario(s)");

        // Verificar los cambios
        allUsers = userDao.getAllUsers();
        for (User user : allUsers) {
            Log.d(TAG, "Después de operaciones - Usuario: " + user.getUsername() + ", Correo: " + user.getEmail());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.closeDatabase();  // Cerrar correctamente la base de datos
    }
}
