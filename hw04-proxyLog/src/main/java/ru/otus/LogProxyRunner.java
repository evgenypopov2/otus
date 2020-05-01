package ru.otus;

public class LogProxyRunner {
    public static void main(String[] args) {
        Class4Logging loggingClass = LogInvocator.createLoggingClass();
        loggingClass.method4Logging1(345, "String Parameter 1");
        loggingClass.notLoggingMethod();
        loggingClass.method4Logging2("String Parameter 2", 98765.65);
        loggingClass.method4Logging2();
    }
}
