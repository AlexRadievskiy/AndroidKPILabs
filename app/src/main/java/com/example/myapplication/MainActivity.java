package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerPhoneTypes;
    private RadioGroup radioGroupBrands;
    private Button buttonOk;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerPhoneTypes = findViewById(R.id.spinnerPhoneTypes);
        radioGroupBrands = findViewById(R.id.radioGroupBrands);
        buttonOk = findViewById(R.id.buttonOk);
        textViewResult = findViewById(R.id.textViewResult);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.phone_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPhoneTypes.setAdapter(adapter);

        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedPhoneType = spinnerPhoneTypes.getSelectedItem().toString();
                int selectedBrandId = radioGroupBrands.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedBrandId);
                String selectedBrand = selectedRadioButton.getText().toString();

                textViewResult.setText("Тип: " + selectedPhoneType + ", Фірма: " + selectedBrand);
            }
        });
    }
}
