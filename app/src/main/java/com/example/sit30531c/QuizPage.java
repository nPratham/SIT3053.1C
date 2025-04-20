package com.example.sit30531c;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class QuizPage extends AppCompatActivity implements View.OnClickListener {

    TextView questionTextView;
    TextView name_input;

    Button ansA, ansB, ansC, ansD;
    Button submit;

    private int currentprogress = 0;
    private ProgressBar progressbar;

    int score = 0;
    int totalQuestion = QuestionAnswer.question.length;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    boolean isAnswered = false; // 🟢 Track if user has already submitted an answer

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizpage);

        name_input = findViewById(R.id.name_input);
        String userName = getIntent().getStringExtra("userName");
        name_input.setText("Welcome " + userName);

        questionTextView = findViewById(R.id.question);
        ansA = findViewById(R.id.answer_A);
        ansB = findViewById(R.id.answer_B);
        ansC = findViewById(R.id.answer_C);
        ansD = findViewById(R.id.answer_D);
        submit = findViewById(R.id.submit);

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        submit.setOnClickListener(this);

        loadNewQuestion();
        progressbar = findViewById(R.id.progressbar);
        progressbar.setMax(totalQuestion); // Assuming 3 questions
        progressbar.setProgress(currentprogress); // Starts at 0

    }




    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.submit) {
            if (!isAnswered) {
                if (selectedAnswer.isEmpty()) return;

                String correctAnswer = QuestionAnswer.correctAnswer[currentQuestionIndex];

                if (selectedAnswer.equals(correctAnswer)) {
                    score++;
                }

                // Highlight correct and wrong answers
                markAnswer(correctAnswer, Color.GREEN);
                if (!selectedAnswer.equals(correctAnswer)) {
                    markAnswer(selectedAnswer, Color.RED);
                }

                // Disable options
                setOptionsEnabled(false);

                isAnswered = true;
                submit.setText("Next");
                currentprogress++;
                progressbar.setProgress(currentprogress);

            } else {
                // Move to next question
                currentQuestionIndex++;
                if (currentQuestionIndex < totalQuestion) {
                    loadNewQuestion();
                } else {
                    finishQuiz();
                }
            }

        } else {
            // Answer button clicked
            resetButtonColors();
            Button clicked = (Button) view;
            selectedAnswer = clicked.getText().toString();
            clicked.setBackgroundColor(Color.MAGENTA);
        }


    }

    private void loadNewQuestion() {
        questionTextView.setText(QuestionAnswer.question[currentQuestionIndex]);
        ansA.setText(QuestionAnswer.choices[currentQuestionIndex][0]);
        ansB.setText(QuestionAnswer.choices[currentQuestionIndex][1]);
        ansC.setText(QuestionAnswer.choices[currentQuestionIndex][2]);
        ansD.setText(QuestionAnswer.choices[currentQuestionIndex][3]);

        // Reset everything
        selectedAnswer = "";
        isAnswered = false;
        resetButtonColors();
        setOptionsEnabled(true);
        submit.setText("Submit");
    }

    private void resetButtonColors() {
        ansA.setBackgroundColor(getResources().getColor(R.color.white));
        ansB.setBackgroundColor(getResources().getColor(R.color.white));
        ansC.setBackgroundColor(getResources().getColor(R.color.white));
        ansD.setBackgroundColor(getResources().getColor(R.color.white));
    }

    private void setOptionsEnabled(boolean enabled) {
        ansA.setEnabled(enabled);
        ansB.setEnabled(enabled);
        ansC.setEnabled(enabled);
        ansD.setEnabled(enabled);
    }

    private void markAnswer(String answer, int color) {
        if (ansA.getText().toString().equals(answer)) ansA.setBackgroundColor(color);
        if (ansB.getText().toString().equals(answer)) ansB.setBackgroundColor(color);
        if (ansC.getText().toString().equals(answer)) ansC.setBackgroundColor(color);
        if (ansD.getText().toString().equals(answer)) ansD.setBackgroundColor(color);
    }

    private void finishQuiz() {
        String passStatus = score > totalQuestion * 0.6 ? "Passed" : "Failed";

        new AlertDialog.Builder(this)
                .setTitle(passStatus)
                .setMessage("Score is " + score + " out of " + totalQuestion)
                .setPositiveButton("Restart", (dialog, which) -> restartQuiz())
                .setCancelable(false)
                .show();
    }

    private void restartQuiz() {
        score = 0;
        currentQuestionIndex = 0;
        loadNewQuestion();
        currentprogress = 0;
        progressbar.setProgress(currentprogress);

    }
}

