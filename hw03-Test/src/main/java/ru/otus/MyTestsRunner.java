package ru.otus;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyTestsRunner {

    private static int totalTestCount = 0;
    private static int successTestCount = 0;
    private static int failedTestCount = 0;

    public static void runMyTests(String className) {

        Class<?> clazz = getTestClass(className);
        if (clazz == null) {
            return;
        }

        List<Method> methodsBefore = new ArrayList<>();
        List<Method> methodsTest = new ArrayList<>();
        List<Method> methodsAfter = new ArrayList<>();

        Method[] methodsAll = clazz.getDeclaredMethods();

        Arrays.stream(methodsAll).forEach(method -> {
            Arrays.stream(method.getDeclaredAnnotations()).forEach(annotation ->  {
                addAnnotatedMethod(method, annotation, methodsBefore, Before.class);
                addAnnotatedMethod(method, annotation, methodsTest, Test.class);
                addAnnotatedMethod(method, annotation, methodsAfter, After.class);
            });
        });

        totalTestCount = 0;
        successTestCount = 0;
        failedTestCount = 0;

        for (Method testMethod : methodsTest) {

            boolean success = true;
            totalTestCount++;
            Object obj;

            try {
                obj = clazz.getDeclaredConstructor().newInstance();

                success = invokeMethods(obj, methodsBefore, "@Before");
                success &= invokeMethod(obj, testMethod, "@Test");
                success &= invokeMethods(obj, methodsAfter, "@After");

            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                System.out.println("Exception occurred instantiating object");
                success = false;
            }

            if (success) {
                successTestCount++;
            } else {
                failedTestCount++;
            }
        }
        System.out.println("=======================");
        System.out.println("Total tests: " + totalTestCount);
        System.out.println("Successful tests: " + successTestCount);
        System.out.println("Failed tests: " + failedTestCount);
    }

    private static void addAnnotatedMethod(Method method, Annotation annotation, List<Method> methodList, Class<?> annotationClass) {
        if (annotation.annotationType().equals(annotationClass)) {
            methodList.add(method);
        }
    }

    private static boolean invokeMethods(Object obj, List<Method> methods, String methodType) {
        boolean success = true;
        for (Method method : methods) {
            success &= invokeMethod(obj, method, methodType);
        }
        return success;
    }

    private static boolean invokeMethod(Object obj, Method method, String methodType) {
        boolean success = true;
        try {
            method.invoke(obj);
        } catch (IllegalAccessException | InvocationTargetException e) {
            System.out.println("Exception occurred calling "+ methodType +" method " + method.getName() + ": " + e.getCause().toString());
            success = false;
        }
        return success;
    }

    private static Class<?> getTestClass(String className) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + className);
        }
        return clazz;
    }
}
