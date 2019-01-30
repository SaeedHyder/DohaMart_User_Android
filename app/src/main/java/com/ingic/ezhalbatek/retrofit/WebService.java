package com.ingic.ezhalbatek.retrofit;


import com.ingic.ezhalbatek.entities.AdminEnt;
import com.ingic.ezhalbatek.entities.AllCategoriesEnt;
import com.ingic.ezhalbatek.entities.CMSEnt;
import com.ingic.ezhalbatek.entities.ChangePhoneEnt;
import com.ingic.ezhalbatek.entities.CreateRequest;
import com.ingic.ezhalbatek.entities.FeedbackEnt;
import com.ingic.ezhalbatek.entities.ForgotPassEnt;
import com.ingic.ezhalbatek.entities.JobDone.JobDoneSubscriptionEnt;
import com.ingic.ezhalbatek.entities.JobDone.JobDoneTaskEnt;
import com.ingic.ezhalbatek.entities.JobDone.SubscriptionPaymentEnt;
import com.ingic.ezhalbatek.entities.JobDone.UserPaymentEnt;
import com.ingic.ezhalbatek.entities.NotificationCountEnt;
import com.ingic.ezhalbatek.entities.NotificationsEnt;
import com.ingic.ezhalbatek.entities.ResponseWrapper;
import com.ingic.ezhalbatek.entities.LoginModule.UserEnt;
import com.ingic.ezhalbatek.entities.ServiceStatus.ServicesStatus;
import com.ingic.ezhalbatek.entities.SubServiceEnt;
import com.ingic.ezhalbatek.entities.SubscriptionsDetail;
import com.ingic.ezhalbatek.entities.SubscriptionPackagesEnt;
import com.ingic.ezhalbatek.entities.getAdditionalJobsEnt;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface WebService {


    @FormUrlEncoded
    @POST("login")
    Call<ResponseWrapper<UserEnt>> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("role_id") String role_id,
            @Field("device_type") String device_type,
            @Field("device_id") String device_id);

    @FormUrlEncoded
    @POST("login")
    Call<ResponseWrapper<UserEnt>> facebookLogin(
            @Field("social_media_token") String social_media_token,
            @Field("role_id") String role_id,
            @Field("device_type") String device_type,
            @Field("device_id") String device_id);

    @FormUrlEncoded
    @POST("forgotpassword")
    Call<ResponseWrapper<ForgotPassEnt>> forgotPassword(
            @Field("email") String email);

    @FormUrlEncoded
    @POST("user/verifypasswordcode")
    Call<ResponseWrapper> verifypasswordcode(
            @Field("email") String email,
            @Field("reset_code") String reset_code);

    @FormUrlEncoded
    @POST("resendcode")
    Call<ResponseWrapper> resendcode(
            @Field("email") String email);

    @FormUrlEncoded
    @POST("user/updatepassword")
    Call<ResponseWrapper> updatePassword(
            @Field("reset_code") String reset_code,
            @Field("email") String email,
            @Field("password") String password);

    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseWrapper<UserEnt>> signup(
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("password") String password,
            @Field("country_code") String country_code,
            @Field("phone_no") String phone_no,
            @Field("zip_code") String zip_code,
            @Field("full_address") String full_address,
            @Field("device_type") String device_type,
            @Field("device_id") String device_id,
            @Field("lang") String lang,
            @Field("FacebookAuthToken") String FacebookAuthToken,
            @Field("GoogleAuthToken") String GoogleAuthToken,
            @Field("profile_picture") String profile_picture);

    @FormUrlEncoded
    @POST("user/register")
    Call<ResponseWrapper<UserEnt>> facebookSignup(
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("country_code") String country_code,
            @Field("phone_no") String phone_no,
            @Field("device_type") String device_type,
            @Field("device_id") String device_id,
            @Field("lang") String lang,
            @Field("FacebookAuthToken") String FacebookAuthToken);

    @FormUrlEncoded
    @POST("user/verifycode")
    Call<ResponseWrapper> verifyCode(
            @Field("user_id") int user_id,
            @Field("verification_code") String verification_code);

    @FormUrlEncoded
    @POST("user/verifychangephone")
    Call<ResponseWrapper<ChangePhoneEnt>> verifyPhoneCode(
            @Field("user_id") int user_id,
            @Field("verification_code") String verification_code,
            @Field("country_code") String country_code,
            @Field("phone_no") String phone_no
    );

    @FormUrlEncoded
    @POST("user/logout")
    Call<ResponseWrapper> logout(
            @Field("user_id") int user_id,
            @Field("device_id") String device_id
    );

    @FormUrlEncoded
    @POST("user/togglenotification")
    Call<ResponseWrapper> toggleNotification(
            @Field("user_id") String user_id,
            @Field("is_notification") String is_notification
    );

    @FormUrlEncoded
    @POST("user/changepassword")
    Call<ResponseWrapper> changePassword(
            @Field("user_id") String user_id,
            @Field("old_password") String old_password,
            @Field("new_password") String new_password
    );


    @GET("user/getnotifications")
    Call<ResponseWrapper<ArrayList<NotificationsEnt>>> getNotifications(
            @Query("user_id") String user_id);

    @FormUrlEncoded
    @POST("user/update")
    Call<ResponseWrapper<UserEnt>> updateProfile(
            @Field("user_id") String user_id,
            @Field("full_name") String full_name,
            @Field("email") String email,
            @Field("full_address") String full_address,
            @Field("location") String location,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude);

    @FormUrlEncoded
    @POST("user/contactus")
    Call<ResponseWrapper> contactUs(
            @Field("email") String email,
            @Field("message") String message
    );

    @FormUrlEncoded
    @POST("changephone")
    Call<ResponseWrapper<UserEnt>> changePhone(
            @Field("user_id") String user_id,
            @Field("country_code") String country_code,
            @Field("phone_no") String phone_no
    );

    @FormUrlEncoded
    @POST("user/getcms")
    Call<ResponseWrapper<CMSEnt>> CMS(
            @Field("type") String type
    );

    @GET("service/allservice")
    Call<ResponseWrapper<ArrayList<AllCategoriesEnt>>> getAllCategories(
    );

    @GET("service/subservice")
    Call<ResponseWrapper<ArrayList<SubServiceEnt>>> getAllSubServices(
            @Query("service_id") String service_id
    );


    @GET("request/getsubscriptionservicestatus")
    Call<ResponseWrapper<ServicesStatus>> getServicesStatus(
            @Query("user_id") String user_id,
            @Query("type") String type);

    @FormUrlEncoded
    @POST("request/cancelrequest")
    Call<ResponseWrapper> cancelRequest(
            @Field("request_id") String request_id,
            @Field("user_id") String user_id
    );

    @GET("subscription/subscriptioncategories")
    Call<ResponseWrapper<ArrayList<SubscriptionPackagesEnt>>> getAllPackages(
    );

    @GET("subscription/allsubscriptions")
    Call<ResponseWrapper<ArrayList<SubscriptionsDetail>>> getPackageDetail(
            @Query("category_id") String category_id
    );

    @FormUrlEncoded
    @POST("subscription/packagesubscription")
    Call<ResponseWrapper<UserEnt>> subscriptionPurchase(
            @Field("subscription_id") String subscription_id,
            @Field("user_id") String user_id,
            @Field("latitude") String latitude,
            @Field("longitude") String longitude,
            @Field("full_address") String full_address
    );


    @POST("user/feedback")
    Call<ResponseWrapper> feedback(
            @Body FeedbackEnt body
    );


    @Multipart
    @POST("request/create")
    Call<ResponseWrapper> createRequest(@Part("job_title") RequestBody job_title,
                                        @Part("service_ids") RequestBody service_ids,
                                        @Part("description") RequestBody description,
                                        @Part("latitude") RequestBody latitude,
                                        @Part("longitude") RequestBody longitude,
                                        @Part("location") RequestBody location,
                                        @Part("full_address") RequestBody full_address,
                                        @Part("date") RequestBody date,
                                        @Part("time") RequestBody time,
                                        @Part("total") RequestBody total,
                                        @Part("payment_type") RequestBody payment_type,
                                        @Part("currency_code") RequestBody currency_code,
                                        @Part("user_id") RequestBody user_id,
                                        @Part("is_urgent") RequestBody is_urgent,
                                        @Part ArrayList<MultipartBody.Part> images
    );



    @GET("user/getadminuser")
    Call<ResponseWrapper<AdminEnt>> getAdminDetail();

    @GET("request/getadditionaljobsuser")
    Call<ResponseWrapper<ArrayList<getAdditionalJobsEnt>>> getAdditionalJobs(
            @Query("user_id") int user_id,
            @Query("action_id") String action_id
    );

    @FormUrlEncoded
    @POST("request/rejectadditionaljobs")
    Call<ResponseWrapper> RejectAdditionalJobTask(
            @Field("user_id") int user_id,
            @Field("request_id") String request_id,
            @Field("additional_job_id") String additional_job_id
    );

    @FormUrlEncoded
    @POST("request/rejectadditionaljobs")
    Call<ResponseWrapper> RejectAdditionalJobSubscription(
            @Field("user_id") int user_id,
            @Field("visit_id") String visit_id,
            @Field("additional_job_id") String additional_job_id
    );


    @FormUrlEncoded
    @POST("request/acknowledgeadditionaljobs")
    Call<ResponseWrapper> AcceptAdditionalJobSubscription(
            @Field("user_id") int user_id,
            @Field("visit_id") String visit_id,
            @Field("additional_job_id") String additional_job_id
    );

    @FormUrlEncoded
    @POST("request/acknowledgeadditionaljobs")
    Call<ResponseWrapper> AcceptAdditionalJobTask(
            @Field("user_id") int user_id,
            @Field("request_id") String request_id,
            @Field("additional_job_id") String additional_job_id
    );

    @FormUrlEncoded
    @POST("user/deletenotification")
    Call<ResponseWrapper> deleteNotification(
            @Field("user_id") int user_id,
            @Field("notification_ids") String notification_ids
    );

    @GET("user/getunreadnotificationscount")
    Call<ResponseWrapper<NotificationCountEnt>> notificationCount(
            @Query("user_id") String user_id);


    @GET("request/getvisitdetails")
    Call<ResponseWrapper<SubscriptionPaymentEnt>> getVisitDetail(
            @Query("visit_id") String visit_id);


    @GET("request/getrequestjob")
    Call<ResponseWrapper<UserPaymentEnt>> getRequestDetail(
            @Query("user_id") int user_id,
            @Query("service_id") String service_id);

    @FormUrlEncoded
    @POST("request/acknowledgejob")
    Call<ResponseWrapper<JobDoneTaskEnt>> JobDone(
            @Field("user_id") int user_id,
            @Field("request_id") String request_id
    );

    @FormUrlEncoded
    @POST("request/acknowledgejob")
    Call<ResponseWrapper<JobDoneSubscriptionEnt>> VisitDone(
            @Field("user_id") int user_id,
            @Field("visit_id") String visit_id
    );

    @FormUrlEncoded
    @POST("user/deletenotification")
    Call<ResponseWrapper> deleteAllNotification(
            @Field("user_id") int user_id
    );

    @FormUrlEncoded
    @POST("user/marknotificationread")
    Call<ResponseWrapper> markUnRead(
            @Field("user_id") int user_id,
            @Field("notification_id") int notification_id
    );

}