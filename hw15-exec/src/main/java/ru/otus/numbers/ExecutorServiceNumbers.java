package ru.otus.numbers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceNumbers {
    private static final int MAX_NUMBER = 10;
    private static final Logger logger = LoggerFactory.getLogger(ExecutorServiceNumbers.class);

    static class Task implements Runnable {
        private final int id;
        int counter = 0;
        int increment = 1;
        public Task(int id) {
            this.id = id;
        }
        @Override
        public void run() {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                counter += increment;
                if (counter == MAX_NUMBER + 1 || counter == 0) {
                    increment = -increment;
                    counter += 2 * increment;
                }
                logger.info("{}", counter);
                sleep(1000);
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.submit(new Task(1));
        sleep(500);
        executor.submit(new Task(2));
    }

    private static void sleep(int delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
