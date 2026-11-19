package com.pbd.vocabury.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.pbd.vocabury.R;
import com.pbd.vocabury.database.DatabaseHelper;
import com.pbd.vocabury.database.LanguageDAO;
import com.pbd.vocabury.model.Language;
import com.pbd.vocabury.utils.AppPreferences;

public class MainActivity extends AppCompatActivity {

    private Button buttonLanguages;
    private Button buttonVocabulary;
    private Button buttonPlay;
    private TextView textCurrentLanguage;
    private AppPreferences preferences;
    private LanguageDAO languageDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        buttonLanguages = findViewById(R.id.buttonLanguages);
        buttonVocabulary = findViewById(R.id.buttonVocabulary);
        buttonPlay = findViewById(R.id.buttonPlay);
        textCurrentLanguage = findViewById(R.id.textCurrentLanguage);

        preferences = new AppPreferences(this);

        languageDAO = new LanguageDAO(this);

        buttonLanguages.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    LanguageManagerActivity.class);

            startActivity(intent);

        });

        buttonVocabulary.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    VocabularyActivity.class);

            startActivity(intent);

        });

        buttonPlay.setOnClickListener(v -> {

            Intent intent = new Intent(
                    MainActivity.this,
                    GameMenuActivity.class);

            startActivity(intent);

        });

    }

    @Override
    protected void onResume() {

        super.onResume();

        loadCurrentLanguage();

    }

    private void loadCurrentLanguage(){

        int id = preferences.getCurrentLanguageId();

        if(id == -1){

            textCurrentLanguage.setText(
                    "Seleccionar Idioma");

            return;

        }

        Language language = languageDAO.getLanguageById(id);

        if(language != null){

            textCurrentLanguage.setText(
                    language.getName());

        }

    }
}