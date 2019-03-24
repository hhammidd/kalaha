package com.kalaha.kalaha.model;

public class Pit {
    private int stonesNo;
    private int id;
    private boolean isKalaha;
    private boolean zeroSeed;

    public Pit() {
        this.stonesNo = 6;
    }

    public int getStonesNo() {
        return stonesNo;
    }

    public void setStonesNo(int stonesNo) {
        this.stonesNo = stonesNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isKalaha() {
        return isKalaha;
    }

    public void setKalaha(boolean kalaha) {
        isKalaha = kalaha;
    }

    public boolean isZeroSeed() {
        return zeroSeed;
    }

    public void setZeroSeed(boolean zeroSeed) {
        this.zeroSeed = zeroSeed;
    }
}
