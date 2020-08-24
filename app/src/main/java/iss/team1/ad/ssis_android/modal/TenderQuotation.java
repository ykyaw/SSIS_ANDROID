package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;

public class TenderQuotation implements Serializable {

    public int id;
    public String supplierId;
    public int year;

    public String productId;
    public double unitprice;
    public String uom;
    public int rank;
    public  Supplier supplier;
    public  Product product;

    public TenderQuotation() {
    }

    public TenderQuotation(int id) {
        this.id = id;
    }

    public TenderQuotation(int id, String supplierId, int year, String productId, double unitprice, String uom, int rank, Supplier supplier, Product product) {
        this.id = id;
        this.supplierId = supplierId;
        this.year = year;
        this.productId = productId;
        this.unitprice = unitprice;
        this.uom = uom;
        this.rank = rank;
        this.supplier = supplier;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
