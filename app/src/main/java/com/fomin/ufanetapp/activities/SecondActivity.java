package com.fomin.ufanetapp.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.fomin.ufanetapp.R;
import com.fomin.ufanetapp.database.User;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SecondActivity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTv;
    TextView tvName1, tvName2, tvName3, activeTv1, activeTv2, advanceTv1, advanceTv2, modernTv1, modernTv2, cabelTv1, cabelTv2;
    Button btnSave, btnShow;
    Switch switch1;
    Boolean i = false;
    public String selectedAddress, textValue, cabelText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        btnSave = findViewById(R.id.btn_save);
        btnShow = findViewById(R.id.btn_show);
        tvName1 = findViewById(R.id.tv_name1);
        tvName2 = findViewById(R.id.tv_name2);
        tvName3 = findViewById(R.id.tv_name3);
        activeTv1 = findViewById(R.id.active_tv1);
        activeTv2 = findViewById(R.id.active_tv2);
        advanceTv1 = findViewById(R.id.advance_tv1);
        advanceTv2 = findViewById(R.id.advance_tv2);
        modernTv1 = findViewById(R.id.modern_tv1);
        modernTv2 = findViewById(R.id.modern_tv2);
        autoCompleteTv = findViewById(R.id.autoComplete_tv);
        cabelTv2 = findViewById(R.id.cabel_tv2);
        cabelTv1 = findViewById(R.id.cabel_tv1);
        switch1 = findViewById(R.id.switch1);

        btnShow.setOnClickListener(v -> showUsersActivity());
        SharedPreferences sharedPreferences = getSharedPreferences("userData", Context.MODE_PRIVATE);

        String name1 = sharedPreferences.getString("name", "");
        String lastname1 = sharedPreferences.getString("lastname", "");
        String surname1 = sharedPreferences.getString("surname", "");
        String phone1 = sharedPreferences.getString("phone", "");

        String[] cities = getResources().getStringArray(R.array.cities);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cities);
        autoCompleteTv.setAdapter(arrayAdapter);
        autoCompleteTv.setSelection(0);

        tvName1.setOnClickListener(v -> {
            if(!modernTv1.getText().toString().isEmpty())
            {
                textValue = tvName1.getText().toString();
                tvName1.setTextColor(Color.GREEN);
                activeTv1.setTextColor(Color.GREEN);
                activeTv2.setTextColor(Color.GREEN);
                tvName2.setTextColor(Color.WHITE);
                tvName3.setTextColor(Color.WHITE);
                advanceTv1.setTextColor(Color.WHITE);
                advanceTv2.setTextColor(Color.WHITE);
                modernTv1.setTextColor(Color.WHITE);
                modernTv2.setTextColor(Color.WHITE);
            }
            else
            {
                tvName1.setTextColor(Color.WHITE);
                tvName2.setTextColor(Color.WHITE);
                tvName3.setTextColor(Color.WHITE);
                Toast.makeText(SecondActivity.this, "Для выбора тарифа укажите адрес!", Toast.LENGTH_SHORT).show();
            }


        });
        tvName2.setOnClickListener(v -> {
            if(!modernTv1.getText().toString().isEmpty())
            {
                textValue = tvName2.getText().toString();
                tvName2.setTextColor(Color.GREEN);
                advanceTv1.setTextColor(Color.GREEN);
                advanceTv2.setTextColor(Color.GREEN);
                tvName1.setTextColor(Color.WHITE);
                tvName3.setTextColor(Color.WHITE);
                activeTv1.setTextColor(Color.WHITE);
                activeTv2.setTextColor(Color.WHITE);
                modernTv1.setTextColor(Color.WHITE);
                modernTv2.setTextColor(Color.WHITE);
            }
            else
            {
                tvName1.setTextColor(Color.WHITE);
                tvName2.setTextColor(Color.WHITE);
                tvName3.setTextColor(Color.WHITE);
                Toast.makeText(SecondActivity.this, "Для выбора тарифа укажите адрес!", Toast.LENGTH_SHORT).show();
            }

        });
        tvName3.setOnClickListener(v -> {
            if(!modernTv1.getText().toString().isEmpty()){
                textValue = tvName3.getText().toString();
                tvName3.setTextColor(Color.GREEN);
                modernTv1.setTextColor(Color.GREEN);
                modernTv2.setTextColor(Color.GREEN);
                tvName1.setTextColor(Color.WHITE);
                tvName2.setTextColor(Color.WHITE);
                activeTv1.setTextColor(Color.WHITE);
                activeTv2.setTextColor(Color.WHITE);
                advanceTv1.setTextColor(Color.WHITE);
                advanceTv2.setTextColor(Color.WHITE);
            }
            else
            {
                tvName1.setTextColor(Color.WHITE);
                tvName2.setTextColor(Color.WHITE);
                tvName3.setTextColor(Color.WHITE);
                Toast.makeText(SecondActivity.this, "Для выбора тарифа укажите адрес!", Toast.LENGTH_SHORT).show();
            }

        });
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                // Включен переключатель, меняем цвет текста
                cabelTv1.setTextColor(Color.GREEN);
                cabelTv2.setTextColor(Color.GREEN);
                cabelText = "Кабельное ТВ";
            } else {
                // Выключен переключатель, возвращаем цвет текста
                cabelTv1.setTextColor(Color.WHITE);
                cabelTv2.setTextColor(Color.WHITE);
                cabelText = null;
            }
        });


        btnSave.setOnClickListener(v -> {
            if (lastname1 == null && name1 == null && surname1 == null && phone1 == null) {
                Toast.makeText(SecondActivity.this, "Пожалуйста введите информацию повторно", Toast.LENGTH_SHORT).show();
            }
            else if (selectedAddress == null) {
                Toast.makeText(SecondActivity.this, "Пожалуйста введите адрес", Toast.LENGTH_SHORT).show();
            }
            else {
                String selectedTariff = textValue;
                saveUserToDatabase(name1, lastname1, surname1, phone1, selectedAddress, selectedTariff, cabelText);
                Toast.makeText(SecondActivity.this, "Информация сохранена", Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTv.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCity = (String) parent.getItemAtPosition(position);
            updateInformation(selectedCity);
        });

        autoCompleteTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}


            @Override
            public void afterTextChanged(Editable s) {
                updateInformation(s.toString());
            }
        });

    }

    private void updateInformation(String address) {

        selectedAddress = address;
        if(address == null || address.isEmpty())
        {
            activeTv1.setText(null);
            activeTv2.setText(null);
            advanceTv1.setText(null);
            advanceTv2.setText(null);
            modernTv1.setText(null);
            modernTv2.setText(null);
            cabelTv2.setText(null);

        }
       else if ((address.equals("Уфа")) || (address.equals("Уфа ")) || (address.equals("Уфа,"))) {
            tvName2.setText("Продвинутый");
            tvName3.setText("Современный");
            activeTv1.setText("450" + getResources().getString(R.string.r_month_text));
            activeTv2.setText("75" + getResources().getString(R.string.mbit_sec_text));
            advanceTv1.setText("560" + getResources().getString(R.string.r_month_text));
            advanceTv2.setText("200" + getResources().getString(R.string.mbit_sec_text));
            modernTv1.setText("640" + getResources().getString(R.string.r_month_text));
            modernTv2.setText("400" + getResources().getString(R.string.mbit_sec_text));
            cabelTv2.setText("200" + getResources().getString(R.string.r_month_text));
            i = true;

        }  else if ((address.equals("Стерлитамак")) || (address.equals("Стерлитамак ")) || (address.equals("Стерлитамак,"))) {
            tvName2.setText("Позитивный");
            tvName3.setText("Улетный");
            activeTv1.setText("410" + getResources().getString(R.string.r_month_text));
            activeTv2.setText("100" + getResources().getString(R.string.mbit_sec_text));
            advanceTv1.setText("530" + getResources().getString(R.string.r_month_text));
            advanceTv2.setText("400" + getResources().getString(R.string.mbit_sec_text));
            modernTv1.setText("650" + getResources().getString(R.string.r_month_text));
            modernTv2.setText("800" + getResources().getString(R.string.mbit_sec_text));
            cabelTv2.setText("200" + getResources().getString(R.string.r_month_text));
            i = true;
        }
    }
    private void saveUserToDatabase(String name, String lastname, String surname, String phone, String address, String tariff, String cabel) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("users");
        // Создаем объект пользователя с адресом
        User user = new User(name, lastname, surname, phone, address, tariff, cabel);
        // Получаем уникальный идентификатор для нового пользователя
        String userId = usersRef.push().getKey();
        // Сохраняем данные пользователя в базу данных
        usersRef.child(userId).setValue(user)
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(SecondActivity.this, "Информация сохранена", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SecondActivity.this, "Ошибка: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }



    public void showUsersActivity()
    {
        Intent intent = new Intent(SecondActivity.this, UsersActivity.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SecondActivity.this, FirstActivity.class);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
}