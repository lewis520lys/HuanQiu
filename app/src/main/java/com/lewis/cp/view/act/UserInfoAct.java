package com.lewis.cp.view.act;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lewis.cp.MainActivity;
import com.lewis.cp.R;
import com.lewis.cp.base.BaseActivity;
import com.lewis.cp.widget.SelectPicPopupWindow;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class UserInfoAct extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.tv_sex)
    EditText tvSex;
    private SelectPicPopupWindow menuWindow;

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
        return R.layout.act_userinfo;
    }

    @Override
    protected int getFragmentId() {
        return 0;
    }


    @OnClick({R.id.iv_back, R.id.tv_right, R.id.iv_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.tv_right:
                break;
            case R.id.iv_head:
                //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(UserInfoAct.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(UserInfoAct.this.findViewById(R.id.root), Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
        }
    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.btn_take_photo:
                    break;
                case R.id.btn_pick_photo:
                    break;
                default:
                    break;
            }


        }

    };
}
