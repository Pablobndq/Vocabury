package com.pbd.vocabury.game;

public final class GameManager {

    private static GameSession currentGame;

    private GameManager(){}

    public static void startGame(GameSession session){

        currentGame = session;

    }

    public static GameSession getCurrentGame(){

        return currentGame;

    }

    public static void finishGame(){

        currentGame = null;

    }

}