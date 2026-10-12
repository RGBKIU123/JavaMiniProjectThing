package com.college.resources.model;

public class Review {
    private final int rating;
    private final String comment;

    public Review(int rating, String comment) {
        this.rating = Math.max(1, Math.min(5, rating));
        this.comment = comment == null ? "" : comment.trim();
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
