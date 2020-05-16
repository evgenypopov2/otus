package ru.otus.banknote;

public enum Nominal {

    NOM_100(100),
    NOM_200(200),
    NOM_500(500),
    NOM_1000(1000),
    NOM_2000(2000),
    NOM_5000(5000);

    public final int value;

    Nominal(int val) {
        this.value = val;
    }

    public int getValue() {
        return value;
    }
}
