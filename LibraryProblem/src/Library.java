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

public class Library {
    public static final int NUM_BOOKS = 20;

    private final boolean[] _booksAlreadyBorrowed;

    Library() {
        _booksAlreadyBorrowed = new boolean[NUM_BOOKS];
        for (int i = 0; i <  NUM_BOOKS; ++i) {
            _booksAlreadyBorrowed[i] = false;
        }
    }

    public synchronized boolean requestBook(int who, int book) {
        if (book < 0 || book >= NUM_BOOKS || _booksAlreadyBorrowed[book]) {
            return false;
        }
        System.out.println("Library gives book " + book + " to thread " + who);
        _booksAlreadyBorrowed[book] = true;
        return true;
    }

    public synchronized void returnBook(int who, int book) {
        if (book >= 0 && book < NUM_BOOKS) {
            System.out.println("Library receives returned book " + book + " from thread " + who);
            _booksAlreadyBorrowed[book] = false;
        }
    }
}
