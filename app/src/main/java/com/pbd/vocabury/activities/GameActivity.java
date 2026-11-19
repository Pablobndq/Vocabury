package com.pbd.vocabury.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.pbd.vocabury.R;
import com.pbd.vocabury.game.GameManager;
import com.pbd.vocabury.game.GameSession;
import com.pbd.vocabury.model.GameMode;
import com.pbd.vocabury.model.Word;
import com.pbd.vocabury.model.GameResult;

public class GameActivity extends AppCompatActivity {

    private TextView textQuestionCounter;
    private TextView textQuestion;
    private EditText editAnswer;
    private Button buttonCheck;
    private GameSession session;
    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        textQuestionCounter = findViewById(R.id.textQuestionCounter);

        textQuestion = findViewById(R.id.textQuestion);

        editAnswer = findViewById(R.id.editAnswer);

        editAnswer.setOnEditorActionListener((v, actionId, event) -> {

            if (buttonCheck.isEnabled()) {

                checkAnswer();

            }

            return true;

        });

        buttonCheck = findViewById(R.id.buttonCheck);

        textResult = findViewById(R.id.textResult);

        session = GameManager.getCurrentGame();

        if(session == null){

            finish();

            return;

        }

        showQuestion();
        buttonCheck.setOnClickListener(v -> checkAnswer());

    }

    private void showQuestion(){

        Word word = session.getCurrentWord();

        textQuestionCounter.setText(
                "Pregunta " +
                        session.getCurrentQuestion() +
                        " de " +
                        session.getQuestionCount());

        if(session.getMode() == GameMode.SPANISH_TO_FOREIGN){

            textQuestion.setText(word.getSpanish());

        }else{

            textQuestion.setText(word.getForeign());

        }

    }

    private void checkAnswer() {

        String answer = editAnswer.getText().toString().trim();

        boolean correct = answer.equalsIgnoreCase(session.getCorrectAnswer());

        session.addResult(
                new GameResult(
                        session.getCurrentWord(),
                        answer,
                        session.getCorrectAnswer(),
                        correct));

        if (correct) {

            session.addPoint();

            textResult.setText("✔ ¡Correcto!");
            textResult.setTextColor(getColor(android.R.color.holo_green_dark));

        } else {

            textResult.setText(
                    "✘ Incorrecto\nRespuesta: " +
                            session.getCorrectAnswer());

            textResult.setTextColor(getColor(android.R.color.holo_red_dark));

        }

        textResult.setVisibility(View.VISIBLE);

        buttonCheck.setEnabled(false);

        new android.os.Handler().postDelayed(() -> {

            if (session.nextQuestion()) {

                editAnswer.setText("");

                textResult.setVisibility(View.INVISIBLE);

                showQuestion();

                buttonCheck.setEnabled(true);

            } else {

                finishGame();

            }

        }, 1000);

    }

    private void finishGame() {

        Intent intent = new Intent(
                GameActivity.this,
                GameResultActivity.class);

        startActivity(intent);

        finish();

    }

}