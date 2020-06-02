package ru.otus.serialization;

import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;

public class SuperJsonRunner {

    public static void main(String[] args) throws IllegalAccessException {

        SuperJson superJson = new SuperJson();
        Gson gson = new Gson();

        // primitives & arrays/collections of primitives
        compareJsons(superJson, gson, (byte)1);
        compareJsons(superJson, gson, (short)2f);
        compareJsons(superJson, gson, 3);
        compareJsons(superJson, gson, 4L);
        compareJsons(superJson, gson, 5f);
        compareJsons(superJson, gson, 6d);
        compareJsons(superJson, gson, "aaa");
        compareJsons(superJson, gson, 'b');
        compareJsons(superJson, gson, new int[] {7, 8, 9});
        compareJsons(superJson, gson, List.of(10, 11 ,12));
        compareJsons(superJson, gson, Collections.singletonList(13));

        // objects & arrays/collections of objects
        CoolObject coolObject = CoolObject.createAndRandomFillCoolObject();
        coolObject.setCoolObjectAttr(CoolObject.createAndRandomFillCoolObject());

        compareJsons(superJson, gson, coolObject);
        compareJsons(superJson, gson, new CoolObject[] {
                CoolObject.createAndRandomFillCoolObject(),
                CoolObject.createAndRandomFillCoolObject()
        });
        compareJsons(superJson, gson, Collections.singletonList(CoolObject.createAndRandomFillCoolObject()));
        compareJsons(superJson, gson, List.of(
                CoolObject.createAndRandomFillCoolObject(),
                CoolObject.createAndRandomFillCoolObject()
        ));
    }

    private static void compareJsons(SuperJson superJson, Gson gson, Object obj) throws IllegalAccessException {

        System.out.println("===========================");

        String superJsonString = superJson.toJson(obj);
        System.out.println(superJsonString);

        String gsonString = gson.toJson(obj);
        System.out.println(gsonString);

        System.out.println(gsonString.equals(superJsonString));
    }
}
