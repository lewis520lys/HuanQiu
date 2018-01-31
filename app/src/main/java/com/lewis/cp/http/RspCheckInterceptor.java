package com.lewis.cp.http;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;


public class RspCheckInterceptor implements Interceptor {

     @Override
     public Response intercept(Chain chain) throws IOException {
         Response response = chain.proceed(chain.request());
             try {
                 ResponseBody rspBody = response.body();
                 JSONObject jsonObject = new JSONObject(rspBody.string().toString());
                 int status = jsonObject.getInt("status");
                 if (status < 200 || status >= 300){
                     throw new IOException(jsonObject.getString("msg"));
                 }
             } catch (JSONException e) {
                 e.printStackTrace();
                 throw new IOException("parase data error");
             }catch (Exception e){
                 if (e instanceof IOException){
                     throw (IOException)e;
                 }
             }

         return response;
     }
 }

