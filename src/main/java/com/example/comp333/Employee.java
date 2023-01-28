package com.example.comp333;

import java.util.Arrays;
import java.util.Date;

public class Employee {
    private int eid; //Primary Key
    private int employeeSSN;
    private String employeeFirstName;
    private String employeeFatherName;
    private String employeeFamilyName;
    private String employeeEmail;
    private String[] employeePhone; // not sure about it
    private Date employeeStartingDate;
    private Date employeeBirthDate;
    private double salary;
    private String password;

    public Employee(int eid, int employeeSSN, String employeeFristName, String employeeFatherName, String employeeFamilyName, String employeeEmail, String[] employeePhone, Date employeeStartingDate, Date employeeBirthDate, double salary, String password) {

        this.eid = eid;
        this.employeeSSN = employeeSSN;
        this.employeeFirstName = employeeFristName;
        this.employeeFatherName = employeeFatherName;
        this.employeeFamilyName = employeeFamilyName;
        this.employeeEmail = employeeEmail;
        this.employeePhone = employeePhone;
        this.employeeStartingDate = employeeStartingDate;
        this.employeeBirthDate = employeeBirthDate;
        this.salary = salary;
        this.password = password;
    }

    public int getEid() {
        return eid;
    }

    public void setEid(int eid) {
        this.eid = eid;
    }

    public int getEmployeeSSN() {
        return employeeSSN;
    }

    public void setEmployeeSSN(int employeeSSN) {
        this.employeeSSN = employeeSSN;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeeFatherName() {
        return employeeFatherName;
    }

    public void setEmployeeFatherName(String employeeFatherName) {
        this.employeeFatherName = employeeFatherName;
    }

    public String getEmployeeFamilyName() {
        return employeeFamilyName;
    }

    public void setEmployeeFamilyName(String employeeFamilyName) {
        this.employeeFamilyName = employeeFamilyName;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String[] getEmployeePhone() {
        return employeePhone;
    }

    public void setEmployeePhone(String[] employeePhone) {
        this.employeePhone = employeePhone;
    }

    public Date getEmployeeStartingDate() {
        return employeeStartingDate;
    }

    public void setEmployeeStartingDate(Date employeeStartingDate) {
        this.employeeStartingDate = employeeStartingDate;
    }

    public Date getEmployeeBirthDate() {
        return employeeBirthDate;
    }

    public void setEmployeeBirthDate(Date employeeBirthDate) {
        this.employeeBirthDate = employeeBirthDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eid=" + eid +
                ", employeeSSN=" + employeeSSN +
                ", employeeFristName='" + employeeFirstName + '\'' +
                ", employeeFatherName='" + employeeFatherName + '\'' +
                ", employeeFamilyName='" + employeeFamilyName + '\'' +
                ", employeeEmail='" + employeeEmail + '\'' +
                ", employeePhone=" + Arrays.toString(employeePhone) +
                ", employeeStartingDate=" + employeeStartingDate +
                ", employeeBirthDate=" + employeeBirthDate +
                ", salary=" + salary +
                ", password='" + password + '\'' +
                '}';
    }
}
