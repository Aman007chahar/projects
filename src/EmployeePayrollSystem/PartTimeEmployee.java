package EmployeePayrollSystem;

public class PartTimeEmployee extends Employee {
    private final int hoursWorked;
    private final double hourlyRate;

    PartTimeEmployee(int id, String name, double hourlyRate, int hoursWorked) {
        super(id, name);
        this.hourlyRate = hourlyRate;
        this.hoursWorked = hoursWorked;
    }

    @Override
    public double calculateSalary() {
        return hourlyRate * hoursWorked;
    }
}
