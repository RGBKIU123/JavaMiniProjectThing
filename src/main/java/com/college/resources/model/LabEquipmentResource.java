package com.college.resources.model;

public class LabEquipmentResource extends BorrowableResource {
    private final String labName;

    public LabEquipmentResource(Student owner, String name, String description, String condition,
                                int maxBorrowDays, String labName) {
        super(owner, ResourceCategory.LAB_EQUIPMENT, name, description, condition, maxBorrowDays);
        this.labName = labName;
    }

    public String getLabName() {
        return labName;
    }

    @Override
    public String getResourceType() {
        return "Lab Equipment";
    }
}
