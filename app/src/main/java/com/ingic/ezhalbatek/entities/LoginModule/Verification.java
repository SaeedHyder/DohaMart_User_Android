package com.ingic.ezhalbatek.entities.LoginModule;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Verification {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("verification_code")
    @Expose
    private String verificationCode;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
 /*   @SerializedName("is_active")
    @Expose
    private boolean isActive;*/
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("is_verified")
    @Expose
    private Integer isVerified;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

   /* public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }*/

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

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }
}
