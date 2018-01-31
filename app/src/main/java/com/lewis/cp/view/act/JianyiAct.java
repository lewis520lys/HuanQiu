package com.lewis.cp.view.act;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
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
 * Created by Administrator on 2018/1/25.
 */

public class JianyiAct extends BaseActivity {


    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_qun)
    EditText etQun;
    private String content;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {


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
        return R.layout.act_jianyi;
    }

    @Override
    protected int getFragmentId() {
        return 0;
    }



    @OnClick({R.id.iv_back, R.id.bt_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_add:
                content = etQun.getText().toString();
                if (TextUtils.isEmpty(content)){
                    showToast("请输入意见");
                    return;
                }
                commit();
                break;
        }
    }

    private void commit() {
        Map<String, String> map = new HashMap<>();
        map.put("userName", user.userName);
        map.put("feedback ", content );
        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .jianYiReq(map)
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
