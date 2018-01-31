package com.lewis.cp.view.act;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.exceptions.HyphenateException;
import com.lewis.cp.R;
import com.lewis.cp.base.BaseActivity;
import com.lewis.cp.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QunDetialAct extends BaseActivity {

    EMGroup group = null;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_qun)
    ImageView ivQun;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_qun_id)
    TextView tvQunId;
    @BindView(R.id.tv_qun_num)
    TextView tvQunNum;
    @BindView(R.id.tv_qungonggao)
    TextView tvQungonggao;
    private String gongao;
    private String username;
    private String userId;

    @Override
    protected void initData() {

        new Thread(new Runnable() {



            @Override
            public void run() {
                try {
                    group = EMClient.getInstance().groupManager().getGroupFromServer(userId);
                    gongao = EMClient.getInstance().groupManager().fetchGroupAnnouncement(userId);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
                final String groupName = group.getGroupName();
                final String owner = group.getOwner();//获取群主
                final int num = group.getAffiliationsCount();//获取内存中的群成员
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvTitle.setText(groupName);
                        tvName.setText("群主："+owner);
                        tvQunId.setText(group.getGroupId());
                        tvQunNum.setText(num+"人");
                        if (!TextUtils.isEmpty(gongao)){
                            tvQungonggao.setText(gongao);
                        }

                    }
                });
            }
        }).start();

    }

    @Override
    protected void initView() {

    }

    @Override
    public void initParms(Bundle parms) {
        username = parms.getString(Constant.EXTRA_USER_ID);
        userId = parms.getString("userId");


    }

    @Override
    public View bindView() {
        return null;
    }

    @Override
    public int bindLayout() {
        return R.layout.act_qundetial;
    }

    @Override
    protected int getFragmentId() {
        return 0;
    }



    @OnClick({R.id.iv_back, R.id.bt_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_exit:
               new Thread(new Runnable() {
                   @Override
                   public void run() {
                       try {
                           EMClient.getInstance().groupManager().leaveGroup(userId);//需异步处理
                           EMClient.getInstance().chatManager().deleteConversation(username, true);

                       } catch (HyphenateException e) {
                           e.printStackTrace();
                       }

                   }
               }).start();
               finish();
                break;
        }
    }
}
