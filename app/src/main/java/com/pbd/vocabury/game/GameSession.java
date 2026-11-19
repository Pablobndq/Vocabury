package com.pbd.vocabury.game;

import com.pbd.vocabury.model.GameMode;
import com.pbd.vocabury.model.GameResult;
import com.pbd.vocabury.model.Word;

import java.util.ArrayList;

public class GameSession {

    private final ArrayList<Word> words;
    private final GameMode mode;
    private int currentIndex;
    private int score;
    private final ArrayList<GameResult> results;

    public GameSession(ArrayList<Word> words,
                       GameMode mode){

        this.words = words;
        this.mode = mode;

        currentIndex = 0;
        score = 0;

        results = new ArrayList<>();

    }

    public Word getCurrentWord(){

        return words.get(currentIndex);

    }

    public GameMode getMode(){

        return mode;

    }

    public int getScore(){

        return score;

    }

    public int getCurrentQuestion(){

        return currentIndex + 1;

    }

    public int getQuestionCount(){

        return words.size();

    }

    public void addPoint() {

        score++;

    }

    public boolean nextQuestion() {

        currentIndex++;

        return currentIndex < words.size();

    }

    public String getCorrectAnswer() {

        Word word = getCurrentWord();

        if (mode == GameMode.SPANISH_TO_FOREIGN) {

            return word.getForeign();

        }

        return word.getSpanish();

    }

    public void addResult(GameResult result){

        results.add(result);

    }

    public ArrayList<GameResult> getResults(){

        return results;

    }

}
