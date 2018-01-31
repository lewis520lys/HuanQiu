package com.lewis.cp.view.frgm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.view.Gravity;
import android.view.View;

import com.lewis.cp.base.AppConfig;
import com.lewis.cp.base.BaseApplication;
import com.lewis.cp.model.UserModel;
import com.lewis.cp.view.act.ComWebAct;
import com.lewis.cp.view.act.QunDetialAct;
import com.lewis.cp.widget.ACache;
import com.lewis.cp.widget.ToupiaoPopupWindow;
import com.lewis.cp.widget.ZhiboPopupWindow;

/**
 * Created by Administrator on 2018/1/29.
 */

public class ChartFragment extends com.hyphenate.easeui.ui.EaseChatFragment {
    String managerId="45022";
    private ToupiaoPopupWindow toupiaoPopupWindow;
    private ACache cache;
    private UserModel.UserBean user;


    @Override
    protected void setUpView() {
        super.setUpView();
        cache = ACache.get(BaseApplication.getContext());
        user = (UserModel.UserBean) cache.getAsObject("user");
        final Bundle bundle = getArguments();
        final String userId = bundle.getString("userId");
        titleBar.setTitle(userId);
        titleBar.setBackgroundColor(Color.parseColor("#373A41"));
        toupiaoPopupWindow = new ToupiaoPopupWindow(getActivity());
        touzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toupiaoPopupWindow.showAtLocation(root,Gravity.BOTTOM,0,0);
            }
        });
        zhibo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               new ZhiboPopupWindow(getActivity()).showAsDropDown(tv_head);
            }
        });
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), QunDetialAct.class);

                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        benju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), ComWebAct.class);
                Bundle budle=new Bundle();
                budle.putString("title","路单");
                budle.putString("url", AppConfig.BASE_URL+"ludan?managerId="+managerId);
                intent.putExtras(budle);
                startActivity(intent);
            }
        });
        ludan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(getActivity(), ComWebAct.class);
                Bundle budle=new Bundle();
                budle.putString("title","本局");
                budle.putString("url", AppConfig.BASE_URL+"currentRecord?managerId="+managerId+"&userName="+user.userName);
                intent.putExtras(budle);
                startActivity(intent);
            }
        });

        toupiaoPopupWindow.btCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String z = toupiaoPopupWindow.tv_zhuang.getText().toString().trim();
                String h = toupiaoPopupWindow.tv_he.getText().toString().trim();
                String x = toupiaoPopupWindow.tv_xian.getText().toString().trim();
                String zd = toupiaoPopupWindow.tv_zhuangdui.getText().toString().trim();
                String xd = toupiaoPopupWindow.tv_xiandui.getText().toString().trim();
                String zxd = toupiaoPopupWindow.tv_zhuangxiandui.getText().toString().trim();
                String sb = toupiaoPopupWindow.tv_sanbao.getText().toString().trim();

                String msg=z+h+x+zd+xd+zxd+sb;
                String[] split = msg.split("android.support");
                sendTextMessage(split[0]);
                toupiaoPopupWindow.dismiss();
                toupiaoPopupWindow.delectAll();
                msg="";
            }
        });

    }
}
