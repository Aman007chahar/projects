package EmployeePayrollSystem;

abstract class Employee {
    private int id;
    private String name;

    public Employee(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }



public abstract double calculateSalary();
    @Override
    public String toString(){
        return "Employee name : " + getName() + "\nEmployee Id : " + getId() + "\nSalary : " + calculateSalary() ;

    }
}
