package com.kalaha.kalaha.service;


import com.kalaha.kalaha.model.Board;
import com.kalaha.kalaha.model.Pit;
import com.kalaha.kalaha.model.GameStarter;
import com.kalaha.kalaha.model.Player;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KalahaServiceImpl implements KalahaService {

    final int NO_PLAYER_PITS = 7;
    String message = "";
    int gameId = 1;
    private GameStarter gameStarter;


    // TODO make a GameStarter here that initialize before  by initializer

    @Override
    public void startGame(int selectedPitIndex) {
        //define a board
        Board boardStatus = gameStarter.getBoard();
        // take no pit you choose and take no after it ???
        // here is ready put to next pitga
        int i = selectedPitIndex + 1;
        // setPitId comes from front end --> before you had all pits info and now just this one that choosen
        Pit selectedPitInfo = gameStarter.getBoard().getPitById(selectedPitIndex);
        int noStonesInSelPit = selectedPitInfo.getStonesNo();
        Pit[] pits = gameStarter.getBoard().getPits();
        // the active player just picked all the stone so we set it as 0
        pits[selectedPitIndex].setStonesNo(0);
        Player activePlayer = gameStarter.getActivePlayer();
        while (i < pits.length && noStonesInSelPit > 0) {
            if (noStonesInSelPit == 1) {
                message = "Last stone in the current player kalaha, this player can keep going!";
                // TODO if the last stone go to own pit with no stone he takes all front pit
                if (isPlacedLastStoneOnOwnEmptyPit(activePlayer, pits[i])) {
                    Pit frontPit = collectFrontPit(gameStarter, pits[i].getId());
                    //TODO why +1 ??
                    int allStonesOnFrontPitForAdding = frontPit.getStonesNo() + 1;
                    // TODO why below
                    // here just add to two side as player number front stones
                    if (activePlayer.getPlayerNo() == 1) {
                        pits[ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount() - 1]
                                .setStonesNo(pits[ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount() - 1]
                                        .getStonesNo() + allStonesOnFrontPitForAdding);
                    } else {
                        pits[pits.length - 1].setStonesNo(pits[pits.length - 1].getStonesNo() + allStonesOnFrontPitForAdding);
                    }

                    // here set the front pit and the pit you take as zero
                    pits[frontPit.getId()].setStonesNo(0);
                    pits[pits[i].getId()].setStonesNo(0);

                    gameStarter = playerChanger(activePlayer, gameStarter);
                    break;
                    //if last seed doesn't end up in active player's Kalaha change turns
                } else if (pits[i].getId() == activePlayer.getPlayerNo() * ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount() - 1) {
                    gameStarter = playerChanger(activePlayer, gameStarter);
                }
            }
            // check that the next pitis not oppononet Kalaha
            // activePlayer.getPlayerNo()*NO_PLAYER_PITS-1 = 6 or 13
            if (!pits[i].isKalaha() || pits[i].getId() == activePlayer.getPlayerNo() * ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount() - 1) {
                // here add one to next pit
                pits[i].setStonesNo(pits[i].getStonesNo() + 1);
                noStonesInSelPit--;
            }
            // TODO change the below explanation ???
            // if we reached the end of the board and still have seeds restart from first pit
            if (i == pits.length - 1 && noStonesInSelPit > 0) {
                i = 0;
            } else {
                i++;
            }

        }
        boardStatus.setPits(pits);
        //give you below all stones remail in each player pits (except kalaha)
        int noPlayerOneStones = totalPlayersStones(boardStatus, gameStarter.getPlayerOne());
        int noPlayerTwoStones = totalPlayersStones(boardStatus, gameStarter.getPlayerTwo());

        if (noPlayerOneStones == 0 || noPlayerTwoStones == 0) {
            gameStarter = gameOver(gameStarter, pits, noPlayerOneStones, noPlayerTwoStones);
        }

        gameStarter.setMessage(message);
        gameStarter.setBoard(boardStatus);
        //return gameStarter;
    }

    @Override
    public void createGame() {
        gameStarter = new GameStarter();
    }

    private GameStarter gameOver(GameStarter gameStarter, Pit[] pits, int noPlayerOneSeeds, int noPlayerTwoSeeds) {
        Pit playerOneKalaha = pits[ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount() - 1];
        Pit playerTwoKalaha = pits[pits.length - 1];
        int noPlayerOneStones = playerOneKalaha.getStonesNo() + noPlayerOneSeeds;
        int noPlayerTwoStones = playerTwoKalaha.getStonesNo() + noPlayerTwoSeeds;
        if (noPlayerOneStones > noPlayerTwoStones) {
            message = "Game Finished! Player one has " + noPlayerOneStones + " stones and player two ha " + noPlayerTwoStones
                    + " , Player one wins";
        } else if (noPlayerOneStones < noPlayerTwoStones) {
            message = "Game Finished! Player one has " + noPlayerOneStones + " stones and player two ha " + noPlayerTwoStones
                    + " , Player Two wins";
        } else {
            message = "Finished Game! It's draw";
        }
        return gameStarter;
    }

    /**
     * @param board
     * @param player
     * @return total Number of stones remain in each player pits(except kalaha)
     */
    private int totalPlayersStones(Board board, Player player) {
        int totalNoStonesPlayer = 0;
        List<Pit> pitsForEachPlayer = getPlayersPitsNo(board, player);
        for (Pit pit : pitsForEachPlayer) {
            totalNoStonesPlayer += pit.getStonesNo();
        }
        return totalNoStonesPlayer;
    }

    private List<Pit> getPlayersPitsNo(Board board, Player player) {
        List<Pit> pitList = new ArrayList<>();
        Pit[] totPits = board.getPits();
        if (player.getPlayerNo() == 1) {
            // TODO why?
            for (int i = 0; i < ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount() - 2; i++) {
                pitList.add(totPits[i]);
            }
        } else {

            for (int i = ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount(); i < ConstantsEnum.NO_PITS.getAmount() - 1; i++) {
                pitList.add(totPits[i]);
            }
        }
        return pitList;
    }


    private GameStarter playerChanger(Player activePlayer, GameStarter gameStarter) {
        if (activePlayer.getPlayerNo() == 1) {
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
     *
     * @param gameStarter
     * @param setPitId
     * @return pit on not active player side in front of other pit
     */
    private Pit collectFrontPit(GameStarter gameStarter, int setPitId) {
        return gameStarter.getBoard().getPitById(ConstantsEnum.NO_PITS.getAmount() - 2 - setPitId);
    }

    private boolean isPlacedLastStoneOnOwnEmptyPit(Player activePlayer, Pit pit) {
        return !(pit.getId() == activePlayer.getPlayerNo() * ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount() - 1) && checkActivePlayerOwnPit(activePlayer, pit.getId())
                && pit.getStonesNo() == 0;

    }

    // TODO what is doing below
    public boolean isActivePlayerKalaha(Player activePlayer, int selectedPitIndex) {
        return selectedPitIndex == activePlayer.getPlayerNo() * ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount() - 1;
    }

    private boolean checkActivePlayerOwnPit(Player activePlayer, int selectedPitIndex) {
        return ((activePlayer.getPlayerNo() == 1 && selectedPitIndex < ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount()) ||
                (activePlayer.getPlayerNo() == 2 && selectedPitIndex > ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount()));
    }
}
