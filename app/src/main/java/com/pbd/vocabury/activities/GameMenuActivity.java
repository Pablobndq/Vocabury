package com.pbd.vocabury.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.pbd.vocabury.R;
import com.pbd.vocabury.database.WordDAO;
import com.pbd.vocabury.model.GameMode;
import com.pbd.vocabury.model.GameModeItem;
import com.pbd.vocabury.utils.AppPreferences;
import com.pbd.vocabury.game.GameManager;
import com.pbd.vocabury.game.GameSession;
import com.pbd.vocabury.model.Word;

import java.util.ArrayList;

public class GameMenuActivity extends AppCompatActivity {

    private Spinner spinnerMode;
    private Spinner spinnerQuestions;
    private Button buttonStart;
    private WordDAO wordDAO;
    private int languageId;
    private int totalWords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_menu);

        wordDAO = new WordDAO(this);

        AppPreferences preferences =
                new AppPreferences(this);

        languageId = preferences.getCurrentLanguageId();
        totalWords = wordDAO.getWordCountByLanguage(languageId);

        spinnerMode = findViewById(R.id.spinnerMode);
        spinnerQuestions = findViewById(R.id.spinnerQuestions);
        buttonStart = findViewById(R.id.buttonStart);


        ArrayList<GameModeItem> modes = new ArrayList<>();

        modes.add(
                new GameModeItem(
                        getString(R.string.spanish_to_foreign),
                        GameMode.SPANISH_TO_FOREIGN));

        modes.add(
                new GameModeItem(
                        getString(R.string.foreign_to_spanish),
                        GameMode.FOREIGN_TO_SPANISH));

        ArrayAdapter<GameModeItem> modeAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        modes);

        modeAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);

        spinnerMode.setAdapter(modeAdapter);


        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(

                        this,

                        android.R.layout.simple_spinner_item,

                        buildQuestionOptions(totalWords));

        adapter.setDropDownViewResource(

                android.R.layout.simple_spinner_dropdown_item);

        spinnerQuestions.setAdapter(adapter);

        buttonStart.setOnClickListener(v -> {

            GameModeItem selectedMode =
                    (GameModeItem) spinnerMode.getSelectedItem();

            GameMode mode = selectedMode.getMode();

            String selectedQuestions =
                    spinnerQuestions.getSelectedItem().toString();

            int questions;

            if(selectedQuestions.contains("Todas")){

                questions = totalWords;

            }else{

                questions = Integer.parseInt(selectedQuestions);

            }

            ArrayList<Word> words =
                    wordDAO.getRandomWordsByLanguage(
                            languageId,
                            questions);

            GameSession session =
                    new GameSession(words, mode);

            GameManager.startGame(session);

            Intent intent = new Intent(
                    GameMenuActivity.this,
                    GameActivity.class);

            startActivity(intent);

        });

    }

    private ArrayList<String> buildQuestionOptions(int totalWords){

        ArrayList<String> options =
                new ArrayList<>();

        int[] values = {5,10,20,30,50};

        for(int value : values){

            if(value < totalWords){

                options.add(String.valueOf(value));

            }

        }

        options.add(totalWords + " (Todas)");

        return options;

    }

}
