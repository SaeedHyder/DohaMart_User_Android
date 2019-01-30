package com.ingic.ezhalbatek.entities.ServiceStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ServicesStatus {

    @SerializedName("Subscription")
    @Expose
    private ArrayList<Subscription> subscription = new ArrayList<>();
    @SerializedName("Service")
    @Expose
    private ArrayList<Service> service =  new ArrayList<>();

    public ArrayList<Subscription> getSubscription() {
        return subscription;
    }

    public void setSubscription(ArrayList<Subscription> subscription) {
        this.subscription = subscription;
    }

    public ArrayList<Service> getService() {
        return service;
    }

    public void setService(ArrayList<Service> service) {
        this.service = service;
    }
}
