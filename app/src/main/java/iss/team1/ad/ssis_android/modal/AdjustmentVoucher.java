package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;
import java.util.List;

public class AdjustmentVoucher implements Serializable {
    private String Id;
    private int InitiatedClerkId;
    private long InitiatedDate;
    private int ApprovedSupId;
    private long ApprovedSupDate;
    private int ApprovedMgrId;
    private long ApprovedMgrDate;
    private String Status;
    private String Reason ;

    private  Employee InitiatedClerk ;
    private  Employee ApprovedSup;
    private  Employee ApprovedMgr;
    private List<AdjustmentVoucherDetail> AdjustmentVoucherDetails;

    public AdjustmentVoucher() {
    }

    public AdjustmentVoucher(String id) {
        Id = id;
    }

    public AdjustmentVoucher(String id, int initiatedClerkId, long initiatedDate, int approvedSupId, long approvedSupDate, int approvedMgrId, long approvedMgrDate, String status, String reason, Employee initiatedClerk, Employee approvedSup, Employee approvedMgr, List<AdjustmentVoucherDetail> adjustmentVoucherDetails) {
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
        AdjustmentVoucherDetails = adjustmentVoucherDetails;
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
