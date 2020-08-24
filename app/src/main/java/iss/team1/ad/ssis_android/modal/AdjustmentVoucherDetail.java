package iss.team1.ad.ssis_android.modal;

import java.io.Serializable;

public class AdjustmentVoucherDetail implements Serializable {
    public int Id;
    public String AdjustmentVoucherId;
    public String ProductId;
    public int QtyAdjusted;
    public double TotalPrice;
    public double Unitprice;
    public String Reason;
    public  AdjustmentVoucher AdjustmentVoucher;
    public  Product Product;

    public AdjustmentVoucherDetail() {
    }

    public AdjustmentVoucherDetail(int id, String adjustmentVoucherId, String productId, int qtyAdjusted, double totalPrice, double unitprice, String reason, iss.team1.ad.ssis_android.modal.AdjustmentVoucher adjustmentVoucher, iss.team1.ad.ssis_android.modal.Product product) {
        Id = id;
        AdjustmentVoucherId = adjustmentVoucherId;
        ProductId = productId;
        QtyAdjusted = qtyAdjusted;
        TotalPrice = totalPrice;
        Unitprice = unitprice;
        Reason = reason;
        AdjustmentVoucher = adjustmentVoucher;
        Product = product;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getAdjustmentVoucherId() {
        return AdjustmentVoucherId;
    }

    public void setAdjustmentVoucherId(String adjustmentVoucherId) {
        AdjustmentVoucherId = adjustmentVoucherId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public int getQtyAdjusted() {
        return QtyAdjusted;
    }

    public void setQtyAdjusted(int qtyAdjusted) {
        QtyAdjusted = qtyAdjusted;
    }

    public double getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        TotalPrice = totalPrice;
    }

    public double getUnitprice() {
        return Unitprice;
    }

    public void setUnitprice(double unitprice) {
        Unitprice = unitprice;
    }

    public String getReason() {
        return Reason;
    }

    public void setReason(String reason) {
        Reason = reason;
    }

    public iss.team1.ad.ssis_android.modal.AdjustmentVoucher getAdjustmentVoucher() {
        return AdjustmentVoucher;
    }

    public void setAdjustmentVoucher(iss.team1.ad.ssis_android.modal.AdjustmentVoucher adjustmentVoucher) {
        AdjustmentVoucher = adjustmentVoucher;
    }

    public iss.team1.ad.ssis_android.modal.Product getProduct() {
        return Product;
    }

    public void setProduct(iss.team1.ad.ssis_android.modal.Product product) {
        Product = product;
    }
}
