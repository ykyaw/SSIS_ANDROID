package iss.team1.ad.ssis_android.comm;

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
    }

    public static class ROLE
    {
        public static final String STORE_CLERK = "sc";
        public static final String STORE_SUPERVISOR = "ss";
        public static final String STORE_MANAGER = "sm";
        public static final String DEPARTMENT_HEAD = "dh";
        public static final String DEPARTMENT_EMPLOYEE = "de";
    }
}
