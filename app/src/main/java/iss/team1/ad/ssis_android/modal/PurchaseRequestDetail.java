package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;

public class PurchaseRequestDetail implements Serializable {
    public int id;
    public long purchaseRequestId; //timestamp
    public int createdByClerkId;
    public String productId;
    public String supplierId;
    public int currentStock;
    public int reorderQty;
    public String venderQuote; //Can't submit if null
    public double totalPrice;
    public long submitDate;
    public long approvedDate;
    public int approvedBySupId;
    public String status;
    public String remarks;

    public Product product;
    public  Employee createdByClerk;
    public  Supplier supplier;
    public  Employee approvedBySup;

    public PurchaseRequestDetail() {
    }

    public PurchaseRequestDetail(int id) {
        this.id = id;
    }

    public PurchaseRequestDetail(int id, long purchaseRequestId, int createdByClerkId, String productId, String supplierId, int currentStock, int reorderQty, String venderQuote, double totalPrice, long submitDate, long approvedDate, int approvedBySupId, String status, String remarks, Product rroduct, Employee createdByClerk, Supplier supplier, Employee approvedBySup) {
        this.id = id;
        this.purchaseRequestId = purchaseRequestId;
        this.createdByClerkId = createdByClerkId;
        this.productId = productId;
        this.supplierId = supplierId;
        this.currentStock = currentStock;
        this.reorderQty = reorderQty;
        this.venderQuote = venderQuote;
        this.totalPrice = totalPrice;
        this.submitDate = submitDate;
        this.approvedDate = approvedDate;
        this.approvedBySupId = approvedBySupId;
        this.status = status;
        this.remarks = remarks;
        this.product = rroduct;
        this.createdByClerk = createdByClerk;
        this.supplier = supplier;
        this.approvedBySup = approvedBySup;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPurchaseRequestId() {
        return purchaseRequestId;
    }

    public void setPurchaseRequestId(long purchaseRequestId) {
        this.purchaseRequestId = purchaseRequestId;
    }

    public int getCreatedByClerkId() {
        return createdByClerkId;
    }

    public void setCreatedByClerkId(int createdByClerkId) {
        this.createdByClerkId = createdByClerkId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public void setCurrentStock(int currentStock) {
        this.currentStock = currentStock;
    }

    public int getReorderQty() {
        return reorderQty;
    }

    public void setReorderQty(int reorderQty) {
        this.reorderQty = reorderQty;
    }

    public String getVenderQuote() {
        return venderQuote;
    }

    public void setVenderQuote(String venderQuote) {
        this.venderQuote = venderQuote;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(long submitDate) {
        this.submitDate = submitDate;
    }

    public long getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(long approvedDate) {
        this.approvedDate = approvedDate;
    }

    public int getApprovedBySupId() {
        return approvedBySupId;
    }

    public void setApprovedBySupId(int approvedBySupId) {
        this.approvedBySupId = approvedBySupId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Employee getCreatedByClerk() {
        return createdByClerk;
    }

    public void setCreatedByClerk(Employee createdByClerk) {
        this.createdByClerk = createdByClerk;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Employee getApprovedBySup() {
        return approvedBySup;
    }

    public void setApprovedBySup(Employee approvedBySup) {
        this.approvedBySup = approvedBySup;
    }
}
