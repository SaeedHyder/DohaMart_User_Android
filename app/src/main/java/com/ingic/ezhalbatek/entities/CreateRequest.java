package com.ingic.ezhalbatek.entities;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateRequest {

    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("service_ids")
    @Expose
    private String serviceIds;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("is_urgent")
    @Expose
    private Integer isUrgent;

    public CreateRequest(String jobTitle, String serviceIds, String description, String latitude, String longitude, String location, String fullAddress, String date, String time, String total, String paymentType, String currencyCode, Integer userId, Integer isUrgent) {
        this.jobTitle = jobTitle;
        this.serviceIds = serviceIds;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.fullAddress = fullAddress;
        this.date = date;
        this.time = time;
        this.total = total;
        this.paymentType = paymentType;
        this.currencyCode = currencyCode;
        this.userId = userId;
        this.isUrgent = isUrgent;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getServiceIds() {
        return serviceIds;
    }

    public void setServiceIds(String serviceIds) {
        this.serviceIds = serviceIds;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Integer isUrgent) {
        this.isUrgent = isUrgent;
    }
}
