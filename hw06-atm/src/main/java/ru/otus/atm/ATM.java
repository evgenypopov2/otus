package ru.otus.atm;

import ru.otus.banknote.Nominal;
import ru.otus.cell.Cell;
import ru.otus.exception.CannotProvideRequestedSumException;
import ru.otus.exception.NotEnoughSpaceException;

import java.util.*;
import java.util.stream.Stream;

public class ATM {

    private static final int CELLS_CAPACITY = 1000;

    private List<Cell> cells = new ArrayList<>();

    public ATM() {
        // sort by nominal value desc
        Stream.of(Nominal.values())
                .sorted(Comparator.comparingInt(Nominal::getValue).reversed())
                .forEach(nominal -> cells.add(new Cell(nominal, CELLS_CAPACITY)));
    }

    public void putMoney(Map<Nominal, Integer> moneyPack) throws NotEnoughSpaceException {

        for (Cell cell: cells) {   // check possibility first
            Integer banknotesNumber = moneyPack.get(cell.getNominal());
            if (banknotesNumber != null && banknotesNumber > 0 &&!cell.canReceive(banknotesNumber)) {
                throw new NotEnoughSpaceException("No space to put banknotes: " + cell.getNominal());
            }
        }
        // start transaction
        cells.forEach(cell -> {
            Integer banknotesNumber = moneyPack.get(cell.getNominal());
            if (banknotesNumber != null && banknotesNumber > 0) {
                cell.putBanknotes(banknotesNumber);
            }
        });
        // commit transaction
    }

    public void getMoney(int sum0) throws CannotProvideRequestedSumException {

        int[] sum = new int[1];
        sum[0] = sum0;
        var moneyPack = new EnumMap<Nominal, Integer>(Nominal.class);

        cells.forEach(cell -> {    // check possibility first
            int nominal = cell.getNominal().getValue();

            if (nominal <= sum[0] && cell.getBanknoteCount() > 0) {
                int banknotes = Math.min(sum[0] / nominal, cell.getBanknoteCount());
                moneyPack.put(cell.getNominal(), banknotes);
                sum[0] -= (banknotes * nominal);
            }
        });

        if (sum[0] > 0) {
            throw new CannotProvideRequestedSumException("Can not provide requested sum " + sum0);
        }

        // start transaction
        cells.forEach(cell -> {
            Integer banknotes = moneyPack.get(cell.getNominal());
            if (banknotes != null && banknotes > 0) {
                cell.getBanknotes(banknotes);
            }
        });
        // commit transaction
    }

    public String getCellsState() {

        StringBuilder stringBuilder = new StringBuilder("[");

        cells.forEach(cell -> stringBuilder
                .append(stringBuilder.length() > 1 ? ", " : "")
                .append(cell.getNominal().getValue())
                .append(": ")
                .append(cell.getBanknoteCount()));

        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public int getRemainedSum() {
        return cells.stream()
                .mapToInt(cell -> cell.getNominal().getValue() * cell.getBanknoteCount())
                .reduce(Integer::sum).orElse(0);
    }
}
