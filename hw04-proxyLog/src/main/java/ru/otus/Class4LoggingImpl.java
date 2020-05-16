package ru.otus;

public class Class4LoggingImpl implements Class4Logging {

    @Override
    @Log
    public void method4Logging1(int number, String str) {
        System.out.println("I'm a logging method method4Logging1, number " + number + " and string '" + str + "' were passed to me");
    }
    @Override
    @Log
    public void method4Logging2(String str, double number) {
        System.out.println("I'm a logging method method4Logging2, string '" + str + "' and number " + number + " were passed to me");
    }
    @Override
    @Log
    public void method4Logging2() {
        System.out.println("I'm a logging method method4Logging2 without parameters");
    }
    @Override
    public void notLoggingMethod() {
        System.out.println("I'm not a logging method");
    }

}
