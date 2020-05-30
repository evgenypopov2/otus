package ru.otus.banknote;

import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Stream;

public class MoneyPack {

    public static Map<Nominal, Integer> makeRandomMoneyPack(int maxBanknotes) {
        Map<Nominal, Integer> moneyPack = new EnumMap<>(Nominal.class);
        Stream.of(Nominal.values()).forEach(nominal -> moneyPack.put(nominal, makeRandomInt(maxBanknotes)));
        return moneyPack;
    }

    public static Map<Nominal, Integer> makeFixedMoneyPack(int numberOfBanknotes) {
        Map<Nominal, Integer> moneyPack = new EnumMap<>(Nominal.class);
        Stream.of(Nominal.values()).forEach(nominal -> moneyPack.put(nominal, numberOfBanknotes));
        return moneyPack;
    }

    public static int makeRandomSum(int maxSum) {
        return makeRandomInt(maxSum / 100) * 100;
    }

    public static int makeRandomInt(int max) {
        return Math.round((float) Math.random() * max);
    }
}
