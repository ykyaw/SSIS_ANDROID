package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;

public class Transaction implements Serializable {

    private int id;
    private String productId;
    private long date;
    private String description;
    private int qty;
    private int balance;

    private int updatedByEmpId;
    private String refCode;
    private  Product product;
    private  Employee updatedByEmp;

    public Transaction() {
    }

    public Transaction(int id) {
        this.id = id;
    }

    public Transaction(int id, String productId, long date, String description, int qty, int balance, int updatedByEmpId, String refCode, Product product, Employee updatedByEmp) {
        this.id = id;
        this.productId = productId;
        this.date = date;
        this.description = description;
        this.qty = qty;
        this.balance = balance;
        this.updatedByEmpId = updatedByEmpId;
        this.refCode = refCode;
        this.product = product;
        this.updatedByEmp = updatedByEmp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getUpdatedByEmpId() {
        return updatedByEmpId;
    }

    public void setUpdatedByEmpId(int updatedByEmpId) {
        this.updatedByEmpId = updatedByEmpId;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Employee getUpdatedByEmp() {
        return updatedByEmp;
    }

    public void setUpdatedByEmp(Employee updatedByEmp) {
        this.updatedByEmp = updatedByEmp;
    }
}
