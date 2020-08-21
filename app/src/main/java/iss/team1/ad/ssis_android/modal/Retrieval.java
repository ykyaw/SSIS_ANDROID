package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;
import java.util.List;

public class Retrieval implements Serializable {
    public int id;
    public long disbursedDate;
    public long retrievedDate;
    public String status;
    public String remark;
    public boolean needAdjustment;
    public List<RequisitionDetail> requisitionDetails;

    public Employee clerk;

    public Retrieval() {
    }

    public Retrieval(int id) {
        this.id = id;
    }

    public Retrieval(int id, long disbursedDate, long retrievedDate, String status, String remark, boolean needAdjustment, List<RequisitionDetail> requisitionDetails, Employee clerk) {
        this.id = id;
        this.disbursedDate = disbursedDate;
        this.retrievedDate = retrievedDate;
        this.status = status;
        this.remark = remark;
        this.needAdjustment = needAdjustment;
        this.requisitionDetails = requisitionDetails;
        this.clerk = clerk;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getDisbursedDate() {
        return disbursedDate;
    }

    public void setDisbursedDate(long disbursedDate) {
        this.disbursedDate = disbursedDate;
    }

    public long getRetrievedDate() {
        return retrievedDate;
    }

    public void setRetrievedDate(long retrievedDate) {
        this.retrievedDate = retrievedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isNeedAdjustment() {
        return needAdjustment;
    }

    public void setNeedAdjustment(boolean needAdjustment) {
        this.needAdjustment = needAdjustment;
    }

    public List<RequisitionDetail> getRequisitionDetails() {
        return requisitionDetails;
    }

    public void setRequisitionDetails(List<RequisitionDetail> requisitionDetails) {
        this.requisitionDetails = requisitionDetails;
    }

    public Employee getClerk() {
        return clerk;
    }

    public void setClerk(Employee clerk) {
        this.clerk = clerk;
    }
}
