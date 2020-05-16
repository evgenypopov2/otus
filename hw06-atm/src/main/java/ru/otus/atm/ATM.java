package ru.otus.atm;

import ru.otus.banknote.Nominal;
import ru.otus.cell.Cell;
import ru.otus.exception.CannotProvideRequestedSumException;
import ru.otus.exception.NotEnoughSpaceException;

import java.util.*;
import java.util.stream.Stream;

public class ATM {

    private static final int CELLS_CAPACITY = 1000;

    List<Cell> cells = new ArrayList<>();

    public ATM() {
        // sort by nominal value desc
        Stream.of(Nominal.values()).sorted(Comparator.comparingInt(Nominal::getValue).reversed()).forEach(nominal -> cells.add(new Cell(nominal, CELLS_CAPACITY)));
    }

    public void putMoney(EnumMap<Nominal, Integer> moneyPack) throws NotEnoughSpaceException {

        for (Cell cell: cells) {
            Integer banknotesNumber = moneyPack.get(cell.getNominal());
            if (banknotesNumber != null && banknotesNumber > 0) {
                cell.putBanknotes(banknotesNumber);
            }
        }
    }

    public void getMoney(int sum0) throws CannotProvideRequestedSumException {

        int sum = sum0;
        EnumMap<Nominal, Integer> moneyPack = new EnumMap<>(Nominal.class);

        for (Cell cell: cells) {    // check possibility first
            int nominal = cell.getNominal().getValue();

            if (nominal <= sum && cell.getBanknoteCount() > 0) {
                int banknotes = Math.min(sum / nominal, cell.getBanknoteCount());
                moneyPack.put(cell.getNominal(), banknotes);
                sum -= (banknotes * nominal);
//            } else {
//                moneyPack.put(cell.getNominal(), 0);
            }
        }

        if (sum > 0) {
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

    public String getState() {

        StringBuilder stringBuilder = new StringBuilder("[");
        int[] sum = new int[1];

        cells.forEach(cell -> {
            stringBuilder.append(stringBuilder.length() > 1 ? ", " : "")
                    .append(cell.getNominal().getValue()).append(": ").append(cell.getBanknoteCount());
            sum[0] += (cell.getNominal().value * cell.getBanknoteCount());
        });
        stringBuilder.append("], Remainder: ").append(sum[0]);

        return stringBuilder.toString();
    }
}
