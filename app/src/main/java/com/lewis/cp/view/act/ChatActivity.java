package com.lewis.cp.view.act;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.hyphenate.easeui.ui.EaseChatFragment;
import com.hyphenate.util.EasyUtils;
import com.lewis.cp.MainActivity;
import com.lewis.cp.R;
import com.lewis.cp.base.BaseActivity;
import com.lewis.cp.view.frgm.ChartFragment;


/**
 * chat activity，EaseChatFragment was used {@link #}
 *
 */
public class ChatActivity extends BaseActivity{
    public static ChatActivity activityInstance;
    private EaseChatFragment chatFragment;
    String toChatUsername;



    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

        activityInstance = this;
        //get user id or group id
        toChatUsername = getIntent().getExtras().getString("userId");
        //use EaseChatFratFragment
        chatFragment = new ChartFragment();
        //pass parameters to chat fragment
        chatFragment.setArguments(getIntent().getExtras());
        getSupportFragmentManager().beginTransaction().add(R.id.container, chatFragment).commit();
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
        return R.layout.em_activity_chat;
    }

    @Override
    protected int getFragmentId() {
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstance = null;
    }
    
    @Override
    protected void onNewIntent(Intent intent) {
    	// make sure only one chat activity is opened
        String username = intent.getStringExtra("userId");
        if (toChatUsername.equals(username))
            super.onNewIntent(intent);
        else {
            finish();
            startActivity(intent);
        }

    }
    

    
    public String getToChatUsername(){
        return toChatUsername;
    }

}
