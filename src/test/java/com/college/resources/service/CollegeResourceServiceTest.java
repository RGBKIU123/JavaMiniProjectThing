package com.college.resources.service;

import com.college.resources.model.CalculatorResource;
import com.college.resources.model.Resource;
import com.college.resources.model.RequestStatus;
import com.college.resources.model.Student;
import com.college.resources.model.TransactionStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollegeResourceServiceTest {

    @Test
    void shouldSearchCaseInsensitiveAndPartial() throws ValidationException {
        CollegeResourceService service = new CollegeResourceService();
        Student owner = service.registerStudent("Asha", "CSE", "asha@college.edu", "111");
        service.registerStudent("Ravi", "ECE", "ravi@college.edu", "222");

        Resource calc = new CalculatorResource(owner, "Casio fx-991ES", "Scientific calc", "good", 7, "fx-991ES", true);
        service.addResource(calc);

        assertEquals(1, service.searchResources("casio").size());
        assertEquals(1, service.searchResources("991").size());
        assertEquals(1, service.searchResources("SCIENTIFIC").size());
    }

    @Test
    void shouldCreateRequestAcceptAndReturnFlow() throws ValidationException {
        CollegeResourceService service = new CollegeResourceService();
        Student owner = service.registerStudent("Owner", "ME", "owner@college.edu", "111");
        Student borrower = service.registerStudent("Borrower", "CE", "borrower@college.edu", "222");

        Resource calc = new CalculatorResource(owner, "Casio", "Calc", "good", 7, "991", true);
        service.addResource(calc);

        var request = service.createBorrowRequest(borrower.getId(), calc.getId(), "Need for exam");
        var transaction = service.processRequest(owner.getId(), request.getId(), true);

        assertNotNull(transaction);
        assertEquals(RequestStatus.ACCEPTED, request.getStatus());
        assertFalse(calc.isAvailable());

        var returned = service.returnResource(borrower.getId(), transaction.getId());
        assertEquals(TransactionStatus.RETURNED, returned.getStatus());
        assertTrue(calc.isAvailable());
    }
}
