package com.kalaha.kalaha.controller;


import com.kalaha.kalaha.model.GameStarter;
import com.kalaha.kalaha.service.KalahaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kalaha")
public class KalahaController {

    @Autowired
    private KalahaService kalahaService;

    //private GameStarter gameStarter;

    @GetMapping("/create")
    public String createGame(){
        kalahaService.createGame();
        return "Game strated. Platyer 1 is active";
    }


    @GetMapping("/move")
    public void move(@RequestParam int pitId){
        //TODO delete below after making controller
        int setPitId = pitId;
        kalahaService.startGame(setPitId);
        //TODO change the name from startGame to move
        //return gameStarter;
    }
}
