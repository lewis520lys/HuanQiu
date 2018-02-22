package com.lewis.cp.http;

import com.lewis.cp.model.BaseCallModel;
import com.lewis.cp.model.CurdUserBean;
import com.lewis.cp.model.GroupModel;
import com.lewis.cp.model.HomeBean;

import com.lewis.cp.model.TouZhuBean;
import com.lewis.cp.model.UserModel;
import com.lewis.cp.model.WelcomeBean;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;

import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface APIService {
  //登录
  @FormUrlEncoded
  @POST("loginAction")
  Call<UserModel> loginReq(@FieldMap Map<String,String> map);
  //找回密码
  @FormUrlEncoded
  @POST("businessUser/findPass")
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
  //上传图片
  @Headers("Content-Type: application/json")
  @POST("businessUser/uploadHead")
  Call<BaseCallModel> unLoadPic(@Body RequestBody body);
  //修改个人信息
  @FormUrlEncoded
  @POST("businessUser/updateUser")
  Call<BaseCallModel> saveUserInfo(@FieldMap Map<String, String> map);
  //查询个人信息
  @FormUrlEncoded
  @POST("businessUser/userDetail")
  Call<CurdUserBean> requestUserInfo(@FieldMap Map<String, String> map);
  //进群
  @FormUrlEncoded
  @POST("businessUser/joinGroup")
  Call<GroupModel> joinGroup(@FieldMap Map<String, String> map);
  //投注
  @FormUrlEncoded
  @POST("businessUser/confirmBet")
  Call<TouZhuBean> TouZhu(@FieldMap Map<String, String> map);


}