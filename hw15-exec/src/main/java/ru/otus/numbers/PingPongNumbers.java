package ru.otus.numbers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PingPongNumbers {

    private static final int MAX_NUMBER = 10;
    private static final Logger logger = LoggerFactory.getLogger(PingPongNumbers.class);

    static class ThreadCounter {
        final int id;
        int counter = 0;
        int increment = 1;
        ThreadCounter(int id) {
            this.id = id;
        }
        int inc() {
            counter += increment;
            if (counter == MAX_NUMBER+1 || counter == 0) {
                increment = -increment;
                counter += 2*increment;
            }
            return counter;
        }
    }
    private int threadCounterId = 2;

    private synchronized void count(ThreadCounter threadCounter) {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            try {
                while (threadCounterId == threadCounter.id) {
                    this.wait();
                }
                logger.info("{}", threadCounter.inc());
                threadCounterId = threadCounter.id;
                sleep();
                notifyAll();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                throw new NotInterestingException(ex);
            }
        }
    }

    public static void main(String[] args) {
        PingPongNumbers pingPongNumbers = new PingPongNumbers();
        Thread thread1 = new Thread(() -> pingPongNumbers.count(new ThreadCounter(1)));
        Thread thread2 = new Thread(() -> pingPongNumbers.count(new ThreadCounter(2)));
        thread1.start();
        thread2.start();
    }

    private static void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static class NotInterestingException extends RuntimeException {
        NotInterestingException(InterruptedException ex) {
            super(ex);
        }
    }
}
