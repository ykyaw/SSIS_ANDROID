package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;
import java.util.List;

public class Employee implements Serializable {
    
    public int id;
    public String name;
    public String email;
    public String password;
    public int phoneNo;
    public String departmentId;
    public int managerId;
    public long delegateFromDate;
    public long delegateToDate;

    public String role;

    public  Department department;


    /*One manager multiple employee relationship*/
    public  Employee manager;
    public  List<Employee> employees;



    /* The InverseProperty attribute is used when two entities have more than one relationship, in this case is Requsition and Employee */

    public  List<Requisition> requestedrequsition;

    public  List<Requisition> approvedrequsition;

    public  List<Requisition> processedrequsition;

    public  List<Requisition> receivedrequsitions;

    public  List<Requisition> acknowledgedrequsition;

    public  List<Transaction> transactions;

    public Employee(int id, String name, String email, String password, int phoneNo, String departmentId, int managerId, long delegateFromDate, long delegateToDate, String role, Department department, Employee manager, List<Employee> employees, List<Requisition> requestedrequsition, List<Requisition> approvedrequsition, List<Requisition> processedrequsition, List<Requisition> receivedrequsitions, List<Requisition> acknowledgedrequsition, List<Transaction> transactions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;
        this.departmentId = departmentId;
        this.managerId = managerId;
        this.delegateFromDate = delegateFromDate;
        this.delegateToDate = delegateToDate;
        this.role = role;
        this.department = department;
        this.manager = manager;
        this.employees = employees;
        this.requestedrequsition = requestedrequsition;
        this.approvedrequsition = approvedrequsition;
        this.processedrequsition = processedrequsition;
        this.receivedrequsitions = receivedrequsitions;
        this.acknowledgedrequsition = acknowledgedrequsition;
        this.transactions = transactions;
    }

    public Employee(int id) {
        this.id = id;
    }

    public Employee() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public long getDelegateFromDate() {
        return delegateFromDate;
    }

    public void setDelegateFromDate(long delegateFromDate) {
        this.delegateFromDate = delegateFromDate;
    }

    public long getDelegateToDate() {
        return delegateToDate;
    }

    public void setDelegateToDate(long delegateToDate) {
        this.delegateToDate = delegateToDate;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getManager() {
        return manager;
    }

    public void setManager(Employee manager) {
        this.manager = manager;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Requisition> getRequestedrequsition() {
        return requestedrequsition;
    }

    public void setRequestedrequsition(List<Requisition> requestedrequsition) {
        this.requestedrequsition = requestedrequsition;
    }

    public List<Requisition> getApprovedrequsition() {
        return approvedrequsition;
    }

    public void setApprovedrequsition(List<Requisition> approvedrequsition) {
        this.approvedrequsition = approvedrequsition;
    }

    public List<Requisition> getProcessedrequsition() {
        return processedrequsition;
    }

    public void setProcessedrequsition(List<Requisition> processedrequsition) {
        this.processedrequsition = processedrequsition;
    }

    public List<Requisition> getReceivedrequsitions() {
        return receivedrequsitions;
    }

    public void setReceivedrequsitions(List<Requisition> receivedrequsitions) {
        this.receivedrequsitions = receivedrequsitions;
    }

    public List<Requisition> getAcknowledgedrequsition() {
        return acknowledgedrequsition;
    }

    public void setAcknowledgedrequsition(List<Requisition> acknowledgedrequsition) {
        this.acknowledgedrequsition = acknowledgedrequsition;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
