package com.kalaha.kalaha.service;


import com.kalaha.kalaha.model.GameStarter;
import com.kalaha.kalaha.model.dto.GameStatusDto;

public interface KalahaService {

    GameStatusDto startGame(int setPitId);

    GameStatusDto createGame();
}
