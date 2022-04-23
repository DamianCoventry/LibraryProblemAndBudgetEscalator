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

import java.util.concurrent.CountDownLatch;

public class Main {
    private static final int NUM_READERS = 100;

    public static void main(String[] args) {
        try {
            Library library = new Library();
            CountDownLatch done = new CountDownLatch(NUM_READERS);

            for (int i = 0; i < NUM_READERS; ++i) {
                Reader reader = new Reader(i, library, done);
                reader.start();
            }

            done.await();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
