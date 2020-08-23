package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;
import java.util.List;

public class AdjustmentVoucher implements Serializable {
    public String Id;
    public int InitiatedClerkId;
    public long InitiatedDate;
    public int ApprovedSupId;
    public long ApprovedSupDate;
    public int ApprovedMgrId;
    public long ApprovedMgrDate;
    public String Status;
    public String Reason ;

    public  Employee InitiatedClerk ;
    public  Employee ApprovedSup;
    public  Employee ApprovedMgr;
    public List<AdjustmentVoucherDetail> AdjustmentVoucherDetails;


    public AdjustmentVoucher() {
    }


    public AdjustmentVoucher(String id, int initiatedClerkId, long initiatedDate, String status) {
        Id = id;
        InitiatedClerkId = initiatedClerkId;
        InitiatedDate = initiatedDate;
        Status = status;
    }


    public AdjustmentVoucher(String id, int initiatedClerkId, long initiatedDate, int approvedSupId, long approvedSupDate, String status, String reason) {
        Id = id;
        InitiatedClerkId = initiatedClerkId;
        InitiatedDate = initiatedDate;
        ApprovedSupId = approvedSupId;
        ApprovedSupDate = approvedSupDate;
        Status = status;
        Reason = reason;
    }

    public AdjustmentVoucher(String id, int initiatedClerkId, long initiatedDate, int approvedSupId, long approvedSupDate, int approvedMgrId, long approvedMgrDate, String status, String reason, Employee initiatedClerk, Employee approvedSup, Employee approvedMgr) {
        Id = id;
        InitiatedClerkId = initiatedClerkId;
        InitiatedDate = initiatedDate;
        ApprovedSupId = approvedSupId;
        ApprovedSupDate = approvedSupDate;
        ApprovedMgrId = approvedMgrId;
        ApprovedMgrDate = approvedMgrDate;
        Status = status;
        Reason = reason;
        InitiatedClerk = initiatedClerk;
        ApprovedSup = approvedSup;
        ApprovedMgr = approvedMgr;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public int getInitiatedClerkId() {
        return InitiatedClerkId;
    }

    public void setInitiatedClerkId(int initiatedClerkId) {
        InitiatedClerkId = initiatedClerkId;
    }

    public long getInitiatedDate() {
        return InitiatedDate;
    }

    public void setInitiatedDate(long initiatedDate) {
        InitiatedDate = initiatedDate;
    }

    public int getApprovedSupId() {
        return ApprovedSupId;
    }

    public void setApprovedSupId(int approvedSupId) {
        ApprovedSupId = approvedSupId;
    }

    public long getApprovedSupDate() {
        return ApprovedSupDate;
    }

    public void setApprovedSupDate(long approvedSupDate) {
        ApprovedSupDate = approvedSupDate;
    }

    public int getApprovedMgrId() {
        return ApprovedMgrId;
    }

    public void setApprovedMgrId(int approvedMgrId) {
        ApprovedMgrId = approvedMgrId;
    }

    public long getApprovedMgrDate() {
        return ApprovedMgrDate;
    }

    public void setApprovedMgrDate(long approvedMgrDate) {
        ApprovedMgrDate = approvedMgrDate;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public Employee getInitiatedClerk() {
        return InitiatedClerk;
    }

    public void setInitiatedClerk(Employee initiatedClerk) {
        InitiatedClerk = initiatedClerk;
    }

    public Employee getApprovedSup() {
        return ApprovedSup;
    }

    public void setApprovedSup(Employee approvedSup) {
        ApprovedSup = approvedSup;
    }

    public Employee getApprovedMgr() {
        return ApprovedMgr;
    }

    public void setApprovedMgr(Employee approvedMgr) {
        ApprovedMgr = approvedMgr;
    }

    public List<AdjustmentVoucherDetail> getAdjustmentVoucherDetails() {
        return AdjustmentVoucherDetails;
    }

    public void setAdjustmentVoucherDetails(List<AdjustmentVoucherDetail> adjustmentVoucherDetails) {
        AdjustmentVoucherDetails = adjustmentVoucherDetails;
    }
}
