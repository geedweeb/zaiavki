package com.fomin.ufanetapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fomin.ufanetapp.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskara.widget.MaskEditText;

public class FirstActivity extends AppCompatActivity {
    ImageButton imBtn;
    Button nextBtn;
    TextInputEditText nameEd, surnameEd, lastnameEd;
    MaskEditText phoneEd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        nextBtn = findViewById(R.id.btn_next);
        nameEd = findViewById(R.id.name_ed);
        imBtn = findViewById(R.id.image_btn);
        surnameEd = findViewById(R.id.surname_ed);
        lastnameEd = findViewById(R.id.lastname_ed);
        phoneEd = findViewById(R.id.phone_ed);
        nextBtn.setOnClickListener(v ->  checkUser());
        imBtn.setOnClickListener(v -> showSecondActivity());


        phoneEd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Не используется
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Не используется
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String phoneNumber = phoneEd.getText().toString();
                // Вы можете использовать phoneNumber для обработки введенного номера телефона
            }
        });

    }

    private void checkUser() {
        if (nameEd.getText().toString().isEmpty() || surnameEd.getText().toString().isEmpty() ||
                lastnameEd.getText().toString().isEmpty() || phoneEd.getText().toString().isEmpty()) {
            Toast.makeText(FirstActivity.this, "Все поля должны быть заполнены", Toast.LENGTH_SHORT).show();
            return;
        }
        // Получаем ссылку на базу данных
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
        String phoneNumberToCheck = phoneEd.getText().toString();

        // Выполняем запрос к базе данных для проверки наличия пользователя с таким номером телефона
        databaseReference.orderByChild("phone").equalTo(phoneNumberToCheck).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Пользователь с таким номером уже зарегистрирован
                    Toast.makeText(FirstActivity.this, "Пользователь с таким номером уже зарегистрирован", Toast.LENGTH_SHORT).show();
                } else {

                    String name = nameEd.getText().toString();
                    String lastname = lastnameEd.getText().toString();
                    String surname = surnameEd.getText().toString();
                    String phone = phoneEd.getText().toString();

                    Intent intent = new Intent(FirstActivity.this, SecondActivity.class);

                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Обработка ошибок запроса к базе данных
                Toast.makeText(FirstActivity.this, "Ошибка при выполнении запроса к базе данных", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name", nameEd.getText().toString());
        editor.putString("lastname", lastnameEd.getText().toString());
        editor.putString("surname", surnameEd.getText().toString());
        editor.putString("phone", phoneEd.getText().toString());
        editor.apply();
    }

    public void showSecondActivity()
    {
        Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        // здесь можно выполнить нужные действия, например, закрыть текущее окно или перейти на другой экран
        super.onBackPressed();
    }
}