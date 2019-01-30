package com.ingic.ezhalbatek.entities.ServiceStatus;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AssignTechnician {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("technician_id")
    @Expose
    private Integer technicianId;
    @SerializedName("request_id")
    @Expose
    private Integer requestId;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("arrival_time")
    @Expose
    private String arrivalTime;
    @SerializedName("completion_time")
    @Expose
    private String completionTime;
    @SerializedName("finish")
    @Expose
    private Integer finish;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("technician_details")
    @Expose
    private TechnicianDetails technicianDetails;
    @SerializedName("technician_detail")
    @Expose
    private TechnicianDetails technicianDetail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTechnicianId() {
        return technicianId;
    }

    public void setTechnicianId(Integer technicianId) {
        this.technicianId = technicianId;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    public Integer getFinish() {
        return finish;
    }

    public void setFinish(Integer finish) {
        this.finish = finish;
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

    public TechnicianDetails getTechnicianDetails() {
        return technicianDetails;
    }

    public void setTechnicianDetails(TechnicianDetails technicianDetails) {
        this.technicianDetails = technicianDetails;
    }
}
