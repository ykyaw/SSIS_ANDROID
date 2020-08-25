package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;

public class Supplier implements Serializable {
    private String id;
    private String name;
    private String contactPersonName;
    private int phoneNo;
    private int faxNo;
    private String address;
    private String email;
    private String gstRegNo;

    public Supplier() {
    }

    public Supplier(String id) {
        this.id = id;
    }

    public Supplier(String id, String name, String contactPersonName, int phoneNo, int faxNo, String address, String email, String gstRegNo) {
        this.id = id;
        this.name = name;
        this.contactPersonName = contactPersonName;
        this.phoneNo = phoneNo;
        this.faxNo = faxNo;
        this.address = address;
        this.email = email;
        this.gstRegNo = gstRegNo;
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

    public String getContactPersonName() {
        return contactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        this.contactPersonName = contactPersonName;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGstRegNo() {
        return gstRegNo;
    }

    public void setGstRegNo(String gstRegNo) {
        this.gstRegNo = gstRegNo;
    }
}
