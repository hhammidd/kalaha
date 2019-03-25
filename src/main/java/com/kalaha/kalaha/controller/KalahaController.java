package com.kalaha.kalaha.controller;


import com.kalaha.kalaha.model.GameStarter;
import com.kalaha.kalaha.service.KalahaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class KalahaController {

    @Autowired
    private KalahaService kalahaService;

    private GameStarter gameStarter;

    @GetMapping("/create")
    public GameStarter createGame(){
        gameStarter = kalahaService.createGame();
        return gameStarter;
    }


    @GetMapping("/move")
    public GameStarter move(@RequestParam int pitId, GameStarter gameStarter){
        //TODO delete below after making controller
        int setPitId = pitId;
        gameStarter = kalahaService.startGame(setPitId, gameStarter);
        //TODO
        return gameStarter;
    }
}
