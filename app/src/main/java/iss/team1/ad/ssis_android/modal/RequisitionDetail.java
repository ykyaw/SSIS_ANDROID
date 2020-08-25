package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;

public class RequisitionDetail implements Serializable {
    private int id;
    private int requisitionId;
    private String productId;
    private int qtyNeeded;
    private int qtyDisbursed;
    private int qtyReceived;
    private String disburseRemark;
    private String repRemark;
    private String clerkRemark;
    private int retrievalId;
    private  Requisition requisition;
    private  Product product;
    private  Retrieval retrieval;

    public RequisitionDetail() {
    }

    public RequisitionDetail(int id) {
        this.id = id;
    }

    public RequisitionDetail(int id, int requisitionId, String productId, int qtyNeeded, int qtyDisbursed, int qtyReceived, String disburseRemark, String repRemark, String clerkRemark, int retrievalId, Requisition requisition, Product product, Retrieval retrieval) {
        this.id = id;
        this.requisitionId = requisitionId;
        this.productId = productId;
        this.qtyNeeded = qtyNeeded;
        this.qtyDisbursed = qtyDisbursed;
        this.qtyReceived = qtyReceived;
        this.disburseRemark = disburseRemark;
        this.repRemark = repRemark;
        this.clerkRemark = clerkRemark;
        this.retrievalId = retrievalId;
        this.requisition = requisition;
        this.product = product;
        this.retrieval = retrieval;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRequisitionId() {
        return requisitionId;
    }

    public void setRequisitionId(int requisitionId) {
        this.requisitionId = requisitionId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQtyNeeded() {
        return qtyNeeded;
    }

    public void setQtyNeeded(int qtyNeeded) {
        this.qtyNeeded = qtyNeeded;
    }

    public int getQtyDisbursed() {
        return qtyDisbursed;
    }

    public void setQtyDisbursed(int qtyDisbursed) {
        this.qtyDisbursed = qtyDisbursed;
    }

    public int getQtyReceived() {
        return qtyReceived;
    }

    public void setQtyReceived(int qtyReceived) {
        this.qtyReceived = qtyReceived;
    }

    public String getDisburseRemark() {
        return disburseRemark;
    }

    public void setDisburseRemark(String disburseRemark) {
        this.disburseRemark = disburseRemark;
    }

    public String getRepRemark() {
        return repRemark;
    }

    public void setRepRemark(String repRemark) {
        this.repRemark = repRemark;
    }

    public String getClerkRemark() {
        return clerkRemark;
    }

    public void setClerkRemark(String clerkRemark) {
        this.clerkRemark = clerkRemark;
    }

    public int getRetrievalId() {
        return retrievalId;
    }

    public void setRetrievalId(int retrievalId) {
        this.retrievalId = retrievalId;
    }

    public Requisition getRequisition() {
        return requisition;
    }

    public void setRequisition(Requisition requisition) {
        this.requisition = requisition;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Retrieval getRetrieval() {
        return retrieval;
    }

    public void setRetrieval(Retrieval retrieval) {
        this.retrieval = retrieval;
    }
}
