package com.lewis.cp.view.act;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lewis.cp.R;
import com.lewis.cp.base.BaseActivity;
import com.lewis.cp.http.APIService;
import com.lewis.cp.http.RetrofitManager;
import com.lewis.cp.model.BaseCallModel;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/1/24.
 */

public class ForgetPwdAct extends BaseActivity {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_encrypted)
    EditText etEncrypted;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.ll)
    LinearLayout ll;
    private String userName;
    private String pwd;
    private String mibao;
    private String type;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        if ("1".equals(type)) {
            tvTitle.setText("找回密码");
        } else {
            tvTitle.setText("修改密码");
            etEncrypted.setHint("原密码");
            etPwd.setHint("新密码");
        }

    }

    @Override
    public void initParms(Bundle parms) {
        type = parms.getString("type");
    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.act_forget_pwd;
    }

    @Override
    protected int getFragmentId() {
        return 0;
    }


    @OnClick({R.id.iv_back, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_login:
                userName = et_account.getText().toString().trim();
                pwd = etPwd.getText().toString().trim();
                mibao = etEncrypted.getText().toString().trim();
                if (TextUtils.isEmpty(userName)) {
                    showToast("请输入账号");
                    return;
                }
                
                if (TextUtils.isEmpty(pwd)) {
                    showToast("请输入密码");
                    return;
                }

                if (TextUtils.isEmpty(mibao)) {
                    if ("1".equals(type)){
                        showToast("请输入密保");
                    }else {
                        showToast("请输入原密码");
                    }

                    return;
                }


                if ("1".equals(type)){
                    findPwd();  
                }else {
                    xiugaiPwd();
                }
               

                break;
        }
    }

    private void xiugaiPwd() {
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", mibao);
        map.put("newPassword", pwd);
        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .xiugaiPwdReq(map)
                .enqueue(new Callback<BaseCallModel>() {
                    @Override
                    public void onResponse(Call<BaseCallModel> call, Response<BaseCallModel> response) {
                        BaseCallModel body = response.body();
                        if (body != null) {
                            showToast(response.body().info);
                            if ("N".equals(response.body().hasException)) {
                                finish();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseCallModel> call, Throwable t) {

                    }
                });
    }

    private void findPwd() {
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("password", pwd);
        map.put("mibao", mibao);
        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .findPwdReq(map)
                .enqueue(new Callback<BaseCallModel>() {
                    @Override
                    public void onResponse(Call<BaseCallModel> call, Response<BaseCallModel> response) {
                        BaseCallModel body = response.body();
                        if (body != null) {
                            showToast(response.body().info);
                            if ("N".equals(response.body().hasException)) {
                                finish();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<BaseCallModel> call, Throwable t) {

                    }
                });


    }

   
}
