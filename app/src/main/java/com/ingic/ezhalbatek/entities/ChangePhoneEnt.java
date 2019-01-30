package com.ingic.ezhalbatek.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePhoneEnt {
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("phone_no")
    @Expose
    private String phoneNo;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
