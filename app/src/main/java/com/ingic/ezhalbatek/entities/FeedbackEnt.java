package com.ingic.ezhalbatek.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FeedbackEnt {


    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("visit_id")
    @Expose
    private String visit_id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("technician_id")
    @Expose
    private String technicianId;
    @SerializedName("feedback")
    @Expose
    private String feedback;
    @SerializedName("rate")
    @Expose
    private String rate;

    public FeedbackEnt(String requestId, String visit_id, String userId, String technicianId, String feedback, String rate) {
        this.requestId = requestId;
        this.visit_id = visit_id;
        this.userId = userId;
        this.technicianId = technicianId;
        this.feedback = feedback;
        this.rate = rate;
    }

    public String getVisit_id() {
        return visit_id;
    }

    public void setVisit_id(String visit_id) {
        this.visit_id = visit_id;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(String technicianId) {
        this.technicianId = technicianId;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}
