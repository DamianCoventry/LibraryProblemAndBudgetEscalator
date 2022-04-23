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

public class Main {
    private static final int NUM_PEOPLE = 100;
    private static final int MIN_PEOPLE = 20;
    private static final int MAX_PEOPLE = NUM_PEOPLE - MIN_PEOPLE;

    private static final Random _random = new Random();

    public static void main(String[] args) {
        try {
            CountDownLatch done = new CountDownLatch(NUM_PEOPLE);

            Escalator escalator = new Escalator();
            escalator.start();

            int people = chooseRandomNumberOfPeople();

            for (int i = 0; i < people; ++i) {
                Person reader = new Person(i, escalator, done, Escalator.Floor.FIRST);
                reader.start();
            }
            for (int i = 0; i < NUM_PEOPLE - people; ++i) {
                Person reader = new Person(i, escalator, done, Escalator.Floor.SECOND);
                reader.start();
            }

            done.await();
            escalator.shutdown();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int chooseRandomNumberOfPeople() {
        return MIN_PEOPLE + _random.nextInt(MAX_PEOPLE - MIN_PEOPLE);
    }
}
