package com.lewis.cp;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hyphenate.EMClientListener;
import com.hyphenate.chat.EMClient;
import com.lewis.cp.base.BaseActivity;
import com.lewis.cp.view.frgm.HomeFragment;
import com.lewis.cp.view.frgm.MeFragment;
import com.lewis.cp.view.frgm.ChartFragment;
import com.lewis.cp.view.frgm.YuleFragment;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;
import java.util.List;
import butterknife.BindView;


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


    @Override
    protected void initData() {

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
        bottomNavigationBar.setTabSelectedListener(this);
        setDefaultFragment();
        initPermission();
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
                        Manifest.permission.ACCESS_COARSE_LOCATION
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

    @Override
    public void onTabSelected(int position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (position) {
            case 0:
                currentTabIndex=0;
                if (homeFragment == null) {
                    homeFragment =  new HomeFragment();
                }
                transaction.replace(R.id.tb, homeFragment);
                break;
            case 1:
                currentTabIndex=1;
                if (yuleFragment == null) {
                    yuleFragment = new YuleFragment();
                }
                transaction.replace(R.id.tb, yuleFragment);
                break;
            case 2:
                currentTabIndex=2;
                if (meFragment == null) {
                    meFragment =new MeFragment();
                }
                transaction.replace(R.id.tb, meFragment);
                break;

            default:
                break;
        }
        // 事务提交
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
    private long mExitTime;





    private void exitBy2Click() {

        if ((System.currentTimeMillis() - mExitTime) > 2000) {

            Toast.makeText(this, "再点击返回一次退出", Toast.LENGTH_SHORT).show();

            mExitTime = System.currentTimeMillis();

        } else {

            finish();

            System.exit(0);

        }

    }


}
