package ru.otus.atm;

import ru.otus.banknote.Nominal;

// ячейка банкомата
public class Cell {

    private int capacity; //ёмкость - макс.число купюр
    private int banknoteCount; // число банкнот в ячейке
    private Nominal nominal;

    public Cell(Nominal nominal, int capacity) {
        this.nominal = nominal;
        this.capacity = capacity;
    }

    protected Nominal getNominal() {
        return nominal;
    }

    protected int getCapacity() {
        return capacity;
    }

    protected int getBanknoteCount() {
        return banknoteCount;
    }

    protected void setBanknoteCount(int banknoteCount) {
        this.banknoteCount = banknoteCount;
    }

    protected void putBanknotes(int banknotesToPutCount) {
        if (canReceive(banknotesToPutCount)) {
            banknoteCount += banknotesToPutCount;
        }
    }

    protected void getBanknotes(int banknotesToGetCount) {
        if (canProvide(banknotesToGetCount)) {
            banknoteCount -= banknotesToGetCount;
        }
    }

    protected boolean canProvide(int banknotesToGetCount) {
        return banknoteCount >= banknotesToGetCount;
    }

    protected boolean canReceive(int banknotesToPutCount) {
        return banknoteCount + banknotesToPutCount <= capacity;
    }
}
