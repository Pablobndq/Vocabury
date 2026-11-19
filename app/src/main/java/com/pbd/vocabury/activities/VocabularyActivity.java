package com.pbd.vocabury.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pbd.vocabury.R;
import com.pbd.vocabury.adapter.WordAdapter;
import com.pbd.vocabury.database.LanguageDAO;
import com.pbd.vocabury.database.WordDAO;
import com.pbd.vocabury.model.Language;
import com.pbd.vocabury.model.Word;
import com.pbd.vocabury.utils.AppPreferences;

public class VocabularyActivity extends AppCompatActivity {

    private TextView textLanguage;

    private EditText editSpanish;
    private EditText editForeign;

    private Button buttonSave;

    private WordDAO wordDAO;
    private LanguageDAO languageDAO;
    private AppPreferences preferences;

    private Language currentLanguage;

    private RecyclerView recyclerWords;

    private WordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vocabulary);

        textLanguage = findViewById(R.id.textLanguage);

        editSpanish = findViewById(R.id.editSpanish);
        editForeign = findViewById(R.id.editForeign);

        buttonSave = findViewById(R.id.buttonSave);

        recyclerWords = findViewById(R.id.recyclerWords);

        wordDAO = new WordDAO(this);
        languageDAO = new LanguageDAO(this);
        preferences = new AppPreferences(this);
        recyclerWords.setLayoutManager(
                new LinearLayoutManager(this));

        loadCurrentLanguage();

        adapter = new WordAdapter(
                wordDAO.getWordsByLanguage(
                        currentLanguage.getId()));

        adapter.setOnItemLongClickListener(this::showDeleteDialog);

        recyclerWords.setAdapter(adapter);

        loadWords();

        buttonSave.setOnClickListener(v -> saveWord());

    }

    private void loadCurrentLanguage() {

        int id = preferences.getCurrentLanguageId();

        currentLanguage = languageDAO.getLanguageById(id);

        if (currentLanguage == null) {

            Toast.makeText(
                    this,
                    "Primero debes seleccionar un idioma",
                    Toast.LENGTH_LONG).show();

            finish();

            return;

        }

        if (currentLanguage != null) {

            textLanguage.setText(
                    "Idioma: " + currentLanguage.getName());

            editForeign.setHint(currentLanguage.getName());

        }

    }

    private void saveWord() {

        String spanish = editSpanish.getText().toString().trim();

        String foreign = editForeign.getText().toString().trim();

        if (spanish.isEmpty() || foreign.isEmpty()) {

            Toast.makeText(
                    this,
                    "Rellena ambos campos",
                    Toast.LENGTH_SHORT).show();

            return;

        }

        Word word = new Word(
                currentLanguage.getId(),
                spanish,
                foreign);

        wordDAO.addWord(word);


        loadWords();

        editSpanish.setText("");
        editForeign.setText("");

        editSpanish.requestFocus();

        Toast.makeText(
                this,
                "Palabra guardada",
                Toast.LENGTH_SHORT).show();

    }

    private void loadWords(){

        adapter.updateData(
                wordDAO.getWordsByLanguage(
                        currentLanguage.getId()));

    }

    private void showDeleteDialog(Word word){

        new androidx.appcompat.app.AlertDialog.Builder(this)

                .setTitle("Eliminar palabra")

                .setMessage(
                        "¿Quieres eliminar la palabra:\n\n" +
                                word.getSpanish() +
                                " → " +
                                word.getForeign() + "?")

                .setNegativeButton(
                        "Cancelar",
                        null)

                .setPositiveButton(
                        "Eliminar",
                        (dialog, which) -> {

                            wordDAO.deleteWord(word.getId());

                            loadWords();

                            Toast.makeText(
                                    this,
                                    "Palabra eliminada",
                                    Toast.LENGTH_SHORT).show();

                        })

                .show();

    }

}
