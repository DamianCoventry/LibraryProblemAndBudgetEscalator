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

public class Escalator extends Thread {
    private static final int MAX_CAPACITY = 10;
    private static final int CHANGE_DIRECTION_TIME = 1000;
    public static final int RIDE_TIME = 750;

    private boolean _shutdown;
    private Direction _direction;
    private int _numOccupants;

    public enum Floor { FIRST, SECOND }
    public enum Direction { UP, DOWN, CHANGING }

    public Escalator() {
        setName("Escalator");
        _shutdown = false;
        _direction = Direction.DOWN;
        _numOccupants = 0;
    }

    public synchronized void shutdown() {
        _shutdown = true;
    }

    private synchronized boolean mustShutdown() {
        return _shutdown;
    }

    public synchronized void enter(int id, Floor startingFloor) {
        // Monitor _direction and _numOccupants for changes
        while ((startingFloor == Floor.FIRST && _direction != Direction.UP) ||
               (startingFloor == Floor.SECOND && _direction != Direction.DOWN) ||
                _numOccupants == MAX_CAPACITY) {
            try {
                wait();
            }
            catch (InterruptedException ignored) { }
        }

        ++_numOccupants;
        System.out.println("Person " + id + ", coming from the " + startingFloor.toString().toLowerCase() +
                " floor, is entering the escalator (" + _numOccupants + "/" + MAX_CAPACITY + ")");
        notifyAll();
    }

    public synchronized void exit(int id, Floor startingFloor) {
        --_numOccupants;
        System.out.println("Person " + id + ", coming from the " + startingFloor.toString().toLowerCase() +
                " floor, is exiting the escalator (" + _numOccupants + "/" + MAX_CAPACITY + ")");
        notifyAll();
    }

    private synchronized void changeDirection() {
        Direction targetDirection;
        String fromFloor;
        if (_direction == Direction.DOWN) {
            targetDirection = Direction.UP;
            fromFloor = "first";
        }
        else {
            targetDirection = Direction.DOWN;
            fromFloor = "second";
        }

        _direction = Direction.CHANGING;
        System.out.println("Direction is about to change to allow travel from the " + fromFloor + " floor");

        // Monitor _numOccupants for changes
        while (_numOccupants > 0) {
            try {
                wait();
            }
            catch (InterruptedException ignored) { }
        }

        System.out.println("Escalator is empty, travel from the " + fromFloor + " floor is allowed");

        _direction = targetDirection;
        notifyAll();
    }

    // Considered using a timer object:
    //      Timer timer = new Timer();
    //      timer.schedule(new TimerTask() {...}, CHANGE_DIRECTION_TIME);
    //
    // But extending Thread and overriding run() is simple enough.
    @Override
    public void run() {
        System.out.println("The escalator has started");
        try {
            while (!mustShutdown()) {
                //noinspection BusyWait
                Thread.sleep(CHANGE_DIRECTION_TIME);
                changeDirection();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            System.out.println("The escalator has stopped");
        }
    }
}
