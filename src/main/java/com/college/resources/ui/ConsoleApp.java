package com.college.resources.ui;

import com.college.resources.model.CalculatorResource;
import com.college.resources.model.LabEquipmentResource;
import com.college.resources.model.ReferenceBookResource;
import com.college.resources.model.Resource;
import com.college.resources.model.ResourceCategory;
import com.college.resources.model.Student;
import com.college.resources.model.TextbookResource;
import com.college.resources.service.CollegeResourceService;
import com.college.resources.service.ValidationException;

import java.util.Vector;

public class ConsoleApp {
    private static final String LINE = "------------------------------------------------------------";
    private final java.util.Scanner scanner = new java.util.Scanner(System.in);
    private final CollegeResourceService service = new CollegeResourceService();

    public void run() {
        System.out.println("\n=== College Resource Sharing System (Phase-1 ~50%) ===");
        while (true) {
            showMenu();
            int choice = readInt("Choose option: ");
            try {
                switch (choice) {
                    case 1 -> registerStudent();
                    case 2 -> addResource();
                    case 3 -> printResources(service.getAvailableResources());
                    case 4 -> searchResources();
                    case 5 -> sendBorrowRequest();
                    case 6 -> processRequests();
                    case 7 -> returnResource();
                    case 8 -> viewHistory();
                    case 9 -> addReview();
                    case 0 -> {
                        System.out.println("Exiting application. Goodbye!");
                        return;
                    }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (ValidationException ex) {
                System.out.println("Error: " + ex.getMessage());
            } catch (Exception ex) {
                System.out.println("Unexpected error: " + ex.getMessage());
            }
        }
    }

    private void showMenu() {
        System.out.println("\n" + LINE);
        System.out.println("1. Register Student");
        System.out.println("2. Add Resource (dynamic type)");
        System.out.println("3. View Available Resources");
        System.out.println("4. Search Resources");
        System.out.println("5. Send Borrow Request");
        System.out.println("6. Accept/Reject Requests");
        System.out.println("7. Return Resource");
        System.out.println("8. View Borrowing History");
        System.out.println("9. Add Rating/Review");
        System.out.println("0. Exit");
        System.out.println(LINE);
    }

    private void registerStudent() throws ValidationException {
        System.out.println("\nRegister Student");
        String name = readLine("Name: ");
        String department = readLine("Department: ");
        String email = readLine("Email: ");
        String phone = readLine("Phone: ");

        Student student = service.registerStudent(name, department, email, phone);
        System.out.println("Registered: " + student);
    }

    private void addResource() throws ValidationException {
        if (service.getStudents().isEmpty()) {
            throw new ValidationException("Register students first.");
        }

        System.out.println("\nAdd Resource");
        listStudents();
        int ownerId = readInt("Owner Student ID: ");
        Student owner = service.findStudentById(ownerId);

        System.out.println("Choose Resource Type: 1.Textbook 2.Reference Book 3.Calculator 4.Lab Equipment");
        int type = readInt("Type: ");
        String name = readLine("Resource Name: ");
        String description = readLine("Description: ");
        String condition = readLine("Condition (new/good/fair): ");
        int maxDays = readInt("Max Borrow Days: ");

        Resource resource;
        if (type == 1) {
            String subject = readLine("Subject: ");
            String author = readLine("Author: ");
            String edition = readLine("Edition: ");
            resource = new TextbookResource(owner, name, description, condition, maxDays, subject, author, edition);
        } else if (type == 2) {
            String subject = readLine("Subject: ");
            String publisher = readLine("Publisher: ");
            resource = new ReferenceBookResource(owner, name, description, condition, maxDays, subject, publisher);
        } else if (type == 3) {
            String model = readLine("Model: ");
            boolean scientific = readYesNo("Scientific calculator? (y/n): ");
            resource = new CalculatorResource(owner, name, description, condition, maxDays, model, scientific);
        } else if (type == 4) {
            String labName = readLine("Lab Name: ");
            resource = new LabEquipmentResource(owner, name, description, condition, maxDays, labName);
        } else {
            throw new ValidationException("Unsupported resource type.");
        }

        service.addResource(resource);
        System.out.println("Resource added: " + resource);
    }

    private void searchResources() {
        String term = readLine("Search term: ");
        String categoryInput = readLine("Category filter (TEXTBOOK/REFERENCE_BOOK/CALCULATOR/LAB_EQUIPMENT or blank): ");

        Vector<Resource> result;
        if (categoryInput.trim().isEmpty()) {
            result = service.searchResources(term);
        } else {
            try {
                ResourceCategory category = ResourceCategory.valueOf(categoryInput.trim().toUpperCase());
                result = service.searchResources(term, category);
            } catch (IllegalArgumentException ex) {
                System.out.println("Invalid category. Showing all categories.");
                result = service.searchResources(term);
            }
        }
        printResources(result);
    }

    private void sendBorrowRequest() throws ValidationException {
        listStudents();
        int borrowerId = readInt("Borrower Student ID: ");
        printResources(service.getAvailableResources());
        int resourceId = readInt("Resource ID to request: ");
        String message = readLine("Message to owner: ");

        var request = service.createBorrowRequest(borrowerId, resourceId, message);
        System.out.println("Request created: " + request);
    }

    private void processRequests() throws ValidationException {
        listStudents();
        int ownerId = readInt("Owner Student ID: ");
        Vector<com.college.resources.model.BorrowRequest> list = service.getRequestsForOwner(ownerId);
        if (list.isEmpty()) {
            System.out.println("No requests for this owner.");
            return;
        }

        for (var request : list) {
            System.out.println(request);
        }

        int requestId = readInt("Request ID to process: ");
        boolean accept = readYesNo("Accept request? (y/n): ");
        var transaction = service.processRequest(ownerId, requestId, accept);

        if (transaction == null) {
            System.out.println("Request rejected.");
        } else {
            System.out.println("Request accepted. Transaction: " + transaction);
        }
    }

    private void returnResource() throws ValidationException {
        listStudents();
        int borrowerId = readInt("Borrower Student ID: ");
        for (var transaction : service.getTransactions()) {
            System.out.println(transaction);
        }
        int transactionId = readInt("Transaction ID to return: ");
        var transaction = service.returnResource(borrowerId, transactionId);
        System.out.println("Returned: " + transaction);
    }

    private void viewHistory() {
        listStudents();
        int studentId = readInt("Student ID: ");
        for (var transaction : service.getHistoryForStudent(studentId)) {
            System.out.println(transaction);
        }
    }

    private void addReview() throws ValidationException {
        for (var transaction : service.getTransactions()) {
            System.out.println(transaction);
        }
        int transactionId = readInt("Transaction ID: ");
        int reviewerId = readInt("Borrower (reviewer) Student ID: ");
        int rating = readInt("Rating (1-5): ");
        String comment = readLine("Comment: ");
        service.addResourceReview(transactionId, reviewerId, rating, comment);
        System.out.println("Review added.");
    }

    private void printResources(Vector<Resource> resources) {
        if (resources.isEmpty()) {
            System.out.println("No matching resources.");
            return;
        }
        for (Resource resource : resources) {
            System.out.println(resource);
        }
    }

    private void listStudents() {
        Vector<Student> students = service.getStudents();
        if (students.isEmpty()) {
            System.out.println("No students registered.");
            return;
        }
        for (Student student : students) {
            System.out.println(student);
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String line = scanner.nextLine();
                return Integer.parseInt(line.trim());
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid integer.");
            }
        }
    }

    private boolean readYesNo(String prompt) {
        while (true) {
            String value = readLine(prompt).trim().toLowerCase();
            if (value.equals("y") || value.equals("yes")) {
                return true;
            }
            if (value.equals("n") || value.equals("no")) {
                return false;
            }
            System.out.println("Please enter y or n.");
        }
    }
}
