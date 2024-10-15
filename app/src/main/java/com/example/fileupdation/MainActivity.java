package com.example.fileupdation;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btn,btn1;
    EditText edt;
    TextView txt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        edt = findViewById(R.id.edt);
        txt1 = findViewById(R.id.txt1);
        btn1=findViewById(R.id.btn1);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "user_file.txt");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = edt.getText().toString();

                if (content.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter some text!", Toast.LENGTH_SHORT).show();
                    return;
                }

                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "user_file.txt");

                try (FileWriter writer = new FileWriter(file, true)) {
                    writer.write(content + "\n"); // Append content to the file
                    txt1.setText("Sucessfully upload file in download folder");
                } catch (IOException e) {
                    txt1.setText("Error saving file: " + e.getMessage());
                }
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try(BufferedReader bw=new BufferedReader(new FileReader(file))){
                    String line;
                    while((line= bw.readLine())!=null){
                        txt1.setText(line);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
