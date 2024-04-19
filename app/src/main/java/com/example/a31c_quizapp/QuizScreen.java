package com.example.a31c_quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class QuizScreen extends AppCompatActivity {

    TextView questionProgressTextView, questionHeader, questionTextView;
    ProgressBar questionProgressBar;
    int currentQuestionIndex = 1;
    Map<String, Map<String, Object>> quizQuestions;

    RadioGroup choicesGroup;
    RadioButton optionOne, optionTwo, optionThree;

    Button confirmButton, nextQuestionButton;

    int totalScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_screen);

        Intent receivedIntent = getIntent();
        String participantName = receivedIntent.getStringExtra("name");

        questionProgressTextView = findViewById(R.id.textView4);
        questionProgressBar = findViewById(R.id.progressBar);
        questionHeader = findViewById(R.id.textView5);
        questionTextView = findViewById(R.id.textView6);
        choicesGroup = findViewById(R.id.radioGroup);
        optionOne = findViewById(R.id.radioButton);
        optionTwo = findViewById(R.id.radioButton2);
        optionThree = findViewById(R.id.radioButton3);
        confirmButton = findViewById(R.id.button2);
        nextQuestionButton = findViewById(R.id.button3);

        quizQuestions = new HashMap<>();

        setupQuizData();

        updateQuestionDisplay();

        confirmButton.setOnClickListener(view -> handleOptionSelection());

        nextQuestionButton.setOnClickListener(view -> handleNextQuestion(participantName));
    }

    private void setupQuizData() {
        // Question 1: General Science
        Map<String, Object> question1Data = new HashMap<>();
        question1Data.put("question_title", "General Science");
        question1Data.put("question_text", "What is the chemical symbol for water?");
        String[] choices1 = {"H2O", "CO2", "O2"};
        question1Data.put("choices", choices1);
        question1Data.put("correct_choice", "H2O");
        quizQuestions.put("question1", question1Data);
        Map<String, Object> question2Data = new HashMap<>();
        question2Data.put("question_title", "World Geography");
        question2Data.put("question_text", "Which country is known as the Land of the Rising Sun?");
        String[] choices2 = {"Japan", "China", "South Korea"};
        question2Data.put("choices", choices2);
        question2Data.put("correct_choice", "Japan");
        quizQuestions.put("question2", question2Data);
        Map<String, Object> question3Data = new HashMap<>();
        question3Data.put("question_title", "History");
        question3Data.put("question_text", "Who was the first President of the United States?");
        String[] choices3 = {"Thomas Jefferson", "John Adams", "George Washington"};
        question3Data.put("choices", choices3);
        question3Data.put("correct_choice", "George Washington");
        quizQuestions.put("question3", question3Data);
        Map<String, Object> question4Data = new HashMap<>();
        question4Data.put("question_title", "Literature");
        question4Data.put("question_text", "Who wrote 'Pride and Prejudice'?");
        String[] choices4 = {"Jane Austen", "Charlotte Brontë", "Emily Dickinson"};
        question4Data.put("choices", choices4);
        question4Data.put("correct_choice", "Jane Austen");
        quizQuestions.put("question4", question4Data);
        Map<String, Object> question5Data = new HashMap<>();
        question5Data.put("question_title", "Mathematics");
        question5Data.put("question_text", "What is the value of pi up to two decimal places?");
        String[] choices5 = {"3.12", "3.14", "3.16"};
        question5Data.put("choices", choices5);
        question5Data.put("correct_choice", "3.14");
        quizQuestions.put("question5", question5Data);
    }


    private void updateQuestionDisplay() {
        questionProgressTextView.setText(((currentQuestionIndex - 1) * 20)+ "%");
        questionProgressBar.setProgress(currentQuestionIndex * 20);
        Map<String, Object> currentQuestionData = quizQuestions.get("question" + currentQuestionIndex);
        questionHeader.setText((String) currentQuestionData.get("question_title"));
        questionTextView.setText((String) currentQuestionData.get("question_text"));
        String[] choices = (String[]) currentQuestionData.get("choices");
        optionOne.setText(choices[0]);
        optionTwo.setText(choices[1]);
        optionThree.setText(choices[2]);

        nextQuestionButton.setVisibility(View.INVISIBLE);
        confirmButton.setVisibility(View.VISIBLE);
        enableChoices(true);
        choicesGroup.clearCheck();
        resetOptionBackgrounds();
    }

    private void handleOptionSelection() {
        int selectedOptionId = choicesGroup.getCheckedRadioButtonId();
        if (selectedOptionId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedOptionId);
            String selectedOption = selectedRadioButton.getText().toString();
            Map<String, Object> currentQuestionData = quizQuestions.get("question" + currentQuestionIndex);
            String correctChoice = (String) currentQuestionData.get("correct_choice");
            checkOption(selectedOption, correctChoice);
        } else {
            Toast.makeText(this, "Please select an option", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleNextQuestion(String participantName) {
        if (currentQuestionIndex == 5) {
            Intent resultsIntent = new Intent(this, ResultActivity.class);
            resultsIntent.putExtra("name", participantName);
            resultsIntent.putExtra("score", String.valueOf(totalScore));
            startActivity(resultsIntent);
        } else {
            currentQuestionIndex++;
            updateQuestionDisplay();
        }
    }

    private void checkOption(String selectedOption, String correctChoice) {
        nextQuestionButton.setVisibility(View.VISIBLE);
        confirmButton.setVisibility(View.INVISIBLE);
        if (selectedOption.equalsIgnoreCase(correctChoice)) {
            totalScore++;
            highlightChoice(selectedOption, "#90EE90");
            Toast.makeText(this, "Correct Answer!", Toast.LENGTH_SHORT).show();
        } else {
            highlightChoice(selectedOption, "#FF8488");
            highlightChoice(correctChoice, "#90EE90");
            Toast.makeText(this, "Oops! Wrong Answer", Toast.LENGTH_SHORT).show();
        }
        enableChoices(false);
    }

    private void highlightChoice(String choiceText, String colorHex) {
        RadioButton radioButton = findRadioButtonByText(choiceText);
        if (radioButton != null) {
            radioButton.setBackgroundColor(Color.parseColor(colorHex));
        }
    }

    private RadioButton findRadioButtonByText(String choiceText) {
        for (int i = 0; i < choicesGroup.getChildCount(); i++) {
            View childView = choicesGroup.getChildAt(i);
            if (childView instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) childView;
                if (radioButton.getText().toString().equals(choiceText)) {
                    return radioButton;
                }
            }
        }
        return null;
    }

    private void enableChoices(boolean isEnabled) {
        optionOne.setEnabled(isEnabled);
        optionTwo.setEnabled(isEnabled);
        optionThree.setEnabled(isEnabled);
    }

    private void resetOptionBackgrounds() {
        int color = Color.parseColor("#F3F3F3");
        optionOne.setBackgroundColor(color);
        optionTwo.setBackgroundColor(color);
        optionThree.setBackgroundColor(color);
    }
}
