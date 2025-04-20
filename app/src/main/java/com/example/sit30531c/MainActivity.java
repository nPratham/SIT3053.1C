package com.example.sit30531c;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText nameInput;
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // This is your welcome screen XML

        nameInput = findViewById(R.id.name_input);
        startButton = findViewById(R.id.startbutton);

        startButton.setOnClickListener(v -> {
            String name = nameInput.getText().toString().trim();

            if (!name.isEmpty()) {
                Intent intent = new Intent(MainActivity.this, QuizPage.class);
                intent.putExtra("userName", name);
                startActivity(intent);
            } else {
                nameInput.setError("Please enter your name");
            }
        });
    }
}


