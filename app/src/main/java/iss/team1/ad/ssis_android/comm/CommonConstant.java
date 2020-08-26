package iss.team1.ad.ssis_android.comm;

import iss.team1.ad.ssis_android.modal.AdjustmentVoucher;

public class CommonConstant {

    public static class HttpUrl{
        private static final String root="http://192.168.8.75:51769/";
        public static final String LOGIN=root+"Login/Verify";
        public static final String TEST=root+"Login/Index";
        public static String GENERATE_RETRIEVAL_FORM(long date){
            return root+"storeclerk/ret/"+date;
        };
        public static final String GET_ALL_DEPARTMENT = root+"storeclerk/retrievealldept";
        public static final String GET_DISBURSEMENT =root+"storeclerk/disbursement" ;
        public static String GET_DEPT_DISBURSEMENT_DETAIL(long date){
            return root+"deptemp/dis/"+date;
        };
        public static final String ACK_DISBURSEMENT = root+"deptemp/ack";
        public static final String GET_DEPT_EMP = root+"depthead/gae";
        public static final String ASSIGN_DELEGATE_EMP =root+"depthead/del" ;
        public static final String GET_DEPT_HEAD_REQUISITIONS = root+"depthead/rfl";

        public static String GET_DEPT_HEAD_REQUISITIONS_DEATIL(String id) {
            return root+"depthead/rfld/"+id;
        };
        public static final String DEPT_HEAD_UPDATE_REQUISITION_DETAIL = root+"depthead/arr";
        public static final String Get_All_ADJUSTMENT_VOUCHERS=root+"storesup/allvoucher";
        public static final String PURCHASE_REQUEST = root+"storeclerk/pr";

        public static String PURCHASE_REQUEST_DETAIL(String id) {
            return root+"storeclerk/prdetails/"+id;
        }
        public static String GET_PRODUCT_SUPPLIER(String id){
            return root+"storeclerk/supplier/"+id;
        }
        public static final String UPDATE_PURCHASE_REQUEST = root+"storesup/updatepr";
        public static final String UPDATE_RETRIEVAL = root+"storeclerk/ret";
        public static final String Get_AV_DETAIL_BY_AVID(String avid){return root+"storesup/voucher/"+avid;}
        public static final String APPRO_REJ_ADJUSTMENT_VOUCHER(AdjustmentVoucher adjustmentvoucher){return root+"storesup/voucher/"+1;}
        public static final String ACK_DISBURSEMENT_COMPLETION=root+"storeclerk/ackreq";
    }

    public static class ROLE
    {
        public static final String STORE_CLERK = "sc";
        public static final String STORE_SUPERVISOR = "ss";
        public static final String STORE_MANAGER = "sm";
        public static final String DEPARTMENT_HEAD = "dh";
        public static final String DEPARTMENT_EMPLOYEE = "de";
    }

    public static class RequsitionStatus
    {
        public static final String CREATED = "Created"; //upon creation before approval
        public static final String PENDING_APPROVAL = "Pending Approval"; //after submit for approval
        public static final String APPROVED = "Approved"; // after approved, before clerk enters a disbursement time
        public static final String REJECT = "Rejected";
        public static final String CONFIRMED = "Confirmed"; //after approved, and after clerk entered a disbursement time
        public static final String RECEIVED = "Received"; // after department rep received at collection point, and pressed the button to indicate he received. This will trigger the button for clerk to click acknowledged
        public static final String COMPLETED = "Completed"; //after clerk click acknowledged, requsition status is completed
    }

    public static class PurchaseRequestStatus {
        public static final String CREATED = "Created"; //upon creation before approval
        public static final String PENDING_APPROVAL = "Pending Approval"; //after submit for approval
        public static final String REJECTED = "Rejected";
        public static final String APPROVED = "Approved";
    }

    public static class RetrievalStatus {
        public static final String CREATED = "Created"; //upon creation of retrieval form
        public static final String RETRIEVED = "Retrieved"; //after clicking retrieved
    }

    public static class AdjsutmentVoucherStatus
    {
        public static final String CREATED = "Created"; //upon creation before approval
        public static final String PENDING_APPROVAL = "Pending Approval"; //upon creation, but not approved by anyone
        public static final String PENDMANAPPROV="Pending Manager Approval"; //if anything more than $250, and has been approved by supervisor but not yet approved by manager
        public static final String APPROVED = "Approved"; // if anything <250 and approved by supervisor, OR if anything more >250 and both supervisor and manager approved
        public static final String REJECTED = "Rejected";
    }
}
