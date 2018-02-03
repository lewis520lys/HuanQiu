package com.lewis.cp.view.frgm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMGroup;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.exceptions.HyphenateException;
import com.lewis.cp.base.AppConfig;
import com.lewis.cp.base.BaseApplication;
import com.lewis.cp.http.APIService;
import com.lewis.cp.http.RetrofitManager;
import com.lewis.cp.model.BaseCallModel;
import com.lewis.cp.model.GroupModel;
import com.lewis.cp.model.UserModel;
import com.lewis.cp.utils.Constant;
import com.lewis.cp.utils.ImageToBase;
import com.lewis.cp.view.act.ChatActivity;
import com.lewis.cp.view.act.ComWebAct;
import com.lewis.cp.view.act.QunDetialAct;
import com.lewis.cp.widget.ACache;
import com.lewis.cp.widget.ToupiaoPopupWindow;
import com.lewis.cp.widget.ZhiboPopupWindow;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/1/29.
 */

public class ChartFragment extends com.hyphenate.easeui.ui.EaseChatFragment {
    String managerId="";
    private ToupiaoPopupWindow toupiaoPopupWindow;
    private ACache cache;
    private UserModel.UserBean user;
    private String userId;
    private GroupModel body;
    private EMGroup group;


    @Override
    protected void setUpView() {
        super.setUpView();
        cache = ACache.get(BaseApplication.getContext());
        user = (UserModel.UserBean) cache.getAsObject("user");
        final Bundle bundle = getArguments();
        userId = bundle.getString("userId");
        titleBar.setTitle(userId);
        joinGroup();
        checkGroupId();
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
               new ZhiboPopupWindow(getActivity(),body.rmtpUrl).showAsDropDown(tv_head);
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
                budle.putString("title","本局");
                budle.putString("url", body.betUrl);
                intent.putExtras(budle);
                startActivity(intent);
            }
        });
        ludan.setOnClickListener(new View.OnClickListener() {
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
        guanli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra(Constant.EXTRA_CHAT_TYPE, Constant.CHATTYPE_SINGLE);
                intent.putExtra(Constant.EXTRA_USER_ID, group.getOwner());
                startActivity(intent);
            }
        });
       listerMes();
    }

    private void listerMes() {
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

    }
    EMMessageListener msgListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            Log.e("msg",messages.toString());
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }
        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };
    private  void checkGroupId(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    group = EMClient.getInstance().groupManager().getGroupFromServer(userId);
                } catch (HyphenateException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    private void joinGroup(){
        Map<String, String> map = new HashMap<>();
        map.put("userName", user.userName);
        map.put("groupId", userId);

        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .joinGroup(map)
                .enqueue(new Callback<GroupModel>() {
                    @Override
                    public void onResponse(Call<GroupModel> call, Response<GroupModel> response) {
                        body = response.body();
                        if (body != null) {
                            Toast.makeText(getActivity(), body.info,Toast.LENGTH_LONG).show();
                            managerId=body.managerId;
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    toupiaoPopupWindow.tvYue.setText(body.balance);
                                }
                            });
                        }

                    }

                    @Override
                    public void onFailure(Call<GroupModel> call, Throwable t) {

                    }
                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EMClient.getInstance().chatManager().removeMessageListener(msgListener);
    }
}
