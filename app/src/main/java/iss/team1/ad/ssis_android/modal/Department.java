package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;
import java.util.List;


public class Department implements Serializable {
    public String id ;
    public String name ;
    public int phoneNo ;
    public int faxNo ;
    public int repId ;

    public int headId ;
    public int collectionPointId ;
    public CollectionPoint collectionPoint ;
    public List<Employee> allEmp ;

    public Department() {
    }

    public Department(String id) {
        this.id = id;
    }

    public Department(String id, String name, int phoneNo, int faxNo, int repId, int headId, int collectionPointId, CollectionPoint collectionPoint, List<Employee> allEmp) {
        this.id = id;
        this.name = name;
        this.phoneNo = phoneNo;
        this.faxNo = faxNo;
        this.repId = repId;
        this.headId = headId;
        this.collectionPointId = collectionPointId;
        this.collectionPoint = collectionPoint;
        this.allEmp = allEmp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public int getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(int faxNo) {
        this.faxNo = faxNo;
    }

    public int getRepId() {
        return repId;
    }

    public void setRepId(int repId) {
        this.repId = repId;
    }

    public int getHeadId() {
        return headId;
    }

    public void setHeadId(int headId) {
        this.headId = headId;
    }

    public int getCollectionPointId() {
        return collectionPointId;
    }

    public void setCollectionPointId(int collectionPointId) {
        this.collectionPointId = collectionPointId;
    }

    public CollectionPoint getCollectionPoint() {
        return collectionPoint;
    }

    public void setCollectionPoint(CollectionPoint collectionPoint) {
        this.collectionPoint = collectionPoint;
    }

    public List<Employee> getAllEmp() {
        return allEmp;
    }

    public void setAllEmp(List<Employee> allEmp) {
        this.allEmp = allEmp;
    }
}
