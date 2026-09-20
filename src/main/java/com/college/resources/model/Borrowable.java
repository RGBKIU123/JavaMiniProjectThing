package com.college.resources.model;

public interface Borrowable {
    boolean isAvailable();

    void markBorrowed();

    void markReturned();
}
