package ru.otus;

import ru.otus.atm.ATM;
import ru.otus.banknote.Nominal;

import java.util.EnumMap;

public class ATMRunner {

    public static void main(String[] args) throws Exception {

        ATM atm = new ATM();

        EnumMap<Nominal, Integer> firstLoad = new EnumMap<>(Nominal.class);
        firstLoad.put(Nominal.NOM_100, 500);
        firstLoad.put(Nominal.NOM_200, 500);
        firstLoad.put(Nominal.NOM_500, 500);
        firstLoad.put(Nominal.NOM_1000, 500);
        firstLoad.put(Nominal.NOM_2000, 500);
        firstLoad.put(Nominal.NOM_5000, 500);
        atm.putMoney(firstLoad);

        System.out.println(atm.getState());

        for (int i = 0; i < 1000; i++) {
            if (Math.random() < 0.2) {  // putting money is more rare than getting
                EnumMap<Nominal, Integer> moneyPack = makeRandomMoneyPack();
                atm.putMoney(moneyPack);
                System.out.println("Money pack loaded: " + moneyPack);
            } else {
                int sum = makeRandomInt(500) * 100; // <= 50000
                atm.getMoney(sum);
                System.out.println("Sum provided: " + sum);
            }
            System.out.println(atm.getState());
        }
    }

    private static EnumMap<Nominal, Integer> makeRandomMoneyPack() {

        EnumMap<Nominal, Integer> moneyPack = new EnumMap<>(Nominal.class);

        for (Nominal nominal: Nominal.values()) {
            moneyPack.put(nominal, makeRandomInt(10));
        }
        return moneyPack;
    }

    private static int makeRandomInt(int max) {
        return Math.round((float) Math.random() * max);
    }
}

