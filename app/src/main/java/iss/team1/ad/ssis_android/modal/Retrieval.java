package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;
import java.util.List;

public class Retrieval implements Serializable {
    public int Id;
    public long DisbursedDate;
    public long RetrievedDate;
    public String Status;
    public String Remark;
    public boolean NeedAdjustment;
    public List<RequisitionDetail> RequisitionDetails;

    public Employee Clerk;

    public Retrieval() {
    }

    public Retrieval(int id) {
        Id = id;
    }

    public Retrieval(int id, long disbursedDate, long retrievedDate, String status, String remark, boolean needAdjustment, List<RequisitionDetail> requisitionDetails, Employee clerk) {
        Id = id;
        DisbursedDate = disbursedDate;
        RetrievedDate = retrievedDate;
        Status = status;
        Remark = remark;
        NeedAdjustment = needAdjustment;
        RequisitionDetails = requisitionDetails;
        Clerk = clerk;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public long getDisbursedDate() {
        return DisbursedDate;
    }

    public void setDisbursedDate(long disbursedDate) {
        DisbursedDate = disbursedDate;
    }

    public long getRetrievedDate() {
        return RetrievedDate;
    }

    public void setRetrievedDate(long retrievedDate) {
        RetrievedDate = retrievedDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public boolean isNeedAdjustment() {
        return NeedAdjustment;
    }

    public void setNeedAdjustment(boolean needAdjustment) {
        NeedAdjustment = needAdjustment;
    }

    public List<RequisitionDetail> getRequisitionDetails() {
        return RequisitionDetails;
    }

    public void setRequisitionDetails(List<RequisitionDetail> requisitionDetails) {
        RequisitionDetails = requisitionDetails;
    }

    public Employee getClerk() {
        return Clerk;
    }

    public void setClerk(Employee clerk) {
        Clerk = clerk;
    }
}
