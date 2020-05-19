package ru.otus;

import ru.otus.atm.ATM;
import ru.otus.banknote.Nominal;
import ru.otus.exception.CannotProvideRequestedSumException;
import ru.otus.exception.NotEnoughSpaceException;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

public class ATMRunner {

    public static void main(String[] args) throws Exception {

        ATM atm = new ATM();

        Map<Nominal, Integer> firstLoad = new EnumMap<>(Nominal.class);
        firstLoad.put(Nominal.NOM_100, 500);
        firstLoad.put(Nominal.NOM_200, 500);
        firstLoad.put(Nominal.NOM_500, 500);
        firstLoad.put(Nominal.NOM_1000, 500);
        firstLoad.put(Nominal.NOM_2000, 500);
        firstLoad.put(Nominal.NOM_5000, 500);
        atm.putMoney(firstLoad);
        System.out.println(atm.getCellsState() + ", Remainder: " + atm.getRemainedSum());

        System.out.println("============= Fixed sum get emulation =============");
        for (int i = 0; i < 100; i++) {
            int sum = 95600;
            try {
                atm.getMoney(sum);
                System.out.println("Sum provided: " + sum);
            } catch (CannotProvideRequestedSumException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(atm.getCellsState() + ", Remainder: " + atm.getRemainedSum());
        }

        System.out.println("\n============= Fixed sum put emulation =============");
        for (int i = 0; i < 100; i++) {
            Map<Nominal, Integer> moneyPack = makeFixedMoneyPack();
            try {
                atm.putMoney(moneyPack);
                System.out.println("Money pack loaded: " + moneyPack);
            } catch (NotEnoughSpaceException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(atm.getCellsState() + ", Remainder: " + atm.getRemainedSum());
        }

        System.out.println("\n============= Randon sum get/put emulation =============");
        for (int i = 0; i < 1000; i++) {
            if (Math.random() < 0.2) {  // putting money is more rare than getting
                Map<Nominal, Integer> moneyPack = makeRandomMoneyPack();
                try {
                    atm.putMoney(moneyPack);
                    System.out.println("Money pack loaded: " + moneyPack);
                } catch (NotEnoughSpaceException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                int sum = makeRandomInt(500) * 100;
                try {
                    atm.getMoney(sum);
                    System.out.println("Sum provided: " + sum);
                } catch (CannotProvideRequestedSumException e) {
                    System.out.println(e.getMessage());
                }
            }
            System.out.println(atm.getCellsState() + ", Remainder: " + atm.getRemainedSum());
        }
    }

    private static Map<Nominal, Integer> makeRandomMoneyPack() {
        Map<Nominal, Integer> moneyPack = new EnumMap<>(Nominal.class);
        Stream.of(Nominal.values()).forEach(nominal -> moneyPack.put(nominal, makeRandomInt(10)));
        return moneyPack;
    }

    private static Map<Nominal, Integer> makeFixedMoneyPack() {
        Map<Nominal, Integer> moneyPack = new EnumMap<>(Nominal.class);
        Stream.of(Nominal.values()).forEach(nominal -> moneyPack.put(nominal, 20));
        return moneyPack;
    }

    private static int makeRandomInt(int max) {
        return Math.round((float) Math.random() * max);
    }
}

