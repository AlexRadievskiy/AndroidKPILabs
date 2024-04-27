package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class FileChooserActivity extends AppCompatActivity {

    private TextView selectedFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_chooser);

        selectedFilePath = findViewById(R.id.selectedFilePath);
        Button btnSelectFile = findViewById(R.id.btnSelectFile);

        btnSelectFile.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            Log.d("FileChooserActivity", "Launching file picker");
            filePickerLauncher.launch(intent);
        });
    }

    private final ActivityResultLauncher<Intent> filePickerLauncher = registerForActivityResult(
       new ActivityResultContracts.StartActivityForResult(),
    result -> {
        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
            Uri fileUri = result.getData().getData();
            assert fileUri != null;
            selectedFilePath.setText(fileUri.getPath());
            Intent intent = new Intent(FileChooserActivity.this, MainActivity.class);
            intent.putExtra("fileUri", fileUri.toString());
            startActivity(intent);
        }
    });
}
