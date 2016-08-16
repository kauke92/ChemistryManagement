package com.example.jason.mooo.Model;

/**
 * Created by Stuart on 19/09/2015.
 */
public class Purchase {

    private String drugName;
    private String purchasePlace;
    private int expireDate;
    private int purchaseDate;
    private String signedBy;
    private String batch;
    private int amount;
    private int amountLeft;
    private String id;

    public Purchase(String drug, String purchasePlace, int expireDate, int purchaseDate, String signedBy, String batch, int amount) {

        this.amount = amount;
        this.amountLeft = amount;
        this.expireDate = expireDate;
        this.purchaseDate = purchaseDate;
        this.purchasePlace = purchasePlace;
        this.signedBy = signedBy;
        drugName = drug;
        this.batch = batch;

    }

    public Purchase() {

    }


    public String getDrugName() { return drugName; }

    public String getPurchasePlace() { return purchasePlace; }

    public int getExpireDate() {
        return expireDate;
    }

    public int getPurchaseDate() {
        return purchaseDate;
    }

    public String getSignedBy() {
        return signedBy;
    }

    public String toString() {
        return (drugName + " - " + batch);
    }

    public String getBatch() { return batch; }

    public int getAmount() { return amount; }

    public int getAmountLeft() { return amountLeft; }

    public String getId() { return id; }
    }
