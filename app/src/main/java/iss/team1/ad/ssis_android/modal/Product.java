package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String description;
    private int reorderLvl;
    private int reorderQty;
    private String uom;
    private Category category;

    public Product() { }

    public Product(String id) {
        this.id = id;
    }

    public Product(String id, String description, int reorderLvl, int reorderQty, String uom, Category category) {
        this.id = id;
        this.description = description;
        this.reorderLvl = reorderLvl;
        this.reorderQty = reorderQty;
        this.uom = uom;
        this.category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReorderLvl() {
        return reorderLvl;
    }

    public void setReorderLvl(int reorderLvl) {
        this.reorderLvl = reorderLvl;
    }

    public int getReorderQty() {
        return reorderQty;
    }

    public void setReorderQty(int reorderQty) {
        this.reorderQty = reorderQty;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
