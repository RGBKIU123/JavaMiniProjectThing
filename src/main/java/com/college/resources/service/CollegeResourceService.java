package com.college.resources.service;

import com.college.resources.model.BorrowRequest;
import com.college.resources.model.BorrowTransaction;
import com.college.resources.model.RequestStatus;
import com.college.resources.model.Resource;
import com.college.resources.model.ResourceCategory;
import com.college.resources.model.Student;
import com.college.resources.model.TransactionStatus;

import java.util.Vector;

public class CollegeResourceService {
    private final Vector<Student> students = new Vector<>();
    private final Vector<Resource> resources = new Vector<>();
    private final Vector<BorrowRequest> requests = new Vector<>();
    private final Vector<BorrowTransaction> transactions = new Vector<>();

    public Student registerStudent(String name, String department, String email, String phone) throws ValidationException {
        if (isBlank(name) || isBlank(department) || isBlank(email)) {
            throw new ValidationException("Name, department and email are required.");
        }
        Student student = new Student(name.trim(), department.trim(), email.trim(), phone == null ? "" : phone.trim());
        students.add(student);
        return student;
    }

    public Resource addResource(Resource resource) throws ValidationException {
        if (resource == null) {
            throw new ValidationException("Resource cannot be null.");
        }
        resources.add(resource);
        return resource;
    }

    public Vector<Student> getStudents() {
        return new Vector<>(students);
    }

    public Vector<Resource> getResources() {
        return new Vector<>(resources);
    }

    public Vector<Resource> getAvailableResources() {
        Vector<Resource> result = new Vector<>();
        for (Resource resource : resources) {
            if (resource.isAvailable()) {
                result.add(resource);
            }
        }
        return result;
    }

    public Vector<Resource> searchResources(String searchTerm) {
        Vector<Resource> result = new Vector<>();
        for (Resource resource : resources) {
            if (resource.isAvailable() && resource.matches(searchTerm)) {
                result.add(resource);
            }
        }
        return result;
    }

    public Vector<Resource> searchResources(String searchTerm, ResourceCategory category) {
        Vector<Resource> filtered = new Vector<>();
        for (Resource resource : searchResources(searchTerm)) {
            if (category == null || resource.getCategory() == category) {
                filtered.add(resource);
            }
        }
        return filtered;
    }

    public BorrowRequest createBorrowRequest(int borrowerId, int resourceId, String message) throws ValidationException {
        Student borrower = findStudentById(borrowerId);
        Resource resource = findResourceById(resourceId);
        if (resource.getOwner().getId() == borrower.getId()) {
            throw new ValidationException("Owner cannot request their own resource.");
        }
        if (!resource.isAvailable()) {
            throw new ValidationException("Resource is currently unavailable.");
        }

        BorrowRequest request = new BorrowRequest(resource, borrower, message);
        requests.add(request);
        return request;
    }

    public BorrowTransaction processRequest(int ownerId, int requestId, boolean accept) throws ValidationException {
        BorrowRequest request = findRequestById(requestId);
        if (request.getOwner().getId() != ownerId) {
            throw new ValidationException("Only owner can process this request.");
        }
        if (request.getStatus() != RequestStatus.PENDING) {
            throw new ValidationException("Request already processed.");
        }

        if (!accept) {
            request.setStatus(RequestStatus.REJECTED);
            return null;
        }

        if (!request.getResource().isAvailable()) {
            throw new ValidationException("Resource became unavailable.");
        }

        request.setStatus(RequestStatus.ACCEPTED);
        request.getResource().markBorrowed();

        int durationDays = 7;
        if (request.getResource() instanceof com.college.resources.model.BorrowableResource borrowableResource) {
            durationDays = borrowableResource.getMaxBorrowDays();
        }

        BorrowTransaction transaction = new BorrowTransaction(request, durationDays);
        transactions.add(transaction);
        return transaction;
    }

    public BorrowTransaction returnResource(int borrowerId, int transactionId) throws ValidationException {
        BorrowTransaction transaction = findTransactionById(transactionId);
        if (transaction.getStatus() != TransactionStatus.ACTIVE) {
            throw new ValidationException("Transaction is not active.");
        }

        if (transaction.getRequest().getBorrower().getId() != borrowerId) {
            throw new ValidationException("Only borrower can return this resource.");
        }

        transaction.markReturned();
        transaction.getRequest().getResource().markReturned();
        return transaction;
    }

    public void addResourceReview(int transactionId, int reviewerId, int rating, String comment) throws ValidationException {
        BorrowTransaction transaction = findTransactionById(transactionId);
        if (transaction.getStatus() != TransactionStatus.RETURNED) {
            throw new ValidationException("Review allowed only after return.");
        }
        if (transaction.getRequest().getBorrower().getId() != reviewerId) {
            throw new ValidationException("Only borrower can submit review.");
        }
        transaction.getRequest().getResource().addReview(rating, comment);
    }

    public Vector<BorrowRequest> getRequests() {
        return new Vector<>(requests);
    }

    public Vector<BorrowRequest> getRequestsForOwner(int ownerId) {
        Vector<BorrowRequest> result = new Vector<>();
        for (BorrowRequest request : requests) {
            if (request.getOwner().getId() == ownerId) {
                result.add(request);
            }
        }
        return result;
    }

    public Vector<BorrowTransaction> getTransactions() {
        return new Vector<>(transactions);
    }

    public Vector<BorrowTransaction> getHistoryForStudent(int studentId) {
        Vector<BorrowTransaction> result = new Vector<>();
        for (BorrowTransaction transaction : transactions) {
            boolean isBorrower = transaction.getRequest().getBorrower().getId() == studentId;
            boolean isOwner = transaction.getRequest().getOwner().getId() == studentId;
            if (isBorrower || isOwner) {
                result.add(transaction);
            }
        }
        return result;
    }

    public Student findStudentById(int id) throws ValidationException {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        throw new ValidationException("Student not found: " + id);
    }

    public Resource findResourceById(int id) throws ValidationException {
        for (Resource resource : resources) {
            if (resource.getId() == id) {
                return resource;
            }
        }
        throw new ValidationException("Resource not found: " + id);
    }

    public BorrowRequest findRequestById(int id) throws ValidationException {
        for (BorrowRequest request : requests) {
            if (request.getId() == id) {
                return request;
            }
        }
        throw new ValidationException("Request not found: " + id);
    }

    public BorrowTransaction findTransactionById(int id) throws ValidationException {
        for (BorrowTransaction transaction : transactions) {
            if (transaction.getId() == id) {
                return transaction;
            }
        }
        throw new ValidationException("Transaction not found: " + id);
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
