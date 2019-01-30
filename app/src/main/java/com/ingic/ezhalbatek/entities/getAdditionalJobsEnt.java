package com.ingic.ezhalbatek.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class getAdditionalJobsEnt {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("quantity")
    @Expose
    private Integer quantity;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("request_job_id")
    @Expose
    private Integer requestJobId;
    @SerializedName("additional_job_item_id")
    @Expose
    private Integer additionalJobItemId;
    @SerializedName("user_subs_visit_id")
    @Expose
    private Object userSubsVisitId;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("item")
    @Expose
    private Item item;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRequestJobId() {
        return requestJobId;
    }

    public void setRequestJobId(Integer requestJobId) {
        this.requestJobId = requestJobId;
    }

    public Integer getAdditionalJobItemId() {
        return additionalJobItemId;
    }

    public void setAdditionalJobItemId(Integer additionalJobItemId) {
        this.additionalJobItemId = additionalJobItemId;
    }

    public Object getUserSubsVisitId() {
        return userSubsVisitId;
    }

    public void setUserSubsVisitId(Object userSubsVisitId) {
        this.userSubsVisitId = userSubsVisitId;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }
}
