package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

public class LogInvocator {

    private static Set<Method> loggingMethods;

    private LogInvocator() {
    }

    static Class4Logging createLoggingClass() throws NoSuchMethodException {

        loggingMethods = new HashSet<>();

        for (Method method : Class4LoggingImpl.class.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Log.class)) {
                Method interfaceMethod = Class4Logging.class.getMethod(method.getName(), method.getParameterTypes());
                loggingMethods.add(interfaceMethod);
            }
        }

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
