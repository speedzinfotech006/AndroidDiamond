package com.dnk.shairugems.Volly;

import com.dnk.shairugems.Class.FooRequest;
import com.dnk.shairugems.Class.LoginData;
import com.dnk.shairugems.Class.MailRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

//    @FormUrlEncoded
//    @POST("API/Login_api")
//    Call<LoginData> login(@Field("token") String token, @Field("os") String os, @Field("userrole_id") String userrole_id, @Field("email") String username, @Field("password") String password
//            , @Field("device_id") String fireToken);
//
//    @FormUrlEncoded
//    @POST("API/Signup_api")
//    Call<LoginData> signUp(@Field("token") String token, @Field("os") String os, @Field("name") String username
//            , @Field("email") String email, @Field("contact") String phone, @Field("password") String password, @Field("userrole_id") String userrole_id
//            , @Field("vendor_id") String vendor_id, @Field("suggested_user_refferal") String suggested_user_refferal
//            , @Field("device_id") String fireToken);

//    @FormUrlEncoded
//    @POST("API/GetProfile_api")
//    Call<ProfileData> getProfile(@Field("token") String token, @Field("os") String os, @Field("userrole_id") String userrole_id, @Field("user_id") String user_id);
//
//    @FormUrlEncoded
//    @POST("verify-otp")
//    Call<LoginData> verifyOtp(@Field("username") String username, @Field("otp") String otp);
//
//    @FormUrlEncoded
//    @POST("resend-otp")
//    Call<Login> resendOtp(@Field("username") String username);
//
//    @FormUrlEncoded
//    @POST("delete-account")
//    Call<Login> deleteAccount(@Field("user_id") String user_id);
//
//    @FormUrlEncoded
//    @POST("API/UpdateProfile_api")
//    Call<LoginData> updateProfile(@Field("user_id") String user_id, @Field("name") String name, @Field("phone_no") String mobile
//            , @Field("gender") String gender, @Field("dob") String dob, @Field("customer_id") String customer_id
//            , @Field("userrole_id") String userrole_id, @Field("token") String token, @Field("os") String os);
//
//    @Multipart
//    @POST("API/UpdateProfile_api")
//    Call<LoginData> updateProfile(@Part MultipartBody.Part image, @PartMap Map<String, RequestBody> params);
//
//    @FormUrlEncoded
//    @POST("notification")
//    Call<Notification> getNotification(@Field("user_id") String user_id);
//
//    @FormUrlEncoded
//    @POST("API/Appointment_api")
//    Call<AppointmentHistory> getAppointment(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token,
//                                            @Field("os") String os, @Field("customer_id") String customer_id, @Field("is_completed") String is_completed);
//
//    @FormUrlEncoded
//    @POST("API/Service_api")
//    Call<Service> getService(@Field("token") String token, @Field("os") String os, @Field("userrole_id") String userrole_id, @Field("user_id") String user_id
//            , @Field("vendor_id") String vendor_id, @Field("service_id") String service_id, @Field("vendor_service_id") String vendor_service_id);
//
//    @FormUrlEncoded
//    @POST("API/Wallet_api")
//    Call<Coupon> getTransaction(@Field("token") String token, @Field("os") String os, @Field("userrole_id") String userrole_id, @Field("user_id") String user_id
//            , @Field("customer_id") String customer_id, @Field("type") String type, @Field("start") String start);
//
//    @FormUrlEncoded
//    @POST("API/Appointment_api/GetTimeSlot")
//    Call<TimeSlot> getTimeSlots(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token,
//                                @Field("os") String os, @Field("vendor_service_id") String service_id, @Field("appointment_date") String appointment_date);
//
//    @FormUrlEncoded
//    @POST("API/Address_api")
//    Call<Address> getAddress(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token,
//                             @Field("os") String os, @Field("customer_id") String customer_id);
//
//    @FormUrlEncoded
//    @POST("API/Address_api/AddAddress")
//    Call<LoginData> addAddress(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token
//            , @Field("os") String os, @Field("customer_id") String customer_id, @Field("address_type") String address_type
//            , @Field("address") String address, @Field("landmark") String landmark, @Field("city") String city
//            , @Field("state") String state, @Field("country") String country, @Field("pincode") String pincode, @Field("ac") String ac);
//
//    @FormUrlEncoded
//    @POST("API/Address_api/AddAddress")
//    Call<LoginData> updateAddress(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token
//            , @Field("os") String os, @Field("customer_id") String customer_id, @Field("address_type") String address_type
//            , @Field("address") String address, @Field("landmark") String landmark, @Field("city") String city
//            , @Field("state") String state, @Field("country") String country, @Field("pincode") String pincode, @Field("ac") String ac
//            , @Field("customer_address_id") String customer_address_id);
//
//    @GET("API/CustomerAC_api")
//    Call<Ac> getAc();
//
//    @GET("API/GetInviteBanners_api")
//    Call<BannerData> getInviteBanner();
//
//    @FormUrlEncoded
//    @POST("API/CustomerAC_api")
//    Call<AcList> getAcList(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token,
//                           @Field("os") String os, @Field("customer_id") String customer_id, @Field("customer_address_id") String customer_address_id);
//
//    @FormUrlEncoded
//    @POST("API/CustomerAC_api/AddCustomerAC")
//    Call<LoginData> addAc(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token
//            , @Field("os") String os, @Field("customer_id") String customer_id, @Field("customer_address_id") String customer_address_id
//            , @Field("company_id") String company_id, @Field("ac_type") String ac_type, @Field("ton") String ton);
//
//    @FormUrlEncoded
//    @POST("API/CustomerAC_api/AddCustomerAC")
//    Call<LoginData> updateAc(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token
//            , @Field("os") String os, @Field("customer_id") String customer_id, @Field("customer_address_id") String customer_address_id
//            , @Field("company_id") String company_id, @Field("ac_type") String ac_type, @Field("ton") String ton, @Field("customer_ac_id") String customer_ac_id);
//
//    @FormUrlEncoded
//    @POST("API/Appointment_api/AddAppointment")
//    Call<LoginData> addAppointment(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token
//            , @Field("os") String os, @Field("customer_id") String customer_id, @Field("vendor_service_id") String service_id
//            , @Field("appointment_date") String appointment_date, @Field("appointment_time") String appointment_time, @Field("customer_address_id") String customer_address_id
//            , @Field("payment_method") String payment_method, @Field("total_amount") String total_amount, @Field("gst_amount") String gst_amount
//            , @Field("discount_amount") String discount_amount, @Field("payable_amount") String payable_amount, @Field("customer_ac_id") String customer_ac_id
//            , @Field("coupon_code") String coupon_code, @Field("is_wallet") String is_wallet);
//
//    @FormUrlEncoded
//    @POST("API/Appointment_api/ApplyCoupon")
//    Call<ApplyCouponData> applyCode(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token
//            , @Field("os") String os, @Field("customer_id") String customer_id, @Field("vendor_service_id") String service_id
//            , @Field("amount") String amount, @Field("coupon_code") String coupon_code);
//
//    @FormUrlEncoded
//    @POST("API/Appointment_api/CancelAppointment")
//    Call<LoginData> cancelAppointment(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token
//            , @Field("os") String os, @Field("customer_id") String customer_id, @Field("appointment_id") String appointment_id);
//
//    @FormUrlEncoded
//    @POST("API/Service_api/MasterService")
//    Call<MasterService> getMasterService(@Field("token") String token, @Field("os") String os, @Field("userrole_id") String userrole_id, @Field("user_id") String user_id
//            , @Field("vendor_id") String vendor_id);
//
//    @FormUrlEncoded
//    @POST("API/Coupon_api")
//    Call<Coupon> getCoupon(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token,
//                           @Field("os") String os, @Field("customer_id") String customer_id);
//
//    @FormUrlEncoded
//    @POST("API/Terms_api")
//    Call<TermsData> getTerms(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token,
//                             @Field("os") String os);

    @FormUrlEncoded
    @POST("User/UpdatePassword")
    Call<LoginData> changePassword(@Field("Password") String user_id);

    @FormUrlEncoded
    @POST("User/DeleteUser")
    Call<LoginData> DeleteUser(@Field("UserID") String user_id);

    @POST("Stock/EmailAllStones")
    Call<LoginData> SendMailAll(@Body MailRequest SearchCriteria);

    @POST("Stock/PairEmailStones")
    Call<LoginData> SendMailAllPair(@Body MailRequest SearchCriteria);

    @POST("Stock/SaveNoFoundSearchStock")
    Call<LoginData> NoStockFound(@Body FooRequest SearchCriteria);

    @FormUrlEncoded
    @POST("Stock/DownloadStockMedia")
    Call<String> DownloadStockMedia(@Field("StoneID") String StoneID, @Field("PageNo") String PageNo, @Field("DownloadMedia") String DownloadMedia);

//    @FormUrlEncoded
//    @POST("API/Login_api/ForgetPass")
//    Call<LoginData> forgotPassword(@Field("email") String username, @Field("token") String token,
//                                   @Field("os") String os);
//
//    @FormUrlEncoded
//    @POST("API/Support_api")
//    Call<LoginData> support(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token,
//                            @Field("os") String os, @Field("name") String name, @Field("email") String email, @Field("subject") String subject, @Field("message") String message);
//
//    @FormUrlEncoded
//    @POST("API/Reviews_api/addReview")
//    Call<LoginData> addReview(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token,
//                              @Field("os") String os, @Field("name") String name, @Field("rating") String rating, @Field("vendor_service_id") String vendor_service_id, @Field("message") String message);
//
//    @FormUrlEncoded
//    @POST("API/Reviews_api")
//    Call<Review> getReview(@Field("token") String token, @Field("os") String os, @Field("userrole_id") String userrole_id, @Field("user_id") String user_id
//            , @Field("vendor_service_id") String vendor_service_id);
//
//    @FormUrlEncoded
//    @POST("API/Appointment_api")
//    Call<Service> getLastAppointment(@Field("user_id") String user_id, @Field("userrole_id") String userrole_id, @Field("token") String token,
//                                     @Field("os") String os, @Field("name") String name, @Field("rating") String rating, @Field("vendor_service_id") String vendor_service_id, @Field("message") String message);
}
