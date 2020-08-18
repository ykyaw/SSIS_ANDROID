package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;

public class Product implements Serializable {
    public String Id;
    public String Description;
    public int ReorderLvl;
    public int ReorderQty;
    public String Uom;
    public Category Category;

    public Product() { }

    public Product(String id) {
        Id = id;
    }

    public Product(String id, String description, int reorderLvl, int reorderQty, String uom, iss.team1.ad.ssis_android.modal.Category category) {
        Id = id;
        Description = description;
        ReorderLvl = reorderLvl;
        ReorderQty = reorderQty;
        Uom = uom;
        Category = category;
    }

    public Product(String id, String description) {
        Id = id;
        Description = description;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getReorderLvl() {
        return ReorderLvl;
    }

    public void setReorderLvl(int reorderLvl) {
        ReorderLvl = reorderLvl;
    }

    public int getReorderQty() {
        return ReorderQty;
    }

    public void setReorderQty(int reorderQty) {
        ReorderQty = reorderQty;
    }

    public String getUom() {
        return Uom;
    }

    public void setUom(String uom) {
        Uom = uom;
    }

    public iss.team1.ad.ssis_android.modal.Category getCategory() {
        return Category;
    }

    public void setCategory(iss.team1.ad.ssis_android.modal.Category category) {
        Category = category;
    }
}
