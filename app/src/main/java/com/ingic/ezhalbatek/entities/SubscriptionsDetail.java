package com.ingic.ezhalbatek.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ingic.ezhalbatek.entities.LoginModule.Services;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionsDetail {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ar_title")
    @Expose
    private String arTitle;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("ar_description")
    @Expose
    private String arDescription;
    @SerializedName("subscription_duration")
    @Expose
    private Integer subscriptionDuration;
    @SerializedName("visit_period")
    @Expose
    private Integer visitPeriod;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("is_active")
    @Expose
    private Integer isActive;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("services")
    @Expose
    private ArrayList<Services> services = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArTitle() {
        return arTitle;
    }

    public void setArTitle(String arTitle) {
        this.arTitle = arTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getArDescription() {
        return arDescription;
    }

    public void setArDescription(String arDescription) {
        this.arDescription = arDescription;
    }

    public Integer getSubscriptionDuration() {
        return subscriptionDuration;
    }

    public void setSubscriptionDuration(Integer subscriptionDuration) {
        this.subscriptionDuration = subscriptionDuration;
    }

    public Integer getVisitPeriod() {
        return visitPeriod;
    }

    public void setVisitPeriod(Integer visitPeriod) {
        this.visitPeriod = visitPeriod;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
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

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public ArrayList<Services> getServices() {
        return services;
    }

    public void setServices(ArrayList<Services> services) {
        this.services = services;
    }
}
