package ru.otus.numbers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

public class ScheduledExecutorServiceNumbers {
    private static final int MAX_NUMBER = 10;
    private static final Logger logger = LoggerFactory.getLogger(ScheduledExecutorServiceNumbers.class);

    static class Task implements Runnable {
        private final int id;
        int counter = 0;
        int increment = 1;
        public Task(int id) {
            this.id = id;
        }
        @Override
        public void run() {
            counter += increment;
            if (counter == MAX_NUMBER+1 || counter == 0) {
                increment = -increment;
                counter += 2*increment;
            }
            logger.info("{}", counter);
        }
    }

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        executor.scheduleAtFixedRate(new Task(1), 0, 1000, TimeUnit.MILLISECONDS);
        executor.scheduleAtFixedRate(new Task(2), 500, 1000, TimeUnit.MILLISECONDS);
    }
}
