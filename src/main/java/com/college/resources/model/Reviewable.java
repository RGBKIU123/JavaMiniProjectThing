package com.college.resources.model;

import java.util.Vector;

public interface Reviewable {
    void addReview(int rating);

    void addReview(int rating, String comment);

    Vector<Review> getReviews();
}
