package com.example.jason.mooo.Model;


/**
 * Created by Stuart on 12/09/2015.
 */
public class Drug {

    private String name;
    private int withholdingSlaughter;
    private int withholdingMilk;

    public Drug()
    {
    }

    public Drug(String name, int ws, int wm) {

        this.name = name;
        this.withholdingMilk = wm;
        this.withholdingSlaughter = ws;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWithholdingSlaughter() {
        return withholdingSlaughter;
    }

    public void setWithholdingSlaughter(int withholdingSlaughter) {
        this.withholdingSlaughter = withholdingSlaughter;
    }

    public int getWithholdingMilk() {
        return withholdingMilk;
    }

    public void setWithholdingMilk(int withholdingMilk) {
        this.withholdingMilk = withholdingMilk;
    }



    public String toString() {
        return name;
    }

}