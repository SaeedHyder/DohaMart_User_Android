package com.ingic.ezhalbatek.entities.JobDone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ingic.ezhalbatek.entities.ServiceStatus.AdditionalJob;
import com.ingic.ezhalbatek.entities.ServiceStatus.AssignTechnician;
import com.ingic.ezhalbatek.entities.ServiceStatus.Feedback;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by saeedhyder on 7/10/2018.
 */

public class UserPaymentEnt {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("job_title")
    @Expose
    private String jobTitle;
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
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
    private Object location;
    @SerializedName("full_address")
    @Expose
    private String fullAddress;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("estimate_from")
    @Expose
    private String estimateFrom;
    @SerializedName("estimate_to")
    @Expose
    private String estimateTo;
    @SerializedName("total")
    @Expose
    private String total;
    @SerializedName("currency_code")
    @Expose
    private String currencyCode;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("is_urgent")
    @Expose
    private Integer isUrgent;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    @SerializedName("urgent_cost")
    @Expose
    private Integer urgentCost;
    @SerializedName("servics_list")
    @Expose
    private ArrayList<ServicsList> servicsList = new ArrayList<>();
    @SerializedName("additional_jobs")
    @Expose
    private ArrayList<AdditionalJob> additionalJobs = new ArrayList<>();
    @SerializedName("image_detail")
    @Expose
    private ArrayList<Object> imageDetail = new ArrayList<>();
    @SerializedName("user_detail")
    @Expose
    private UserDetail userDetail;
    @SerializedName("assign_technician")
    @Expose
    private AssignTechnician assignTechnician;
    @SerializedName("subscription_rooms")
    @Expose
    private ArrayList<CreateRoomEnt> subscriptionRooms = new ArrayList<>();
    @SerializedName("feedback")
    @Expose
    private Feedback feedback;

    public Feedback getFeedback() {
        return feedback;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getUrgentCost() {
        return urgentCost;
    }

    public void setUrgentCost(Integer urgentCost) {
        this.urgentCost = urgentCost;
    }

    public void setFeedback(Feedback feedback) {
        this.feedback = feedback;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
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

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getEstimateFrom() {
        return estimateFrom;
    }

    public void setEstimateFrom(String estimateFrom) {
        this.estimateFrom = estimateFrom;
    }

    public String getEstimateTo() {
        return estimateTo;
    }

    public void setEstimateTo(String estimateTo) {
        this.estimateTo = estimateTo;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getIsUrgent() {
        return isUrgent;
    }

    public void setIsUrgent(Integer isUrgent) {
        this.isUrgent = isUrgent;
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

    public ArrayList<ServicsList> getServicsList() {
        return servicsList;
    }

    public void setServicsList(ArrayList<ServicsList> servicsList) {
        this.servicsList = servicsList;
    }

    public ArrayList<AdditionalJob> getAdditionalJobs() {
        return additionalJobs;
    }

    public void setAdditionalJobs(ArrayList<AdditionalJob> additionalJobs) {
        this.additionalJobs = additionalJobs;
    }

    public ArrayList<Object> getImageDetail() {
        return imageDetail;
    }

    public void setImageDetail(ArrayList<Object> imageDetail) {
        this.imageDetail = imageDetail;
    }

    public UserDetail getUserDetail() {
        return userDetail;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
    }

    public AssignTechnician getAssignTechnician() {
        return assignTechnician;
    }

    public void setAssignTechnician(AssignTechnician assignTechnician) {
        this.assignTechnician = assignTechnician;
    }

    public ArrayList<CreateRoomEnt> getSubscriptionRooms() {
        return subscriptionRooms;
    }

    public void setSubscriptionRooms(ArrayList<CreateRoomEnt> subscriptionRooms) {
        this.subscriptionRooms = subscriptionRooms;
    }
}
