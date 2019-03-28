package com.kalaha.kalaha.controller;


import com.kalaha.kalaha.model.GameStarter;
import com.kalaha.kalaha.model.dto.GameStatusDto;
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
    public GameStatusDto createGame(){
        return kalahaService.createGame();
    }


    @GetMapping("/move")
    public GameStatusDto move(@RequestParam int pitId){

        //TODO change the name from startGame to move
        return kalahaService.startGame(pitId);
    }
}
