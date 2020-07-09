package ru.otus;

import ru.otus.appcontainer.AppComponentsContainerImpl;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.config.AppConfig;
import ru.otus.config.AppConfig1;
import ru.otus.config.AppConfig2;
import ru.otus.services.GameProcessor;

/*
В классе AppComponentsContainerImpl реализовать обработку, полученной в конструкторе конфигурации,
основываясь на разметке аннотациями из пакета appcontainer. Так же необходимо реализовать методы getAppComponent.
В итоге должно получиться работающее приложение. Менять можно только класс AppComponentsContainerImpl.

PS Приложение представляет из себя тренажер таблицы умножения)
*/

public class App {

    public static void main(String[] args) throws Exception {

        // single config
        /*AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig.class);
        GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        //GameProcessor gameProcessor = container.getAppComponent("gameProcessor");*/

        // several configs
        /*AppComponentsContainer container = new AppComponentsContainerImpl(AppConfig2.class, AppConfig1.class);
        GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        //GameProcessor gameProcessor = container.getAppComponent("gameProcessor");*/

        // package config
        AppComponentsContainer container = new AppComponentsContainerImpl("ru.otus.config");
        GameProcessor gameProcessor = container.getAppComponent(GameProcessor.class);
        //GameProcessor gameProcessor = container.getAppComponent("gameProcessor");

        gameProcessor.startGame();
    }
}
