package com.kalaha.kalaha.model;

public class GameStarter {
    private Player playerOne;
    private Player playerTwo;

    private Board board;
    private String message;

    public GameStarter() {
        this.playerOne = new Player();
        this.playerTwo = new Player();
        this.board = new Board();
        playerOne.setPlayerNo(1);
        playerTwo.setPlayerNo(2);
        playerOne.setActive(true);
        playerTwo.setActive(false);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public void setPlayerOne(Player playerOne) {
        this.playerOne = playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public void setPlayerTwo(Player playerTwo) {
        this.playerTwo = playerTwo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    //TODO what is that -> take the object of active player as I defined here first one is active
    public Player getActivePlayer() {
        return playerOne.isActive() ? playerOne : playerTwo;
    }

}
