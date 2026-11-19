package com.pbd.vocabury.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pbd.vocabury.R;
import com.pbd.vocabury.adapter.LanguageAdapter;
import com.pbd.vocabury.database.LanguageDAO;
import com.pbd.vocabury.model.Language;
import com.pbd.vocabury.utils.AppPreferences;

public class LanguageManagerActivity extends AppCompatActivity {

    private EditText editLanguage;

    private Button buttonAdd;

    private RecyclerView recyclerLanguages;

    private LanguageAdapter adapter;

    private LanguageDAO languageDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_manager);

        editLanguage = findViewById(R.id.editLanguage);
        buttonAdd = findViewById(R.id.buttonAdd);
        recyclerLanguages = findViewById(R.id.recyclerLanguages);

        languageDAO = new LanguageDAO(this);

        recyclerLanguages.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerLanguages.addItemDecoration(
                new DividerItemDecoration(
                        this,
                        DividerItemDecoration.VERTICAL));

        adapter = new LanguageAdapter(languageDAO.getLanguages(), language -> {

            AppPreferences preferences =
                    new AppPreferences(this);

            preferences.setCurrentLanguageId(
                    language.getId());

            finish();

        });

        recyclerLanguages.setAdapter(adapter);
        adapter.setOnItemLongClickListener(this::showDeleteDialog);

        buttonAdd.setOnClickListener(v -> {

            String name = editLanguage.getText().toString().trim();

            if (name.isEmpty())
                return;

            Language language = new Language(name);

            languageDAO.addLanguage(language);

            editLanguage.setText("");

            loadLanguages();

        });

    }

    private void loadLanguages(){

        adapter.updateData(languageDAO.getLanguages());

    }

    private void showDeleteDialog(Language language){

        new AlertDialog.Builder(this)

                .setTitle("Eliminar idioma")

                .setMessage(
                        "¿Quieres eliminar el idioma \"" +
                                language.getName() +
                                "\"?\n\nTambién se eliminarán todas las palabras asociadas.")

                .setNegativeButton("Cancelar", null)

                .setPositiveButton("Eliminar", (dialog, which) -> {

                    languageDAO.deleteLanguage(language.getId());

                    loadLanguages();

                    Toast.makeText(
                            this,
                            "Idioma eliminado",
                            Toast.LENGTH_SHORT).show();

                })

                .show();

    }

}
