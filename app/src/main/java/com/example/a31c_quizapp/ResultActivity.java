package com.example.a31c_quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        TextView completionMessage = findViewById(R.id.textView7);
        TextView percentageScore = findViewById(R.id.textView9);

        Intent intent = getIntent();
        String participantName = intent.getStringExtra("name");
        String rawScore = intent.getStringExtra("score");

        completionMessage.setText("Quiz Completed " + participantName);
        percentageScore.setText((Integer.parseInt(rawScore) * 20) + "%");

        Button retryQuizButton = findViewById(R.id.button5);
        Button exitButton = findViewById(R.id.button6);

        retryQuizButton.setOnClickListener(view -> {
            Intent restartQuiz = new Intent(this, MainActivity.class);
            restartQuiz.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(restartQuiz);
        });

        exitButton.setOnClickListener(view -> {
            finishAffinity();
        });
    }
}