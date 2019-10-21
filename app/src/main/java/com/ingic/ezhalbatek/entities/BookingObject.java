package com.ingic.ezhalbatek.entities;

public class BookingObject {

    String job_title;
    String service_ids;
    String description;
    String latitude;
    String longitude;
    String location;
    String full_address;
    String date;
    String time;
    String payment_type;
    String total;
    String currency_code;
    String user_id;
    String is_urgent;
    String images;

    public BookingObject(String job_title, String service_ids, String description, String latitude, String longitude, String location, String full_address, String date, String time, String payment_type, String total, String currency_code, String user_id, String is_urgent, String images) {
        this.job_title = job_title;
        this.service_ids = service_ids;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.full_address = full_address;
        this.date = date;
        this.time = time;
        this.payment_type = payment_type;
        this.total = total;
        this.currency_code = currency_code;
        this.user_id = user_id;
        this.is_urgent = is_urgent;
        this.images = images;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getService_ids() {
        return service_ids;
    }

    public void setService_ids(String service_ids) {
        this.service_ids = service_ids;
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

    public String getFull_address() {
        return full_address;
    }

    public void setFull_address(String full_address) {
        this.full_address = full_address;
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

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getIs_urgent() {
        return is_urgent;
    }

    public void setIs_urgent(String is_urgent) {
        this.is_urgent = is_urgent;
    }
}
