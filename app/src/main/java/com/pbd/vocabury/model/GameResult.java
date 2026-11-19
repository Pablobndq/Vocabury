package com.pbd.vocabury.model;

public class GameResult {

    private final Word word;
    private final String userAnswer;
    private final String correctAnswer;
    private final boolean correct;

    public GameResult(Word word,
                      String userAnswer,
                      String correctAnswer,
                      boolean correct) {

        this.word = word;
        this.userAnswer = userAnswer;
        this.correctAnswer = correctAnswer;
        this.correct = correct;

    }

    public Word getWord() {
        return word;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public boolean isCorrect() {
        return correct;
    }

}
