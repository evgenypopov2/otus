package ru.otus;

import com.google.common.base.CaseFormat;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import java.util.Arrays;
import java.util.List;

public class HelloOtus {

    public static void main(String... args) {
        List<String> stringList = Arrays.asList("This ", "array_list", "     for  ", null, "google_guava", "test_calls");
        System.out.println(Lists.transform(stringList, (s) -> s != null ? CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, s.trim()) : "<empty>"));
        System.out.println(Joiner.on("\t").useForNull("<empty>").join(stringList));
    }

}
