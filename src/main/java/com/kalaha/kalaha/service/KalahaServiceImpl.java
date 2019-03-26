package com.kalaha.kalaha.service;


import com.kalaha.kalaha.model.Board;
import com.kalaha.kalaha.model.Pit;
import com.kalaha.kalaha.model.GameStarter;
import com.kalaha.kalaha.model.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class KalahaServiceImpl implements KalahaService {

    String message = "";
    int gameId = 1;
    private GameStarter gameStarter;


    // TODO make a GameStarter here that initialize before  by initializer

    @Override
    public void startGame(int choosenPitId) {
        //define a board
        Board boardStatus = gameStarter.getBoard();
        // take no pit you choose and take no after it ???
        // here is ready put to next pitga
        int i = choosenPitId + 1;
        // setPitId comes from front end --> before you had all pits info and now just this one that choosen
        Pit choosenPit = gameStarter.getBoard().getPitById(choosenPitId);
        int noStonesFromChoosenPit = choosenPit.getStonesNo();
        Pit[] pits = gameStarter.getBoard().getPits();
        // the active player just picked all the stone so we set it as 0
        pits[choosenPitId].setStonesNo(0);
        Player activePlayer = gameStarter.getActivePlayer();
        while (i< 14 && noStonesFromChoosenPit > 0) {
            if (noStonesFromChoosenPit == 1) {
                message = "Last seed in the current player kalah, this player can keep going!" ;
                // TODO if the last seed go to own pit with no seed
                if (isPlacedLastSeedOnOwnEmptyPit(activePlayer, pits[i])) {
                    Pit frontPit = collectFrontPit(gameStarter, pits[i].getId());
                    //TODO why +1 ??
                    int allSeedsOnFrontPitForAdding = frontPit.getStonesNo() +1;
                    // TODO why below
                    // here just add to two side as player number front stones
                    if (activePlayer.getPlayerNo() == 1) {
                        pits[7-1].setStonesNo(pits[7-1].getStonesNo() + allSeedsOnFrontPitForAdding);
                    } else {
                        pits[14 -1].setStonesNo(pits[14-1].getStonesNo() + allSeedsOnFrontPitForAdding);
                    }

                    // here set the front pit and the pit you take as zero
                    pits[frontPit.getId()].setStonesNo(0);
                    pits[pits[i].getId()].setStonesNo(0);

                    gameStarter = playerChanger(activePlayer,gameStarter);
                    break;
                    //if last seed doesn't end up in active player's Kalaha change turns
                } else if (pits[i].getId() == activePlayer.getPlayerNo()*7-1){
                    gameStarter = playerChanger(activePlayer, gameStarter);
                }
            }
            // check that the next pitis not oppononet Kalaha
            // activePlayer.getPlayerNo()*7-1 = 6 or 13
            if (!pits[i].isKalaha() || pits[i].getId() == activePlayer.getPlayerNo()*7-1 ){
                // here add one to next pit
                pits[i].setStonesNo(pits[i].getStonesNo()+1);
                noStonesFromChoosenPit--;
            }
            // TODO change the below explanation ???
            // if we reached the end of the board and still have seeds restart from first pit
            if (i == 14-1 && noStonesFromChoosenPit>0) {
                i = 0;
            } else {
                i++;
            }

        }
        boardStatus.setPits(pits);
        //give you below all stones remail in each player pits (except kalaha)
        int noPlayerOneStones = totalPlayersStones(boardStatus, gameStarter.getPlayerOne());
        int noPlayerTwoStones = totalPlayersStones(boardStatus, gameStarter.getPlayerTwo());

        if (noPlayerOneStones ==0 || noPlayerTwoStones == 0){
            gameStarter = gameOver(gameStarter, pits, noPlayerOneStones, noPlayerTwoStones);
        }

        gameStarter.setMessage(message);
        gameStarter.setBoard(boardStatus);
        //return gameStarter;
    }

    @Override
    public void createGame() {
         gameStarter = new GameStarter();
        //return gameStarter;
    }

    private GameStarter gameOver(GameStarter gameStarter, Pit[] pits, int noPlayerOneSeeds, int noPlayerTwoSeeds) {
        Pit playerOneKalaha = pits[7-1];
        Pit playerTwoKalaha = pits[14-1];
        int noPlayerOneStones = playerOneKalaha.getStonesNo() + noPlayerOneSeeds;
        int noPlayerTwoStones = playerTwoKalaha.getStonesNo() + noPlayerTwoSeeds;
        if (noPlayerOneStones > noPlayerTwoStones) {
            message = "Game Finished! Player one has " + noPlayerOneStones + " stones and player two ha " + noPlayerTwoStones
            + " , Player one wins";
        } else if (noPlayerOneStones < noPlayerTwoStones){
            message = "Game Finished! Player one has " + noPlayerOneStones + " stones and player two ha " + noPlayerTwoStones
                    + " , Player Two wins";
        } else {
            message = "Finished Game! It's draw" ;
        }
        return gameStarter;
    }

    /**
     *
     * @param board
     * @param player
     * @return total Number of stones remain in each player pits(except kalaha)
     */
    private int totalPlayersStones(Board board, Player player) {
        int totalNoStonesPlayer = 0;
        List<Pit> pitsForEachPlayer = getPlayersPitsNo(board, player);
        for (Pit pit: pitsForEachPlayer){
            totalNoStonesPlayer += pit.getStonesNo();
        }
        return totalNoStonesPlayer;
    }

    private List<Pit> getPlayersPitsNo(Board board, Player player) {
        List<Pit> pitList = new ArrayList<>();
        Pit[] totPits = board.getPits();
        if (player.getPlayerNo() == 1) {
            // TODO why?
            for (int i=0; i < 7-2; i++){
                pitList.add(totPits[i]);
            }
        } else {
            for (int i= 7; i<14-1;i++){
                pitList.add(totPits[i]);
            }
        }
        return pitList;
    }


    private GameStarter playerChanger(Player activePlayer, GameStarter gameStarter) {
        if (activePlayer.getPlayerNo() == 1){
            gameStarter.getPlayerOne().setActive(false);
            gameStarter.getPlayerTwo().setActive(true);
        } else {
            gameStarter.getPlayerOne().setActive(true);
            gameStarter.getPlayerTwo().setActive(false);
        }

        // TODO change this part
        message = "active player changed";
        gameStarter.setMessage(message);
        return gameStarter;

    }

    /**
     * this is
     * @param gameStarter
     * @param setPitId
     * @return  pit on not active player side in front of other pit
     */
    private Pit collectFrontPit(GameStarter gameStarter, int setPitId) {
        return gameStarter.getBoard().getPitById(14 -2 - setPitId);
    }

    private boolean isPlacedLastSeedOnOwnEmptyPit(Player activePlayer, Pit pit) {
        return !(pit.getId() == activePlayer.getPlayerNo()*7-1) && checkActivePlayerOwnPit(activePlayer, pit.getId())
                && pit.getStonesNo() == 0;

    }

    // TODO what is doing below
    public boolean isActivePlayerKalaha(Player activePlayer, int choosedId){
        return choosedId == activePlayer.getPlayerNo()*7-1;
    }

    private boolean checkActivePlayerOwnPit(Player activePlayer, int choosenPiId) {
        return ((activePlayer.getPlayerNo() == 1 && choosenPiId < 7) ||
                (activePlayer.getPlayerNo() == 2 && choosenPiId > 7));
    }
}
