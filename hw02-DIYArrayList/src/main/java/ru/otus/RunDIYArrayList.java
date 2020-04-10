package ru.otus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RunDIYArrayList {

    public static void main(String... args) {

        // String DIYArrayList
        DIYArrayList<String> stringDIYArrayList = new DIYArrayList<>();
        stringDIYArrayList.add(" ===================================");
        stringDIYArrayList.add("Домашнее задание");
        stringDIYArrayList.add("DIY ArrayList");
        stringDIYArrayList.add("Цель: изучить как устроена стандартная коллекция ArrayList. Попрактиковаться в создании своей коллекции.");
        stringDIYArrayList.add("Написать свою реализацию ArrayList на основе массива.");
        stringDIYArrayList.add("class DIYarrayList<T> implements List<T>{...}");
        stringDIYArrayList.add("Проверить, что на ней работают методы из java.util.Collections:");
        stringDIYArrayList.add("Collections.addAll(Collection<? super T> c, T... elements)");
        stringDIYArrayList.add("Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)");
        stringDIYArrayList.add("Collections.static <T> void sort(List<T> list, Comparator<? super T> c)");
        stringDIYArrayList.add("1) Проверяйте на коллекциях с 20 и больше элементами.");
        stringDIYArrayList.add("2) DIYarrayList должен имплементировать ТОЛЬКО ОДИН интерфейс - List.");
        stringDIYArrayList.add("3) Если метод не имплементирован, то он должен выбрасывать исключение UnsupportedOperationException.");
        stringDIYArrayList.add("Критерии оценки: Система оценки максимально соответсвует привычной школьной:");
        stringDIYArrayList.add("3 и больше - задание принято (удовлетворительно).");
        stringDIYArrayList.add("ниже - задание возвращается на доработку.");
        stringDIYArrayList.add("Задание не принимается, если основной функционал не работает или есть критические недостатки (например, copy-past кода, классы на 100500 строк, sql-конкатенация, race condition и т.д.).");
        stringDIYArrayList.add("Если все работает и критических недостатоков нет, то:");
        stringDIYArrayList.add("- 3 - можно мержить, но есть неприятные недочеты, которые хорошо бы поправить.");
        stringDIYArrayList.add("- 4 - можно мержить, но есть недочеты.");
        stringDIYArrayList.add("- 5 - можно мержить, отличная работа. (при этом могут быть мелкие субъективные шероховатости)");
        stringDIYArrayList.add("Рекомендуем сдать до: 17.04.2020");
        stringDIYArrayList.add("Статус: не сдано");
        stringDIYArrayList.add("я======================================");
        // + some lines else
        Collections.addAll(stringDIYArrayList, "Свой тестовый фреймворк",
                "Цель: научиться работать с reflection и аннотациями, понять принцип работы фреймворка junit.",
                "Написать свой тестовый фреймворк.",
                "Поддержать свои аннотации @Test, @Before, @After.",
                "Запускать вызовом статического метода с именем класса с тестами.",
                "Т.е. надо сделать:",
                "1) создать три аннотации - @Test, @Before, @After.",
                "2) Создать класс-тест, в котором будут методы, отмеченные аннотациями.",
                "3) Создать 'запускалку теста'. На вход она должна получать имя класса с тестами, в котором следует найти и запустить методы отмеченные аннотациями и пункта 1.",
                "4) Алгоритм запуска должен быть следующий::",
                "метод(ы) Before",
                "текущий метод Test",
                "метод(ы) After",
                "для каждой такой 'тройки' надо создать СВОЙ объект класса-теста.",
                "5) Исключение в одном тесте не должно прерывать весь процесс тестирования.",
                "6) На основании возникших во время тестирования исключений вывести статистику выполнения тестов (сколько прошло успешно, сколько упало, сколько было всего)");

        List<String> stringList = createEmptyArrayList(stringDIYArrayList.size());
        Collections.copy(stringList, stringDIYArrayList);   // copy before sort
        Collections.sort(stringDIYArrayList, (s1, s2) -> -s1.compareToIgnoreCase(s2));
        // compare collections
        printAnyArrayList(stringList);
        printAnyArrayList(stringDIYArrayList);


        // double DIYArrayList
        DIYArrayList<Double> doubleDIYArrayList = new DIYArrayList<>();
        for (int i = 0; i < 50; i++)
            doubleDIYArrayList.add(randomDouble());

        // + 30 numbers else
        Collections.addAll(doubleDIYArrayList,
                randomDouble(), randomDouble(), randomDouble(), randomDouble(), randomDouble(),
                randomDouble(), randomDouble(), randomDouble(), randomDouble(), randomDouble(),
                randomDouble(), randomDouble(), randomDouble(), randomDouble(), randomDouble(),
                randomDouble(), randomDouble(), randomDouble(), randomDouble(), randomDouble(),
                randomDouble(), randomDouble(), randomDouble(), randomDouble(), randomDouble(),
                randomDouble(), randomDouble(), randomDouble(), randomDouble(), randomDouble()
        );

        List<Double> doubleList = createEmptyArrayList(doubleDIYArrayList.size());
        Collections.copy(doubleList, doubleDIYArrayList);   // copy before sort
        Collections.sort(doubleDIYArrayList, (n1, n2) -> -n1.compareTo(n2));
        // compare collections
        printAnyArrayList(doubleList);
        printAnyArrayList(doubleDIYArrayList);


        // Long DIYArrayList
        DIYArrayList<Long> longDIYArrayList = new DIYArrayList<>();
        for (int i = 0; i < 50; i++)
            longDIYArrayList.add(randomLong6());

        // + 30 numbers else
        Collections.addAll(longDIYArrayList,
                randomLong6(), randomLong6(), randomLong6(), randomLong6(), randomLong6(),
                randomLong6(), randomLong6(), randomLong6(), randomLong6(), randomLong6(),
                randomLong6(), randomLong6(), randomLong6(), randomLong6(), randomLong6(),
                randomLong6(), randomLong6(), randomLong6(), randomLong6(), randomLong6(),
                randomLong6(), randomLong6(), randomLong6(), randomLong6(), randomLong6(),
                randomLong6(), randomLong6(), randomLong6(), randomLong6(), randomLong6()
        );

        List<Long> longList = createEmptyArrayList(longDIYArrayList.size());
        Collections.copy(longList, longDIYArrayList);   // copy before sort
        Collections.sort(longDIYArrayList, (n1, n2) -> -n1.compareTo(n2));
        printAnyArrayList(longList);
        printAnyArrayList(longDIYArrayList);
    }

    private static <T> List<T> createEmptyArrayList(int size) {
    List<T> result = new ArrayList<>(size);
        for (int i = 0; i < size; i++)
            result.add(null);
        return result;
    }

    private static <T> void printAnyArrayList(List<T> arrayList) {
        int i = 0;
        for (T obj : arrayList)
            System.out.println("" + (i++) + ": "+ obj.toString());
    }

    private static Double randomDouble() {
        return Math.random() * 1000000;
    }
    
    private static Long randomLong6() {
        return Long.valueOf(("" + Math.round(randomDouble()) + "000000").substring(0,6));
    }
}
