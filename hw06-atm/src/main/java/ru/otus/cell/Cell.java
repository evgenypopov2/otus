package ru.otus.cell;

import ru.otus.banknote.Nominal;
import ru.otus.exception.NotEnoughSpaceException;

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

    public boolean putBanknotes(int banknotesToPutCount) throws NotEnoughSpaceException {
        if (banknoteCount + banknotesToPutCount <= capacity) {
            banknoteCount += banknotesToPutCount;
            return true;
        }
        throw new NotEnoughSpaceException("No space to put banknotes");
    }

    public int getBanknotes(int banknotesToGetCount) {

        int banknotesProvided = banknotesToGetCount;

        if (banknoteCount >= banknotesToGetCount) {
            banknoteCount -= banknotesToGetCount;
        } else if (banknoteCount > 0) {
            banknotesProvided = banknoteCount;
            banknoteCount = 0;
        } else {
            banknotesProvided = 0;
        }
        return banknotesProvided;
    }
}
