package com.college.resources.model;

public class TextbookResource extends AcademicResource {
    private final String author;
    private final String edition;

    public TextbookResource(Student owner, String name, String description, String condition,
                            int maxBorrowDays, String subject, String author, String edition) {
        super(owner, ResourceCategory.TEXTBOOK, name, description, condition, maxBorrowDays, subject);
        this.author = author;
        this.edition = edition;
    }

    public String getAuthor() {
        return author;
    }

    public String getEdition() {
        return edition;
    }

    @Override
    public String getResourceType() {
        return "Textbook";
    }
}
