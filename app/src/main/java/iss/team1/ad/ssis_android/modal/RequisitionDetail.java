package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;

public class RequisitionDetail implements Serializable {
    public int Id;
    public int QtyNeeded;
    public int QtyDisbursed;
    public int QtyReceived;
    public String DisburseRemark;
    public String RepRemark;
    public String ClerkRemark;
    // FKs
    public Requisition Requisition;
    public Product Product;

    public RequisitionDetail() {
    }

    public RequisitionDetail(int id) {
        Id = id;
    }

    public RequisitionDetail(int qtyNeeded, iss.team1.ad.ssis_android.modal.Product product) {
        QtyNeeded = qtyNeeded;
        Product = product;
    }

    public RequisitionDetail(int id, int qtyNeeded, int qtyDisbursed, int qtyReceived, String disburseRemark, String repRemark, String clerkRemark, iss.team1.ad.ssis_android.modal.Requisition requisition, iss.team1.ad.ssis_android.modal.Product product) {
        Id = id;
        QtyNeeded = qtyNeeded;
        QtyDisbursed = qtyDisbursed;
        QtyReceived = qtyReceived;
        DisburseRemark = disburseRemark;
        RepRemark = repRemark;
        ClerkRemark = clerkRemark;
        Requisition = requisition;
        Product = product;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getQtyNeeded() {
        return QtyNeeded;
    }

    public void setQtyNeeded(int qtyNeeded) {
        QtyNeeded = qtyNeeded;
    }

    public int getQtyDisbursed() {
        return QtyDisbursed;
    }

    public void setQtyDisbursed(int qtyDisbursed) {
        QtyDisbursed = qtyDisbursed;
    }

    public int getQtyReceived() {
        return QtyReceived;
    }

    public void setQtyReceived(int qtyReceived) {
        QtyReceived = qtyReceived;
    }

    public String getDisburseRemark() {
        return DisburseRemark;
    }

    public void setDisburseRemark(String disburseRemark) {
        DisburseRemark = disburseRemark;
    }

    public String getRepRemark() {
        return RepRemark;
    }

    public void setRepRemark(String repRemark) {
        RepRemark = repRemark;
    }

    public String getClerkRemark() {
        return ClerkRemark;
    }

    public void setClerkRemark(String clerkRemark) {
        ClerkRemark = clerkRemark;
    }

    public iss.team1.ad.ssis_android.modal.Requisition getRequisition() {
        return Requisition;
    }

    public void setRequisition(iss.team1.ad.ssis_android.modal.Requisition requisition) {
        Requisition = requisition;
    }

    public iss.team1.ad.ssis_android.modal.Product getProduct() {
        return Product;
    }

    public void setProduct(iss.team1.ad.ssis_android.modal.Product product) {
        Product = product;
    }
}
