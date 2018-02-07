package com.lewis.cp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.lewis.cp.base.BaseActivity;
import com.lewis.cp.http.APIService;
import com.lewis.cp.http.RetrofitManager;
import com.lewis.cp.model.WelcomeBean;
import com.lewis.cp.view.act.ComWebAct;
import com.lewis.cp.view.frgm.HomeFragment;
import com.lewis.cp.view.frgm.MeFragment;

import com.lewis.cp.view.frgm.YuleFragment;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends BaseActivity implements BottomNavigationBar.OnTabSelectedListener {


    @BindView(R.id.tb)
    LinearLayout tb;
    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private HomeFragment homeFragment;
    private YuleFragment yuleFragment;
    private MeFragment meFragment;

    private int lastSelectedPosition=0;
    private int currentTabIndex=0;
    private ArrayList<Fragment> fragments;


    private LocationClient mLocationClient;
    private BDLocationListener mBDLocationListener;
    @Override
    protected void initData() {
            getLocation();
    }

    @Override
    protected void initView() {
        bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED)
                .addItem(new BottomNavigationItem(R.mipmap.home_s, "首页").setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.home)).setActiveColor(Color.parseColor("#2BA246")))
                .addItem(new BottomNavigationItem(R.mipmap.game_s, "娱乐").setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.game)).setActiveColor(Color.parseColor("#2BA246")))
                .addItem(new BottomNavigationItem(R.mipmap.me_s, "我的").setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.me)).setActiveColor(Color.parseColor("#2BA246")))
                 .setFirstSelectedPosition(lastSelectedPosition )
                .initialise();
        fragments = getFragments();
        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
        initPermission();
        // 声明LocationClient类
        mLocationClient = new LocationClient(getApplicationContext());
        mBDLocationListener = new MyBDLocationListener();
        // 注册监听
        mLocationClient.registerLocationListener(mBDLocationListener);

    }
    /** 获得所在位置经纬度及详细地址 */
    public void getLocation() {
        // 声明定位参数
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 设置定位模式 高精度
        option.setCoorType("bd09ll");// 设置返回定位结果是百度经纬度 默认gcj02

        //option.setScanSpan(5000);// 设置发起定位请求的时间间隔 单位ms
        option.setIsNeedAddress(true);// 设置定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 设置定位结果包含手机机头 的方向
        // 设置定位参数
        mLocationClient.setLocOption(option);
        // 启动定位
        mLocationClient.start();

    }
     class MyBDLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // 非空判断
            if (location != null) {
                // 根据BDLocation 对象获得经纬度以及详细地址信息
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                getVersion(String.valueOf(longitude),String.valueOf(latitude));
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationClient != null) {
            mLocationClient.unRegisterLocationListener(mBDLocationListener);
        }
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
        return R.layout.activity_main;
    }


    @Override
    protected int getFragmentId() {
        return 0;
    }

    private void initPermission() {
        AndPermission.with(this)
                .permission(
                        Manifest.permission.CAMERA,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.RECORD_AUDIO
                       )
                .requestCode(200)
                .callback(listener)
                .start();

    }
    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。

            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            if(requestCode == 200) {

            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            // 权限申请失败回调。
            if(requestCode == 200) {


            }
            // 是否有不再提示并拒绝的权限。
            if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {

                // 第二种：用自定义的提示语。
                AndPermission.defaultSettingDialog(MainActivity.this, 400)
                        .setTitle("权限申请失败")
                        .setMessage("您拒绝了我们必要的一些权限，已经没法愉快的玩耍了，请在设置中授权！")
                        .setPositiveButton("好，去设置")
                        .show();
            }
        }
    };
    /**
     * 设置默认的
     */
    private void setDefaultFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.replace(R.id.tb, homeFragment);
        transaction.commit();
    }
    private ArrayList<Fragment> getFragments() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new YuleFragment());
        fragments.add(new MeFragment());

        return fragments;
    }


    @Override
    public void onTabSelected(int position) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        //当前的fragment
        Fragment from = fm.findFragmentById(R.id.tb);
        if (fragments != null) {
            if (position < fragments.size()) {

                //点击即将跳转的fragment
                Fragment fragment = fragments.get(position);
                if (fragment.isAdded()) {
                    // 隐藏当前的fragment，显示下一个
                    ft.hide(from).show(fragment);
                } else {

                    ft.hide(from).add(R.id.tb, fragment);
                    if (fragment.isHidden()) {
                        ft.show(fragment);

                    }
                }
                ft.commitAllowingStateLoss();
            }
        }


    }

    @Override
    public void onTabUnselected(int position) {
        if (fragments != null) {
            if (position < fragments.size()) {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Fragment fragment = fragments.get(position);
                // 隐藏当前的fragment
                ft.hide(fragment);
                ft.commitAllowingStateLoss();
            }
        }


    }

    @Override
    public void onTabReselected(int position) {

    }
    private long mExitTime;


    @Override
    public void onBackPressed() {
        exitBy2Click();


    }

    private void exitBy2Click() {

        if ((System.currentTimeMillis() - mExitTime) > 2000) {

            Toast.makeText(this, "再点击返回一次退出", Toast.LENGTH_SHORT).show();

            mExitTime = System.currentTimeMillis();

        } else {

            finish();

            System.exit(0);

        }

    }
    private  void  getVersion(String longitudinal,String latitude){
        Map<String,String> map =new HashMap<>();
        map.put("userName",user.userName);
        map.put("loginToken",user.loginToken);
        map.put("longitudinal",longitudinal);
        map.put("latitude",latitude);
        
        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .getVersionUrl(map)
                .enqueue(new Callback<WelcomeBean>() {
                    @Override
                    public void onResponse(Call<WelcomeBean> call, Response<WelcomeBean> response) {
                        WelcomeBean body = response.body();
                        if (body!=null){
                            String version = body.version;
                           if (Integer.parseInt(version)>getVersionCode(MainActivity.this)){
                                Intent intent= new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri content_url = Uri.parse(body.versionUrl);
                                intent.setData(content_url);
                                startActivity(intent);;
                           }
                        }
                    }

                    @Override
                    public void onFailure(Call<WelcomeBean> call, Throwable t) {

                    }
                });
    }
    /**
     * @return 当前应用的版本号
     */
    public int getVersionCode(Context context) {
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            int version = info.versionCode;
            return version;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }



}
