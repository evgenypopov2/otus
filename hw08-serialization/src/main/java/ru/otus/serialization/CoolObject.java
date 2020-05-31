package ru.otus.serialization;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
public class CoolObject {

    private static final String STR_TEMPLATE = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,.!?;:-'";

    private final transient int transientInt = 333;
    public static final long longConst = 8764L;

    private boolean boolAttr;
    private int intAttr;
    private long longAttr;
    private float floatAttr;
    private double doubleAttr;
    private char charAttr;
    private String stringAttr;
    private String nullAttr = null;
    private boolean[] boolArrayAttr;
    private int[] intArrayAttr;
    private long[] longArrayAttr;
    private float[] floatArrayAttr;
    private double[] doubleArrayAttr;
    private String[] stringArrayAttr;
    private Collection<Boolean> booleanCollectionAttr;
    private Collection<Integer> integerCollectionAttr;
    private Collection<Long> longCollectionAttr;
    private Collection<Float> floatCollectionAttr;
    private Collection<Double> doubleCollectionAttr;
    private Collection<String> stringCollectionAttr;

    private CoolObject coolObjectAttr;

    //=================================

    public static CoolObject createAndRandomFillCoolObject() {

        CoolObject coolObject = new CoolObject();
        coolObject.setBoolAttr(getRandomBoolean());
        coolObject.setIntAttr(getRandomInt());
        coolObject.setLongAttr(getRandomLong());
        coolObject.setFloatAttr(getRandomFloat());
        coolObject.setDoubleAttr(getRandomDouble());
        coolObject.setCharAttr(getRandomChar());
        coolObject.setStringAttr("This is Cool Object");

        coolObject.setBoolArrayAttr(getRandomBooleanArray(5));
        coolObject.setIntArrayAttr(getRandomIntArray(5));
        coolObject.setLongArrayAttr(getRandomLongArray(5));
        coolObject.setFloatArrayAttr(getRandomFloatArray(5));
        coolObject.setDoubleArrayAttr(getRandomDoubleArray(5));
        coolObject.setStringArrayAttr(getRandomStringArray(5));

        coolObject.setBooleanCollectionAttr(getRandomBooleanList(5));
        coolObject.setIntegerCollectionAttr(getRandomIntList(5));
        coolObject.setLongCollectionAttr(getRandomLongList(5));
        coolObject.setFloatCollectionAttr(getRandomFloatList(5));
        coolObject.setDoubleCollectionAttr(getRandomDoubleList(5));
        coolObject.setStringCollectionAttr(getRandomStringCollection(5));

        return coolObject;
    }

    private static boolean getRandomBoolean() {
        return Math.random() < 0.5;
    }
    private static int getRandomInt() {
        return getRandomInt(1000);
    }
    private static int getRandomInt(int max) {
        return Math.round((float) Math.random() * max);
    }
    private static long getRandomLong() {
        return Math.round(Math.random() * 1000000000);
    }
    private static float getRandomFloat() {
        return (float) Math.floor(Math.random() * 1000);
    }
    private static double getRandomDouble() {
        return Math.floor(Math.random() * 1000000);
    }
    private static char getRandomChar() {
        return STR_TEMPLATE.charAt(getRandomInt(STR_TEMPLATE.length() - 1));
    }

    private static boolean[] getRandomBooleanArray(int length) {
        boolean[] result = new boolean[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = getRandomBoolean();
        }
        return result;
    }
    private static int[] getRandomIntArray(int length) {
        int[] result = new int[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = getRandomInt();
        }
        return result;
    }
    private static long[] getRandomLongArray(int length) {
        long[] result = new long[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = getRandomLong();
        }
        return result;
    }
    private static float[] getRandomFloatArray(int length) {
        float[] result = new float[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = getRandomFloat();
        }
        return result;
    }
    private static double[] getRandomDoubleArray(int length) {
        double[] result = new double[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = getRandomDouble();
        }
        return result;
    }
    private static List<Boolean> getRandomBooleanList(int length) {
        List<Boolean> result = new ArrayList<>();
        for (boolean val: getRandomBooleanArray(length)) {
            result.add(val);
        }
        return result;
    }
    private static List<Integer> getRandomIntList(int length) {
        List<Integer> result = new ArrayList<>();
        for (int val: getRandomIntArray(length)) {
            result.add(val);
        }
        return result;
    }
    private static List<Long> getRandomLongList(int length) {
        List<Long> result = new ArrayList<>();
        for (long val: getRandomLongArray(length)) {
            result.add(val);
        }
        return result;
    }
    private static List<Float> getRandomFloatList(int length) {
        List<Float> result = new ArrayList<>();
        for (float val: getRandomFloatArray(length)) {
            result.add(val);
        }
        return result;
    }
    private static List<Double> getRandomDoubleList(int length) {
        List<Double> result = new ArrayList<>();
        for (double val: getRandomDoubleArray(length)) {
            result.add(val);
        }
        return result;
    }
    private static String getRandomString(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(getRandomChar());
        }
        return sb.toString();
    }
    private static String[] getRandomStringArray(int length) {
        String[] result = new String[length];
        for (int i = 0; i < result.length; i++) {
            result[i] = getRandomString(getRandomInt(32) + 1);
        }
        return result;
    }
    private static List<String> getRandomStringCollection(int length) {
        return new ArrayList<>(Arrays.asList(getRandomStringArray(length)));
    }
}
