package com.example.jason.mooo;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.view.LayoutInflater;

/**
 * Created by alex on 12/09/2015.
 */




public class loading_splash {

    Builder builder;
    LayoutInflater layoutInflater;
    protected  static AlertDialog alert;
    boolean splashStatus;

    public loading_splash(Builder builder, LayoutInflater layoutInflater) {
        this.builder = builder;
        this.layoutInflater = layoutInflater;
        splashStatus = false;
    }

    public void setView(){
        builder.setView(layoutInflater.inflate(R.layout.loading_splash, null));

    }

    public  void setCancel(){
        builder.setCancelable(false);
    }

    public void create(){
        alert = builder.create();

    }

    public  void show(){
        alert.show();
    }

    public void setSplashStatus(boolean b) {
        splashStatus = b;
    }
    public void cancel(){
        alert.cancel();
        splashStatus = false;
    }

    public void dismiss(){
        alert.dismiss();

    }

    public void start_splash(){
        setView();
        setCancel();
        create();
        show();
        setSplashStatus(true);
    }

    public boolean isShowing(){
        return alert.isShowing();
    }

}
