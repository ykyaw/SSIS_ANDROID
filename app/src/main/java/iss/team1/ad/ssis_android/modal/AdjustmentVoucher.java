package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;
import java.util.List;

public class AdjustmentVoucher implements Serializable {
    public String id;
    public int initiatedClerkId;
    public long initiatedDate;
    public int approvedSupId;
    public long approvedSupDate;
    public int approvedMgrId;
    public long approvedMgrDate;
    public String status;
    public String reason ;

    public  Employee initiatedClerk ;
    public  Employee approvedSup;
    public  Employee approvedMgr;
    public List<AdjustmentVoucherDetail> adjustmentVoucherDetails;
    public AdjustmentVoucher() {
    }

    public AdjustmentVoucher(String id, int initiatedClerkId, long initiatedDate, int approvedSupId, long approvedSupDate, int approvedMgrId, long approvedMgrDate, String status, String reason, Employee initiatedClerk, Employee approvedSup, Employee approvedMgr, List<AdjustmentVoucherDetail> adjustmentVoucherDetails) {
        this.id = id;
        this.initiatedClerkId = initiatedClerkId;
        this.initiatedDate = initiatedDate;
        this.approvedSupId = approvedSupId;
        this.approvedSupDate = approvedSupDate;
        this.approvedMgrId = approvedMgrId;
        this.approvedMgrDate = approvedMgrDate;
        this.status = status;
        this.reason = reason;
        this.initiatedClerk = initiatedClerk;
        this.approvedSup = approvedSup;
        this.approvedMgr = approvedMgr;
        this.adjustmentVoucherDetails = adjustmentVoucherDetails;
    }

    public AdjustmentVoucher(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getInitiatedClerkId() {
        return initiatedClerkId;
    }

    public void setInitiatedClerkId(int initiatedClerkId) {
        this.initiatedClerkId = initiatedClerkId;
    }

    public long getInitiatedDate() {
        return initiatedDate;
    }

    public void setInitiatedDate(long initiatedDate) {
        this.initiatedDate = initiatedDate;
    }

    public int getApprovedSupId() {
        return approvedSupId;
    }

    public void setApprovedSupId(int approvedSupId) {
        this.approvedSupId = approvedSupId;
    }

    public long getApprovedSupDate() {
        return approvedSupDate;
    }

    public void setApprovedSupDate(long approvedSupDate) {
        this.approvedSupDate = approvedSupDate;
    }

    public int getApprovedMgrId() {
        return approvedMgrId;
    }

    public void setApprovedMgrId(int approvedMgrId) {
        this.approvedMgrId = approvedMgrId;
    }

    public long getApprovedMgrDate() {
        return approvedMgrDate;
    }

    public void setApprovedMgrDate(long approvedMgrDate) {
        this.approvedMgrDate = approvedMgrDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Employee getInitiatedClerk() {
        return initiatedClerk;
    }

    public void setInitiatedClerk(Employee initiatedClerk) {
        this.initiatedClerk = initiatedClerk;
    }

    public Employee getApprovedSup() {
        return approvedSup;
    }

    public void setApprovedSup(Employee approvedSup) {
        this.approvedSup = approvedSup;
    }

    public Employee getApprovedMgr() {
        return approvedMgr;
    }

    public void setApprovedMgr(Employee approvedMgr) {
        this.approvedMgr = approvedMgr;
    }

    public List<AdjustmentVoucherDetail> getAdjustmentVoucherDetails() {
        return adjustmentVoucherDetails;
    }

    public void setAdjustmentVoucherDetails(List<AdjustmentVoucherDetail> adjustmentVoucherDetails) {
        this.adjustmentVoucherDetails = adjustmentVoucherDetails;
    }
}
