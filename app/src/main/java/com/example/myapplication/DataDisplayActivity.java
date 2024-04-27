package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_display);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView dataTextView = findViewById(R.id.dataTextView);
        Button buttonDeleteAll = findViewById(R.id.buttonDeleteAll);

        SharedPreferences sharedPreferences = getSharedPreferences("AppData", MODE_PRIVATE);
        String existingData = sharedPreferences.getString("data", "[]");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        ArrayList<String> data = gson.fromJson(existingData, type);

        StringBuilder displayText = new StringBuilder();
        for (int i = 0; i < data.size(); i++) {
            displayText.append(i).append(", ").append(data.get(i)).append("\n");
        }

        if (data.isEmpty()) {
            displayText.append(getString(R.string.no_data));
        }

        dataTextView.setText(displayText.toString());

        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("data");
                editor.apply();
                Toast.makeText(DataDisplayActivity.this, "Всі дані видалено", Toast.LENGTH_SHORT).show();
                dataTextView.setText(R.string.no_data);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
