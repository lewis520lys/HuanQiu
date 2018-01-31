package com.lewis.cp.view.act;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.lewis.cp.MainActivity;
import com.lewis.cp.R;
import com.lewis.cp.base.BaseActivity;
import com.lewis.cp.http.APIService;
import com.lewis.cp.http.RetrofitManager;

import com.lewis.cp.model.UserModel;



import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by Administrator on 2018/1/23.
 */

public class LoginAct extends BaseActivity {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.et_account)
    EditText etAccount;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_forpwd)
    TextView tvForpwd;
    @BindView(R.id.bt_login)
    Button btLogin;
    private String userName;
    private String pwd;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ivBack.setVisibility(View.GONE);
        tvTitle.setText("登录");
    }

    @Override
    public void initParms(Bundle parms) {

    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.act_login;
    }

    @Override
    protected int getFragmentId() {
        return 0;
    }



    @OnClick({R.id.iv_back, R.id.tv_forpwd, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;

            case R.id.tv_forpwd:
                Bundle bundle=new Bundle();
                bundle.putString("type","1");//1忘记密码
                startActivity(ForgetPwdAct.class,bundle);
                break;
            case R.id.bt_login:
                userName = etAccount.getText().toString().trim();
                pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(userName)){
                     showToast("请输入账号");
                    return;
                }
                if (TextUtils.isEmpty(pwd)){
                     showToast("请输入密码");
                    return;
                }
                login();
                break;
        }
    }

    private void login() {
        Map<String,String> map=new HashMap<>();
        map.put("userName",userName);
        map.put("password",pwd);
        map.put("uuid","455");
        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .loginReq(map)
                .enqueue(new Callback<UserModel>() {
                    @Override
                    public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                        UserModel body = response.body();
                        if (body!=null){
                            showToast(body.info);
                            if ("N".equals(body.hasException)){
                                UserModel.UserBean user = body.user;
                                try {
                                    mCache.put("user",user);
                                    mCache.put("isLogin","Y");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                loginIm(user.hxpass);
                                startActivity(MainActivity.class);
                                finish();
                            }
                        }
                  }

                    @Override
                    public void onFailure(Call<UserModel> call, Throwable t) {

                    }
                });



    }

    private void loginIm(String pwd) {
        EMClient.getInstance().login(userName,pwd,new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
                Log.d("main", "登录聊天服务器成功！");
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                Log.d("main", "登录聊天服务器失败！");
            }
        });
    }
}
