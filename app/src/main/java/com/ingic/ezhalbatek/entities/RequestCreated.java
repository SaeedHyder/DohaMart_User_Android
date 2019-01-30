package com.ingic.ezhalbatek.entities;


import com.ingic.ezhalbatek.entities.ServiceStatus.ServiceDetail;

import java.util.List;

public class RequestCreated {

    public int id;
    public String job_title;
    public String category_id;
    public String description;
    public String latitude;
    public String longitude;
    public String full_address;
    public String date;
    public String time;
    public String payment_type;
    public String status;
    public String estimate_from;
    public String estimate_to;
    public String total;
    public String currency_code;
    public String user_id;
    public String message;
    public String parent_id;
    public String created_at;
    public String updated_at;
    public ServiceDetail service_detail;
    public List<Object> image_detail;
    public CategoryDetail category_detail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEstimate_from() {
        return estimate_from;
    }

    public void setEstimate_from(String estimate_from) {
        this.estimate_from = estimate_from;
    }

    public String getEstimate_to() {
        return estimate_to;
    }

    public void setEstimate_to(String estimate_to) {
        this.estimate_to = estimate_to;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public ServiceDetail getService_detail() {
        return service_detail;
    }

    public void setService_detail(ServiceDetail service_detail) {
        this.service_detail = service_detail;
    }

    public List<Object> getImage_detail() {
        return image_detail;
    }

    public void setImage_detail(List<Object> image_detail) {
        this.image_detail = image_detail;
    }

    public CategoryDetail getCategory_detail() {
        return category_detail;
    }

    public void setCategory_detail(CategoryDetail category_detail) {
        this.category_detail = category_detail;
    }
}
