package com.kalaha.kalaha.service;


import com.kalaha.kalaha.model.GameStarter;

public interface KalahaService {

    GameStarter startGame(int setPitId, GameStarter gameStarter);

    GameStarter createGame();
}
