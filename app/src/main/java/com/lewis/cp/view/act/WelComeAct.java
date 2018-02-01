package com.lewis.cp.view.act;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lewis.cp.MainActivity;
import com.lewis.cp.R;
import com.lewis.cp.base.BaseActivity;

import com.lewis.cp.model.Bomb;
import com.lewis.cp.model.UserModel;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;


import java.util.List;


import butterknife.BindView;

import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;


/**
 * Created by Administrator on 2018/1/25.
 */

public class WelComeAct extends BaseActivity {
    @BindView(R.id.tv)
    TextView tv;
    private boolean is_bomb=false;

    private String isLogin="";

    @Override
    protected void initData() {
        getExplose();
        timer.start();
    }

    @Override
    protected void initView() {
        isLogin = mCache.getAsString("isLogin");



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
        return R.layout.act_welcome;
    }

    @Override
    protected int getFragmentId() {
        return 0;
    }


    private CountDownTimer timer = new CountDownTimer(3000, 1000) {

        @Override
        public void onTick(long millisUntilFinished) {
            tv.setText("跳过\n"+(millisUntilFinished / 1000) + "秒");
        }

        @Override
        public void onFinish() {
                if (!is_bomb){
                    if ("Y".equals(isLogin)){
                        startActivity(MainActivity.class);

                    }else {
                        startActivity(LoginAct.class);
                    }
                    finish();
                }

        }
    };



    @OnClick(R.id.tv)
    public void onViewClicked() {
        timer.cancel();
        if (!is_bomb){
            if ("Y".equals(isLogin)){
                startActivity(MainActivity.class);

            }else {
                startActivity(LoginAct.class);
            }
            finish();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
    private void  getExplose(){
        //查找Person表里面id为6b6c11c537的数据
        BmobQuery<Bomb> bmobQuery = new BmobQuery<Bomb>();
        bmobQuery.getObject("Nwxu666E", new QueryListener<Bomb>() {
            @Override
            public void done(Bomb object,BmobException e) {
                if(e==null){
                    if (object.isIs_bomb()){
                        is_bomb=true;
                    }else {
                        is_bomb=false;
                    }
                }else{

                }
            }
        });
    }

}
