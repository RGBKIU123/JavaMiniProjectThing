package com.college.resources.model;

import java.time.LocalDate;

public class BorrowTransaction {
    private static int nextId = 5000;

    private final int id;
    private final BorrowRequest request;
    private final LocalDate borrowedOn;
    private final LocalDate dueDate;
    private LocalDate returnedOn;
    private TransactionStatus status;

    public BorrowTransaction(BorrowRequest request, int durationDays) {
        this.id = nextId++;
        this.request = request;
        this.borrowedOn = LocalDate.now();
        this.dueDate = borrowedOn.plusDays(Math.max(1, durationDays));
        this.status = TransactionStatus.ACTIVE;
    }

    public int getId() {
        return id;
    }

    public BorrowRequest getRequest() {
        return request;
    }

    public LocalDate getBorrowedOn() {
        return borrowedOn;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public LocalDate getReturnedOn() {
        return returnedOn;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void markReturned() {
        this.returnedOn = LocalDate.now();
        this.status = TransactionStatus.RETURNED;
    }

    @Override
    public String toString() {
        return "Transaction{id=" + id
                + ", requestId=" + request.getId()
                + ", resource=" + request.getResource().getName()
                + ", borrower=" + request.getBorrower().getName()
                + ", status=" + status
                + ", borrowedOn=" + borrowedOn
                + ", dueDate=" + dueDate
                + ", returnedOn=" + returnedOn
                + "}";
    }
}
