package com.kalaha.kalaha.service;

public enum  ConstantsEnum {
    NO_STONES_IN_PIT(6),
    NO_PITS(14),
    NO_PITS_FOR_PLAYER(7);

    private final int amount;

    private ConstantsEnum(int pitsNumber) {
        this.amount = pitsNumber;
    }
    
    public int getAmount() {
        return amount;
    }
}
