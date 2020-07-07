package ru.otus.appcontainer;

import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {

        processConfig(initialConfigClass);
    }

    private void processConfig(Class<?> configClass) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {

        checkConfigClass(configClass);
        fillAppComponentLists(scanAppConfigMethods(configClass), configClass.getConstructor().newInstance());
    }

    private Map<Integer, Map<String, Method>> scanAppConfigMethods(Class<?> configClass) {
        Map<Integer, Map<String, Method>> result = new TreeMap<>();

        for (Method method : configClass.getMethods()) {
            AppComponent appComponentAnnotation = method.getDeclaredAnnotation(AppComponent.class);
            if (appComponentAnnotation != null) {
                result.computeIfAbsent(appComponentAnnotation.order(), k -> new HashMap<>()).put(appComponentAnnotation.name(), method);
            }
        }
        return result;
    }

    private void fillAppComponentLists(Map<Integer, Map<String, Method>> allMethodsMap, Object configObject)
            throws InvocationTargetException, IllegalAccessException {

        for (Map<String, Method> methodMap: allMethodsMap.values()) {
            for (Map.Entry<String, Method> methodEntry: methodMap.entrySet()) {
                Object appComponent = createAppComponent(configObject, methodEntry.getValue());
                appComponents.add(appComponent);
                appComponentsByName.put(methodEntry.getKey(), appComponent);
            }
        }
    }

    private Object createAppComponent(Object configObject, Method method) throws InvocationTargetException, IllegalAccessException {
        return method.invoke(configObject, fillMethodParameters(method));
    }

    private Object[] fillMethodParameters(Method method) {
        Object[] params = new Object[method.getParameterCount()];

        for (int p = 0; p < method.getParameterCount(); p++) {
            params[p] = getAppComponent(method.getParameters()[p].getType());
        }
        return params;
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(Class<C> componentClass) {
        return (C) appComponents.stream().filter(componentClass::isInstance).findFirst().orElse(null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <C> C getAppComponent(String componentName) {
        return (C) appComponentsByName.get(componentName);
    }
}
