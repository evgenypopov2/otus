package ru.otus.appcontainer;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    private static class ScannedAppConfigMethod {
        String name;
        Method method;
        private ScannedAppConfigMethod(String name, Method method) {
            this.name = name;
            this.method = method;
        }
    }

    public AppComponentsContainerImpl(Class<?> initialConfigClass) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {

        processConfig(initialConfigClass);
    }

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {

        processConfig(initialConfigClasses);
    }

    public AppComponentsContainerImpl(String configPackage) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {

        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage(configPackage))
                .setScanners(new TypeAnnotationsScanner(), new SubTypesScanner()));

        processConfig(new ArrayList<>(reflections.getTypesAnnotatedWith(AppComponentsContainerConfig.class)));
    }

    //==================================

    private void processConfig(Class<?> configClass) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {

        checkConfigClass(configClass);
        fillAppComponentLists(scanAppConfigMethods(configClass), configClass.getConstructor().newInstance());
    }

    private void processConfig(Class<?>[] configClasses) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {

        processConfig(Stream.of(configClasses).peek(this::checkConfigClass).collect(Collectors.toList()));
    }

    private void processConfig(List<Class<?>> configClasses) throws InvocationTargetException, IllegalAccessException,
            NoSuchMethodException, InstantiationException {

        configClasses = configClasses.stream()
                .sorted(Comparator.comparingInt(c -> c.getDeclaredAnnotation(AppComponentsContainerConfig.class).order()))
                .collect(Collectors.toList());

        for (Class<?> configClass: configClasses) {
            processConfig(configClass);
        }
    }

    //==================================

    private List<ScannedAppConfigMethod> scanAppConfigMethods(Class<?> configClass) {
        return Stream.of(configClass.getMethods()).filter(method -> method.getDeclaredAnnotation(AppComponent.class) != null)
                .sorted(Comparator.comparingInt(m -> m.getDeclaredAnnotation(AppComponent.class).order()))
                .map(m -> new ScannedAppConfigMethod(m.getDeclaredAnnotation(AppComponent.class).name(), m))
                .collect(Collectors.toList());
    }

    private void fillAppComponentLists(List<ScannedAppConfigMethod> scannedAppConfigMethods, Object configObject)
            throws InvocationTargetException, IllegalAccessException {

        for (ScannedAppConfigMethod method: scannedAppConfigMethods) {
            if (appComponentsByName.get(method.name) == null) {
                Object appComponent = createAppComponent(configObject, method.method);
                appComponents.add(appComponent);
                appComponentsByName.put(method.name, appComponent);
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
