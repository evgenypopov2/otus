package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LogInvocator {

    private static List<Method> loggingMethods;

    private LogInvocator() {
    }

    static Class4Logging createLoggingClass() {

        loggingMethods = new ArrayList<>();

        Arrays.stream(Class4LoggingImpl.class.getDeclaredMethods()).forEach(method -> {
            Arrays.stream(method.getDeclaredAnnotations()).forEach(annotation -> {
                if (annotation.annotationType().equals(Log.class)) {
                    try {
                        Method mm = Class4Logging.class.getMethod(method.getName(), method.getParameterTypes());
                        loggingMethods.add(mm);
                    } catch (NoSuchMethodException e) {
                        ; // dont know what to do - so what?
                    }
                }
            });
        });

        InvocationHandler handler = new LogInvocationHandler(new Class4LoggingImpl());
        return (Class4Logging) Proxy.newProxyInstance(LogInvocator.class.getClassLoader(),
                new Class<?>[]{Class4Logging.class}, handler);
    }

    static class LogInvocationHandler implements InvocationHandler {

        private final Class4Logging loggingClass;

        LogInvocationHandler(Class4Logging loggingClass) {
            this.loggingClass = loggingClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            Object result = method.invoke(loggingClass, args);

            if (loggingMethods.contains(method)) {
                StringBuilder stringBuilder = new StringBuilder("==== Executed method: ").append(method.getName());
                if (args != null && args.length > 0) {
                    stringBuilder.append(", params: (");
                    for (int i = 0; i < args.length; i++) {
                        stringBuilder.append(i == 0 ? "" : ", ").append(args[i]);
                    }
                    stringBuilder.append(")");
                }
                System.out.println(stringBuilder.toString());
            }
            return result;
        }
    }
}
