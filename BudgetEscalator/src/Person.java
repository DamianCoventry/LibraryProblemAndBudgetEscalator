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

public class Person extends Thread {
    private static final int MIN_START_DELAY = 500;
    private static final int MAX_START_DELAY = 30000;

    private final int _id;
    private final Escalator _escalator;
    private final CountDownLatch _done;
    private final Escalator.Floor _startingFloor;
    private final Random _random = new Random();

    public Person(int i, Escalator escalator, CountDownLatch done, Escalator.Floor startingFloor) {
        _id = i;
        setName("Person" + i);
        _escalator = escalator;
        _done = done;
        _startingFloor = startingFloor;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(randomStartDelay());

            _escalator.enter(_id, _startingFloor);      // synchronised

            Thread.sleep(Escalator.RIDE_TIME);

            _escalator.exit(_id, _startingFloor);       // synchronised
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            _done.countDown();
        }
    }
    private int randomStartDelay() {
        return MIN_START_DELAY + _random.nextInt(MAX_START_DELAY);
    }

}
