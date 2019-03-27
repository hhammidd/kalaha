package com.kalaha.kalaha.model;

import com.kalaha.kalaha.service.ConstantsEnum;

/**
 *  Board will introduce the status of the board
 */
public class Board {
    private Pit[] pits;

    public Board() {
        this.pits = new Pit[ConstantsEnum.NO_PITS.getAmount()];
        for (int i=0; i< pits.length ; i++) {
            Pit pit = new Pit();
            pits[i] = pit;
            pits[i].setId(i);
            // TODO check the pit is kalaha
            if ((i+1) % ConstantsEnum.NO_PITS_FOR_PLAYER.getAmount() == 0) {
                pits[i].setKalaha(true);
            } else {
                pits[i].setKalaha(false);
            }
            // if it is not pit of kalaha put 6 otherwise should be 0
            if (!pits[i].isKalaha()) {
                pits[i].setStonesNo(ConstantsEnum.NO_STONES_IN_PIT.getAmount());
            } else {
                pits[i].setStonesNo(0);

            }
                //TODO delete this int value    = 100; int remainder = value % 9; reminder = 1

        }
    }

    public Pit[] getPits() {
        return pits;
    }

    public void setPits(Pit[] pits) {
        this.pits = pits;
    }

    //TODO why -> give the pit that choosen by active player and return back this pit
    public Pit getPitById(int pitId) {
        return this.getPits()[pitId];
    }
}
