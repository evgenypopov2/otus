package ru.otus.serialization;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SuperJsonRunner {

    private static final String STR_TEMPLATE = " abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ,.!?;:-'";

    public static void main(String[] args) throws IllegalAccessException {

        SuperJson superJson = new SuperJson();

        CoolObject coolObject = new CoolObject();
        coolObject.setBoolAttr(getRandomBoolean());
        coolObject.setIntAttr(getRandomInt());
        coolObject.setLongAttr(getRandomLong());
        coolObject.setCharAttr(getRandomChar());
        coolObject.setStringAttr("This is Cool Object");

        coolObject.setBoolArray(getRandomBooleanArray(10));
        coolObject.setIntArray(getRandomIntArray(15));
        coolObject.setLongArray(getRandomLongArray(20));
        coolObject.setStringArray(getRandomStringArray(10));

        coolObject.setBooleanCollection(getRandomBooleanList(20));
        coolObject.setIntegerCollection(getRandomIntList(15));
        coolObject.setLongCollection(getRandomLongList(10));
        coolObject.setStringCollection(getRandomStringCollection(16));


        String superJsonString = superJson.toJson(coolObject);
        System.out.println(superJsonString);

        Gson gson = new Gson();
        String gsonString = gson.toJson(coolObject);
        System.out.println(gsonString);

        System.out.println(gsonString.equals(superJsonString));
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
