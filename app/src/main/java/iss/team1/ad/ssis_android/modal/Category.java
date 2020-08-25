package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
    private int id;
    private String name;
    private String binNo; // Same value as Id?

    private List<Product> products;

    public Category() {
    }

    public Category(int id) {
        this.id = id;
    }

    public Category(int id, String name, String binNo, List<Product> products) {
        this.id = id;
        this.name = name;
        this.binNo = binNo;
        this.products = products;
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

    public String getBinNo() {
        return binNo;
    }

    public void setBinNo(String binNo) {
        this.binNo = binNo;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
