/**
 * Designed and written by Damian Coventry
 * Copyright (c) 2022, all rights reserved
 *
 * Massey University
 * 159.355 Concurrent Systems
 * Assignment 2
 * 2022 Semester 1
 *
 */

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class Reader extends Thread {
    private static final int NUM_BOOKS = 3;
    private final static int MIN_WAIT_TIME = 500;
    private final static int MAX_WAIT_TIME = 1000;
    private final static int MIN_READ_TIME = 1500;
    private final static int MAX_READ_TIME = 5000;

    private final int _id;
    private final Library _library;
    private final CountDownLatch _done;
    private final Random _random;

    public Reader(int id, Library library, CountDownLatch done) {
        _id = id;
        setName("Reader" + id);
        _library = library;
        _done = done;
        _random = new Random();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < NUM_BOOKS; ++i) {
                int book = chooseBook();
                borrowBook(book);
                readBook(book);
                _library.returnBook(_id, book);       // <-- synchronised
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("Person " + _id + " has finished reading " + NUM_BOOKS + " books");
            _done.countDown();
        }
    }

    private int chooseBook() {
        return _random.nextInt(Library.NUM_BOOKS);
    }

    private void borrowBook(int book) throws InterruptedException {
        System.out.println("Thread " + _id + " wants book " + book);
        while (!_library.requestBook(_id, book)) {       // <-- synchronised
            waitPatiently();
        }
    }

    private void waitPatiently() throws InterruptedException {
        Thread.sleep(randomWaitTime(MIN_WAIT_TIME, MAX_WAIT_TIME));
    }

    private void readBook(int book) throws InterruptedException {
        System.out.println("Thread " + _id + " is reading book " + book);
        Thread.sleep(randomWaitTime(MIN_READ_TIME, MAX_READ_TIME));
    }

    private int randomWaitTime(int min, int max) {
        return min + _random.nextInt((max - min) + 1);
    }
}
