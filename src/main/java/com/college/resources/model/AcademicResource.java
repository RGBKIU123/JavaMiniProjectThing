package com.college.resources.model;

public abstract class AcademicResource extends BorrowableResource {
    private final String subject;

    protected AcademicResource(Student owner, ResourceCategory category, String name, String description, String condition,
                               int maxBorrowDays, String subject) {
        super(owner, category, name, description, condition, maxBorrowDays);
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }
}
