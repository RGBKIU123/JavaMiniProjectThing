package com.college.resources.model;

import java.time.LocalDateTime;

public class BorrowRequest {
    private static int nextId = 1000;

    private final int id;
    private final Resource resource;
    private final Student borrower;
    private final Student owner;
    private final String message;
    private final LocalDateTime createdAt;
    private RequestStatus status;

    public BorrowRequest(Resource resource, Student borrower, String message) {
        this.id = nextId++;
        this.resource = resource;
        this.borrower = borrower;
        this.owner = resource.getOwner();
        this.message = message == null ? "" : message.trim();
        this.createdAt = LocalDateTime.now();
        this.status = RequestStatus.PENDING;
    }

    public int getId() {
        return id;
    }

    public Resource getResource() {
        return resource;
    }

    public Student getBorrower() {
        return borrower;
    }

    public Student getOwner() {
        return owner;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BorrowRequest{id=" + id + ", resource=" + resource.getName() + ", borrower=" + borrower.getName()
                + ", owner=" + owner.getName() + ", status=" + status + "}";
    }
}
