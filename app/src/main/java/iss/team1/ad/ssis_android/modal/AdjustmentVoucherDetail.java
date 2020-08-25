package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;

public class AdjustmentVoucherDetail implements Serializable {
    public int id;
    public String adjustmentVoucherId;
    public String productId;
    public int qtyAdjusted;
    public double totalPrice;
    public double unitprice;
    public String reason;
    public  AdjustmentVoucher adjustmentVoucher;
    public  Product product;

    public AdjustmentVoucherDetail() {
    }

    public AdjustmentVoucherDetail(int id) {
        this.id = id;
    }

    public AdjustmentVoucherDetail(int id, String adjustmentVoucherId, String productId, int qtyAdjusted, double totalPrice, double unitprice, String reason, AdjustmentVoucher adjustmentVoucher, Product product) {
        this.id = id;
        this.adjustmentVoucherId = adjustmentVoucherId;
        this.productId = productId;
        this.qtyAdjusted = qtyAdjusted;
        this.totalPrice = totalPrice;
        this.unitprice = unitprice;
        this.reason = reason;
        this.adjustmentVoucher = adjustmentVoucher;
        this.product = product;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdjustmentVoucherId() {
        return adjustmentVoucherId;
    }

    public void setAdjustmentVoucherId(String adjustmentVoucherId) {
        this.adjustmentVoucherId = adjustmentVoucherId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getQtyAdjusted() {
        return qtyAdjusted;
    }

    public void setQtyAdjusted(int qtyAdjusted) {
        this.qtyAdjusted = qtyAdjusted;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public double getUnitprice() {
        return unitprice;
    }

    public void setUnitprice(double unitprice) {
        this.unitprice = unitprice;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public AdjustmentVoucher getAdjustmentVoucher() {
        return adjustmentVoucher;
    }

    public void setAdjustmentVoucher(AdjustmentVoucher adjustmentVoucher) {
        this.adjustmentVoucher = adjustmentVoucher;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
