package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;

public class CollectionPoint implements Serializable {
    
    public int id;
    public String location;
    public String collectionTime; // 9:30AM, 11:00AM

    public CollectionPoint() { }

    public CollectionPoint(int id) {
        this.id = id;
    }

    public CollectionPoint(int id, String location, String collectionTime) {
        this.id = id;
        this.location = location;
        this.collectionTime = collectionTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }
}
