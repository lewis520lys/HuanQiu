package com.lewis.cp.view.act;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lewis.cp.R;
import com.lewis.cp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/1/23.
 */

public class RegisterAct extends BaseActivity {
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
    @BindView(R.id.et_pwd2)
    EditText etPwd2;
    @BindView(R.id.bt_register)
    Button btRegister;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tvTitle.setText("注册");
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
        return R.layout.act_register;
    }

    @Override
    protected int getFragmentId() {
        return 0;
    }



    @OnClick(R.id.bt_register)
    public void onViewClicked() {
    }
}
