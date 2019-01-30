package com.ingic.ezhalbatek.entities.LoginModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Services {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("subscription_id")
    @Expose
    private Integer subscriptionId;
    @SerializedName("service_id")
    @Expose
    private Integer serviceId;
    @SerializedName("accessory_count")
    @Expose
    private Integer accessoryCount;
    @SerializedName("accessory_id")
    @Expose
    private Integer accessoryId;
    @SerializedName("is_unlimited")
    @Expose
    private Integer isUnlimited;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("service")
    @Expose
    private Service service;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(Integer subscriptionId) {
        this.subscriptionId = subscriptionId;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getAccessoryCount() {
        return accessoryCount;
    }

    public void setAccessoryCount(Integer accessoryCount) {
        this.accessoryCount = accessoryCount;
    }

    public Integer getAccessoryId() {
        return accessoryId;
    }

    public void setAccessoryId(Integer accessoryId) {
        this.accessoryId = accessoryId;
    }

    public Integer getIsUnlimited() {
        return isUnlimited;
    }

    public void setIsUnlimited(Integer isUnlimited) {
        this.isUnlimited = isUnlimited;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
