package com.college.resources.model;

public class ReferenceBookResource extends AcademicResource {
    private final String publisher;

    public ReferenceBookResource(Student owner, String name, String description, String condition,
                                 int maxBorrowDays, String subject, String publisher) {
        super(owner, ResourceCategory.REFERENCE_BOOK, name, description, condition, maxBorrowDays, subject);
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    @Override
    public String getResourceType() {
        return "Reference Book";
    }
}
