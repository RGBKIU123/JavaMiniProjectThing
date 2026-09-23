package com.college.resources.model;

public class CalculatorResource extends BorrowableResource {
    private final String model;
    private final boolean scientific;

    public CalculatorResource(Student owner, String name, String description, String condition,
                              int maxBorrowDays, String model, boolean scientific) {
        super(owner, ResourceCategory.CALCULATOR, name, description, condition, maxBorrowDays);
        this.model = model;
        this.scientific = scientific;
    }

    public String getModel() {
        return model;
    }

    public boolean isScientific() {
        return scientific;
    }

    @Override
    public String getResourceType() {
        return scientific ? "Scientific Calculator" : "Calculator";
    }
}
