package com.lewis.cp.http;

import com.lewis.cp.model.BaseCallModel;
import com.lewis.cp.model.HomeBean;

import com.lewis.cp.model.UserModel;
import com.lewis.cp.model.WelcomeBean;

import java.util.Map;

import retrofit2.Call;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


public interface APIService {
  //登录
  @FormUrlEncoded
  @POST("loginAction")
  Call<UserModel> loginReq(@FieldMap Map<String,String> map);
  //找回密码
  @FormUrlEncoded
  @POST("findPass")
  Call<BaseCallModel> findPwdReq(@FieldMap Map<String,String> map);
  //首页
  @GET("adver")
  Call<HomeBean> getHomeData();

  //单点登录
  @FormUrlEncoded
  @POST("version")
  Call<WelcomeBean> getVersionUrl(@FieldMap Map<String,String> map);
  //修改密码
  @FormUrlEncoded
  @POST("businessUser/resetPass")
  Call<BaseCallModel> xiugaiPwdReq(@FieldMap Map<String, String> map);
  //添加群组
  @FormUrlEncoded
  @POST("businessUser/addGroup")
  Call<BaseCallModel> addQunReq(@FieldMap Map<String, String> map);
  //建议
  @FormUrlEncoded
  @POST("businessUser/feedback")
  Call<BaseCallModel> jianYiReq(@FieldMap Map<String, String> map);
}