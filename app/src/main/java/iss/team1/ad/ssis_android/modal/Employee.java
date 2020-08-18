package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;
import java.util.List;

public class Employee implements Serializable {
    
    public int Id ;
    public String Name ;
    public String Email ;
    public String Password ;
    public int PhoneNo ;
    public int ManagerId ;
    public long DelegateFromDate ;
    public long DelegateToDate ;

    public String Role ;

    public  Department Department ;


    /*One manager multiple employee relationship*/
    public  Employee Manager ;
    public List<Employee> Employees ;

    public Employee() {
    }

    public Employee(int id) {
        Id = id;
    }

    public Employee(int id, String name, String email, String password, int phoneNo, int managerId, long delegateFromDate, long delegateToDate, String role, iss.team1.ad.ssis_android.modal.Department department, Employee manager, List<Employee> employees) {
        Id = id;
        Name = name;
        Email = email;
        Password = password;
        PhoneNo = phoneNo;
        ManagerId = managerId;
        DelegateFromDate = delegateFromDate;
        DelegateToDate = delegateToDate;
        Role = role;
        Department = department;
        Manager = manager;
        Employees = employees;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public int getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        PhoneNo = phoneNo;
    }

    public int getManagerId() {
        return ManagerId;
    }

    public void setManagerId(int managerId) {
        ManagerId = managerId;
    }

    public long getDelegateFromDate() {
        return DelegateFromDate;
    }

    public void setDelegateFromDate(long delegateFromDate) {
        DelegateFromDate = delegateFromDate;
    }

    public long getDelegateToDate() {
        return DelegateToDate;
    }

    public void setDelegateToDate(long delegateToDate) {
        DelegateToDate = delegateToDate;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public iss.team1.ad.ssis_android.modal.Department getDepartment() {
        return Department;
    }

    public void setDepartment(iss.team1.ad.ssis_android.modal.Department department) {
        Department = department;
    }

    public Employee getManager() {
        return Manager;
    }

    public void setManager(Employee manager) {
        Manager = manager;
    }

    public List<Employee> getEmployees() {
        return Employees;
    }

    public void setEmployees(List<Employee> employees) {
        Employees = employees;
    }

    /* The InverseProperty attribute is used when two entities have more than one relationship, in this case is Requsition and Employee */
//
//    public  ICollection<Requisition> Requestedrequsition ;
//
//    public  ICollection<Requisition> Approvedrequsition ;
//
//    public  ICollection<Requisition> Processedrequsition ;
//
//    public  ICollection<Requisition> Receivedrequsitions ;
//
//    public  ICollection<Requisition> Acknowledgedrequsition ;
//
//    public  ICollection<Transaction> Transactions ;
//
//    public Employee() { }
//    public Employee(String Name, String Email, String Password,String DepartmentId,String Role )
//    {
//        this.Name = Name;
//        this.Email = Email;
//        this.Password = Password;
//        this.DepartmentId = DepartmentId;
//        this.Role = Role;
//
//    }
}
