package com.example.a31c_quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        EditText et_name = findViewById(R.id.et_name);

        findViewById(R.id.button).setOnClickListener(v -> {
            if(et_name.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please enter valid information", Toast.LENGTH_SHORT).show();
            }else {
                Intent i = new Intent(MainActivity.this, QuizScreen.class);
                i.putExtra("name", et_name.getText().toString());
                startActivity(i);
            }
        });
    }
}