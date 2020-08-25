package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;
import java.util.List;

public class Requisition implements Serializable {
    private int id;
    private String departmentId;
    private int reqByEmpId;
    private long submittedDate;
    private int approvedById;
    private long approvalDate;
    private String remarks;
    private int processedByClerkId;
    private long createdDate;
    private String status;
    private int collectionPointId;
    private long collectionDate;
    private int receivedByRepId;
    private long receivedDate;
    private int ackByClerkId;
    private long ackDate;

    // FKs
    private Department department;
    private Employee reqByEmp;
    private Employee approvedBy;
    private Employee processedByClerk;
    private Employee receivedByRep;
    private Employee ackByClerk;
    private CollectionPoint collectionPoint;
    private List<RequisitionDetail> requisitionDetails;

    public Requisition() {
    }

    public Requisition(int id) {
        this.id = id;
    }

    public Requisition(int id, String departmentId, int reqByEmpId, long submittedDate, int approvedById, long approvalDate, String remarks, int processedByClerkId, long createdDate, String status, int collectionPointId, long collectionDate, int receivedByRepId, long receivedDate, int ackByClerkId, long ackDate, Department department, Employee reqByEmp, Employee approvedBy, Employee processedByClerk, Employee receivedByRep, Employee ackByClerk, CollectionPoint collectionPoint, List<RequisitionDetail> requisitionDetails) {
        this.id = id;
        this.departmentId = departmentId;
        this.reqByEmpId = reqByEmpId;
        this.submittedDate = submittedDate;
        this.approvedById = approvedById;
        this.approvalDate = approvalDate;
        this.remarks = remarks;
        this.processedByClerkId = processedByClerkId;
        this.createdDate = createdDate;
        this.status = status;
        this.collectionPointId = collectionPointId;
        this.collectionDate = collectionDate;
        this.receivedByRepId = receivedByRepId;
        this.receivedDate = receivedDate;
        this.ackByClerkId = ackByClerkId;
        this.ackDate = ackDate;
        this.department = department;
        this.reqByEmp = reqByEmp;
        this.approvedBy = approvedBy;
        this.processedByClerk = processedByClerk;
        this.receivedByRep = receivedByRep;
        this.ackByClerk = ackByClerk;
        this.collectionPoint = collectionPoint;
        this.requisitionDetails = requisitionDetails;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public int getReqByEmpId() {
        return reqByEmpId;
    }

    public void setReqByEmpId(int reqByEmpId) {
        this.reqByEmpId = reqByEmpId;
    }

    public long getSubmittedDate() {
        return submittedDate;
    }

    public void setSubmittedDate(long submittedDate) {
        this.submittedDate = submittedDate;
    }

    public int getApprovedById() {
        return approvedById;
    }

    public void setApprovedById(int approvedById) {
        this.approvedById = approvedById;
    }

    public long getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(long approvalDate) {
        this.approvalDate = approvalDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public int getProcessedByClerkId() {
        return processedByClerkId;
    }

    public void setProcessedByClerkId(int processedByClerkId) {
        this.processedByClerkId = processedByClerkId;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCollectionPointId() {
        return collectionPointId;
    }

    public void setCollectionPointId(int collectionPointId) {
        this.collectionPointId = collectionPointId;
    }

    public long getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(long collectionDate) {
        this.collectionDate = collectionDate;
    }

    public int getReceivedByRepId() {
        return receivedByRepId;
    }

    public void setReceivedByRepId(int receivedByRepId) {
        this.receivedByRepId = receivedByRepId;
    }

    public long getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(long receivedDate) {
        this.receivedDate = receivedDate;
    }

    public int getAckByClerkId() {
        return ackByClerkId;
    }

    public void setAckByClerkId(int ackByClerkId) {
        this.ackByClerkId = ackByClerkId;
    }

    public long getAckDate() {
        return ackDate;
    }

    public void setAckDate(long ackDate) {
        this.ackDate = ackDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getReqByEmp() {
        return reqByEmp;
    }

    public void setReqByEmp(Employee reqByEmp) {
        this.reqByEmp = reqByEmp;
    }

    public Employee getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(Employee approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Employee getProcessedByClerk() {
        return processedByClerk;
    }

    public void setProcessedByClerk(Employee processedByClerk) {
        this.processedByClerk = processedByClerk;
    }

    public Employee getReceivedByRep() {
        return receivedByRep;
    }

    public void setReceivedByRep(Employee receivedByRep) {
        this.receivedByRep = receivedByRep;
    }

    public Employee getAckByClerk() {
        return ackByClerk;
    }

    public void setAckByClerk(Employee ackByClerk) {
        this.ackByClerk = ackByClerk;
    }

    public CollectionPoint getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(CollectionPoint collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public List<RequisitionDetail> getRequisitionDetails() {
        return requisitionDetails;
    }

    public void setRequisitionDetails(List<RequisitionDetail> requisitionDetails) {
        this.requisitionDetails = requisitionDetails;
    }
}
