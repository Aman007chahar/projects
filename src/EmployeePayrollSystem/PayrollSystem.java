package EmployeePayrollSystem;

import java.util.ArrayList;

public class PayrollSystem {

    private ArrayList<Employee> employeeList;

    public PayrollSystem() {
        employeeList = new ArrayList<>();
    }

    public void addEmployee(Employee employee){
        employeeList.add(employee);

    }
    public void removeEmployee(int id){
        Employee employeeToRemove = null;
        for(Employee employee : employeeList){
            if(employee.getId() == id){
                employeeToRemove = employee;
                break;
            }
        }
        if(employeeToRemove != null){
            employeeList.remove(employeeToRemove);
        }

    }

    public void displayEmployees(){
        for(Employee employee : employeeList){
            System.out.println("Employee ID     : " + employee.getId() +"\nEmployee name   : " + employee.getName() + "\nEmployee Salary : " + employee.calculateSalary());
            System.out.println();
//            System.out.println(employee);
        }
    }


}
