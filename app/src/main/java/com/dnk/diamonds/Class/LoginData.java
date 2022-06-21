package com.dnk.shairugems.Class;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LoginData implements Serializable {
    @SerializedName("Message")
    @Expose
    public String Message;
    @SerializedName("Status")
    @Expose
    public String Status;
    @SerializedName("Error")
    @Expose
    public String Error;
//    @SerializedName("Error")
//    @Expose
//    public String email;
//    @SerializedName("mobile")
//    @Expose
//    public String mobile;
//    @SerializedName("gender")
//    @Expose
//    public String gender;
//    @SerializedName("dob")
//    @Expose
//    public String dob;
//    @SerializedName("otp")
//    @Expose
//    public String otp;
//    @SerializedName("photo")
//    @Expose
//    public String photo;
//    @SerializedName("created_at")
//    @Expose
//    public String created_at;
//    @SerializedName("updated_at")
//    @Expose
//    public String updated_at;
//    @SerializedName("userrole_id")
//    @Expose
//    public String userrole_id;
//    @SerializedName("token")
//    @Expose
//    public String token;
//    @SerializedName("refferal_code")
//    @Expose
//    public String refferal_code;
//    @SerializedName("wallet_amount")
//    @Expose
//    public double wallet_amount;
//    @SerializedName("status")
//    @Expose
//    public String success;
//    @SerializedName("message")
//    @Expose
//    public String message;
}
