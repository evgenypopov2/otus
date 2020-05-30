package ru.otus.serialization;

import lombok.Data;

import java.util.Collection;

@Data
public class CoolObject {
    private boolean boolAttr;
    private int intAttr;
    private long longAttr;
    private char charAttr;
    private String stringAttr;
    private boolean[] boolArray;
    private int[] intArray;
    private long[] longArray;
    private String[] stringArray;
    private Collection<Boolean> booleanCollection;
    private Collection<Integer> integerCollection;
    private Collection<Long> longCollection;
    private Collection<String> stringCollection;
}
