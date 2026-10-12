package com.college.resources.model;

public abstract class BorrowableResource extends Resource {
    private final int maxBorrowDays;

    protected BorrowableResource(Student owner, ResourceCategory category, String name, String description, String condition,
                                 int maxBorrowDays) {
        super(owner, category, name, description, condition);
        this.maxBorrowDays = maxBorrowDays;
    }

    public int getMaxBorrowDays() {
        return maxBorrowDays;
    }
}
