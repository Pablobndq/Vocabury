package com.pbd.vocabury.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pbd.vocabury.R;
import com.pbd.vocabury.adapter.GameResultAdapter;
import com.pbd.vocabury.game.GameManager;
import com.pbd.vocabury.game.GameSession;

public class GameResultActivity extends AppCompatActivity {

    private TextView textScore;
    private TextView textPercentage;
    private RecyclerView recyclerResults;
    private Button buttonPlayAgain;
    private Button buttonMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result);

        textScore = findViewById(R.id.textScore);
        textPercentage = findViewById(R.id.textPercentage);

        recyclerResults = findViewById(R.id.recyclerResults);

        buttonPlayAgain = findViewById(R.id.buttonPlayAgain);
        buttonMenu = findViewById(R.id.buttonMenu);

        GameSession session = GameManager.getCurrentGame();

        if(session == null){

            finish();

            return;

        }

        textScore.setText(
                session.getScore() +
                        " / " +
                        session.getQuestionCount());

        int percentage =
                session.getScore() * 100 /
                        session.getQuestionCount();

        textPercentage.setText(
                percentage + "%");

        recyclerResults.setLayoutManager(
                new LinearLayoutManager(this));

        recyclerResults.setAdapter(
                new GameResultAdapter(
                        session.getResults(),
                        session.getMode()));

        buttonPlayAgain.setOnClickListener(v -> {

            GameManager.finishGame();

            startActivity(
                    new Intent(
                            this,
                            GameMenuActivity.class));

            finish();

        });

        buttonMenu.setOnClickListener(v -> {

            GameManager.finishGame();

            Intent intent =
                    new Intent(
                            this,
                            MainActivity.class);

            intent.addFlags(
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);

            startActivity(intent);

            finish();

        });

    }

}
