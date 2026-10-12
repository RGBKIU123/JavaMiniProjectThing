# College Resource Sharing System (Java OOPJ)

This repository now contains a **working Phase-1 (~50%)** console-based College Resource Sharing System using Java and OOP concepts.

## Is VS Code enough?
Yes. VS Code is enough if you install:
- **JDK 17+**
- **Maven 3.9+**
- VS Code extension: **Extension Pack for Java** (recommended)

## How to run
```bash
mvn test
mvn exec:java
```

## Implemented architecture (OOP focused)

### Core model files (`src/main/java/com/college/resources/model`)
- `Student.java` - student entity with encapsulated fields and constructor.
- `Resource.java` - **abstract** base class for all resources.
- `BorrowableResource.java` - single inheritance layer for borrowable resources.
- `AcademicResource.java` - multilevel inheritance layer for academic resources.
- `TextbookResource.java`, `ReferenceBookResource.java`, `CalculatorResource.java`, `LabEquipmentResource.java` - concrete resource types (**hierarchical inheritance** + overriding).
- `BorrowRequest.java` - dynamic borrow request object.
- `BorrowTransaction.java` - borrowing/return lifecycle tracking.
- `Review.java` - rating/review object.
- `Borrowable.java`, `Reviewable.java` - interfaces (multiple interface usage through `Resource`).
- `ResourceCategory.java`, `RequestStatus.java`, `TransactionStatus.java` - enums.

### Service layer (`src/main/java/com/college/resources/service`)
- `CollegeResourceService.java` - business logic and full dynamic runtime workflow.
- `ValidationException.java` - custom exception used for validation and flow errors.

### Console/UI (`src/main/java/com/college/resources/ui`)
- `ConsoleApp.java` - Scanner-based professional menu.
- `Main.java` - app entry point.

### Testing (`src/test/java/com/college/resources/service`)
- `CollegeResourceServiceTest.java` validates:
  - case-insensitive + partial String search
  - request -> accept -> borrow -> return flow

## OOPJ requirements covered in this phase
- Classes/objects, constructors, encapsulation, access modifiers
- `this`, `super`, `static`, `final`
- Single/multilevel/hierarchical inheritance
- Method overloading (`searchResources`, `addReview`)
- Method overriding + runtime polymorphism
- Abstract class and abstract method
- Interfaces + multiple interfaces
- String methods for realistic searching
- `Vector` collections for runtime dynamic objects
- Exception handling with custom exceptions and UI-safe catches

## Current working features (Phase-1 ~50%)
1. Student registration (dynamic)
2. Dynamic resource addition (type selected at runtime)
3. View available resources
4. Search/filter resources (partial + case-insensitive)
5. Send borrow requests
6. View owner requests and accept/reject
7. Auto-create borrow transactions when accepted
8. Return resource and restore availability
9. Borrowing history
10. Ratings/reviews after return
11. Input validation and exception handling

## Typical workflow
Register Student -> Add Resource -> Search/View -> Request -> Accept/Reject -> Borrow -> Return -> View History -> Review
