package com.lewis.cp.view.frgm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.lewis.cp.base.AppConfig;
import com.lewis.cp.base.BaseApplication;
import com.lewis.cp.http.APIService;
import com.lewis.cp.http.RetrofitManager;
import com.lewis.cp.model.GroupModel;
import com.lewis.cp.model.UserModel;
import com.lewis.cp.view.act.ComWebAct;
import com.lewis.cp.view.act.QunDetialAct;
import com.lewis.cp.widget.ACache;
import com.lewis.cp.widget.ToupiaoPopupWindow;
import com.lewis.cp.widget.ZhiboPopupWindow;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/1/29.
 */

public class ChartMangerFragment extends com.hyphenate.easeui.ui.EaseChatFragment {

    private ACache cache;
    private UserModel.UserBean user;
    private String userId;



    @Override
    protected void setUpView() {
        super.setUpView();
        cache = ACache.get(BaseApplication.getContext());
        user = (UserModel.UserBean) cache.getAsObject("user");
        final Bundle bundle = getArguments();
        userId = bundle.getString("userId");
        titleBar.setTitle(userId);
        titleBar.setBackgroundColor(Color.parseColor("#373A41"));
        ll_head.setVisibility(View.GONE);
        ll_right.setVisibility(View.GONE);

    }

}
