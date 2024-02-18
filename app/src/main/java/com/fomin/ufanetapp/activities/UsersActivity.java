package com.fomin.ufanetapp.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.fomin.ufanetapp.R;
import com.fomin.ufanetapp.database.User;
import com.fomin.ufanetapp.database.UserAdapter;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList = new ArrayList<>();
    private DatabaseReference usersRef; // Добавляем ссылку на базу данных Firebase
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        recyclerView = findViewById(R.id.recyclerView);
        // Инициализируем ссылку на базу данных Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
        setupRecyclerView();
        loadAllUsersFromFirebase(); // Загружаем пользователей из Firebase
    }
    private void setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(this, userList); // передайте контекст (this) и список пользователей
        recyclerView.setAdapter(userAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(userAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }
    public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
        private UserAdapter adapter;

        public SwipeToDeleteCallback(UserAdapter adapter) {
            super(0, ItemTouchHelper.LEFT); // Разрешаем свайп только влево
            this.adapter = adapter;
        }

        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false; // Мы не поддерживаем перемещение элементов
        }

        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            User userToDelete = userList.get(position);
            userList.remove(position); // Remove the user from the list
            adapter.notifyItemRemoved(position); // Notify the adapter about the removed item

            // Remove the user from the Firebase Realtime Database
            DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users");
            Query query = databaseRef.orderByChild("phone").equalTo(userToDelete.getPhone());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        snapshot.getRef().removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error
                }
            });
        }
    }

    private void loadAllUsersFromFirebase() {
        // Добавляем слушатель для получения данных из Firebase
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear(); // Очищаем список перед добавлением новых данных
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    userList.add(user); // Добавляем пользователя в список
                }
                userAdapter.notifyDataSetChanged(); // Обновляем список после загрузки данных
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок при загрузке данных из Firebase
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UsersActivity.this, SecondActivity.class);
        startActivity(intent);
        super.onBackPressed();
    }
}



