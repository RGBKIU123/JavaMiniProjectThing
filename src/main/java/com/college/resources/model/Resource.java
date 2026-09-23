package com.college.resources.model;

import java.util.Vector;

public abstract class Resource implements Borrowable, Reviewable {
    private static int nextId = 100;

    private final int id;
    private final Student owner;
    private final ResourceCategory category;
    private String name;
    private String description;
    private String condition;
    private boolean available;
    private final Vector<Review> reviews = new Vector<>();

    protected Resource(Student owner, ResourceCategory category, String name, String description, String condition) {
        this.id = nextId++;
        this.owner = owner;
        this.category = category;
        this.name = name;
        this.description = description;
        this.condition = condition;
        this.available = true;
    }

    public abstract String getResourceType();

    public int getId() {
        return id;
    }

    public Student getOwner() {
        return owner;
    }

    public ResourceCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCondition() {
        return condition;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    @Override
    public void markBorrowed() {
        this.available = false;
    }

    @Override
    public void markReturned() {
        this.available = true;
    }

    @Override
    public void addReview(int rating) {
        addReview(rating, "");
    }

    @Override
    public void addReview(int rating, String comment) {
        reviews.add(new Review(rating, comment));
    }

    @Override
    public Vector<Review> getReviews() {
        return new Vector<>(reviews);
    }

    public double getAverageRating() {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        return (double) sum / reviews.size();
    }

    public boolean matches(String searchTerm) {
        String term = searchTerm == null ? "" : searchTerm.trim().toLowerCase();
        if (term.isEmpty()) {
            return true;
        }
        return name.toLowerCase().contains(term)
                || description.toLowerCase().contains(term)
                || getResourceType().toLowerCase().contains(term)
                || category.name().toLowerCase().contains(term);
    }

    @Override
    public String toString() {
        return "Resource{id=" + id
                + ", type='" + getResourceType() + '\''
                + ", name='" + name + '\''
                + ", category=" + category
                + ", owner=" + owner.getName()
                + ", available=" + available
                + ", rating=" + String.format("%.1f", getAverageRating())
                + "}";
    }
}
