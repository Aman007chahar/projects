package EmployeePayrollSystem;

public class MAIN {
    public static void main(String[] args) {
        PayrollSystem payrollSystem = new PayrollSystem();
        PartTimeEmployee emp1 = new PartTimeEmployee(1,"AMAN CHAHAR",500,50);
        FullTimeEmployee emp2 = new FullTimeEmployee(2,"AKASH CHAHAR",80000);
        payrollSystem.addEmployee(emp1);
        payrollSystem.addEmployee(emp2);

        System.out.println("Initial Employee details : ");
        payrollSystem.displayEmployees();

        System.out.println("Removing Employee.....");
        payrollSystem.removeEmployee(2);

        System.out.println("Remaining Employee details : ");
        payrollSystem.displayEmployees();

    }

}
