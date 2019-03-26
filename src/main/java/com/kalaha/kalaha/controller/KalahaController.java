package com.kalaha.kalaha.controller;


import com.kalaha.kalaha.model.GameStarter;
import com.kalaha.kalaha.service.KalahaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kalaha")
public class KalahaController {

    @Autowired
    private KalahaService kalahaService;

    //private GameStarter gameStarter;

    @GetMapping("/create")
    public String createGame(){
        //gameStarter =
        kalahaService.createGame();
        //GameStarter gameStarter = new GameStarter();
        return "tsest";
    }


    @GetMapping("/move")
    public void move(@RequestParam int pitId){
        //TODO delete below after making controller
        int setPitId = pitId;
        kalahaService.startGame(setPitId);
        //TODO
        //return gameStarter;
    }
}
