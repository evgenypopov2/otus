package ru.otus.cell;

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

    public Nominal getNominal() {
        return nominal;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getBanknoteCount() {
        return banknoteCount;
    }

    public void putBanknotes(int banknotesToPutCount) {
        if (canReceive(banknotesToPutCount)) {
            banknoteCount += banknotesToPutCount;
        }
    }

    public void getBanknotes(int banknotesToGetCount) {
        if (canProvide(banknotesToGetCount)) {
            banknoteCount -= banknotesToGetCount;
        }
    }

    public boolean canProvide(int banknotesToGetCount) {
        return banknoteCount >= banknotesToGetCount;
    }

    public boolean canReceive(int banknotesToPutCount) {
        return banknoteCount + banknotesToPutCount <= capacity;
    }
}
