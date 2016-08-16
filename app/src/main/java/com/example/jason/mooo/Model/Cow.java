package com.example.jason.mooo.Model;

/**
 * Created by apple on 9/5/15.
 */
public class Cow {

    private String owner;
    String id;

    public  Cow()
    {

    }

    public  Cow(String id, String owner)
    {
        this.owner = owner;
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return id;
    }
}
