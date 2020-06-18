package ru.otus.core.model;

/**
 * @author Evgeny Popov
 * created on 12.06.20.
 */
public class Account {

    @Id
    private long no;
    private String type;
    private double rest;

    public Account() {}
    public Account(long no, String type, double rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public String getType() {
        return type;
    }

    public Double getRest() {
        return rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest='" + rest + '\'' +
                '}';
    }
}
