package com.lewis.cp.view.frgm;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
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
import com.lewis.cp.model.TouZhuBean;
import com.lewis.cp.model.UserModel;
import com.lewis.cp.utils.Constant;
import com.lewis.cp.utils.ImageToBase;
import com.lewis.cp.view.act.ChatActivity;
import com.lewis.cp.view.act.ComWebAct;
import com.lewis.cp.view.act.QunDetialAct;
import com.lewis.cp.widget.ACache;
import com.lewis.cp.widget.ToupiaoPopupWindow;
import com.lewis.cp.widget.ZhiboPopupWindow;

import org.json.JSONObject;

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
    private String banker="";
    private String player="";
    private String pair="";
    private String bankerPair="";
    private String playerPair="";
    private String bpPair="";
    private String sanBao="";


    @Override
    protected void setUpView() {
        super.setUpView();
        cache = ACache.get(BaseApplication.getContext());
        user = (UserModel.UserBean) cache.getAsObject("user");
        final Bundle bundle = getArguments();
        userId = bundle.getString("userId");


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
                banker=z.replace("庄:","").replace(";","");
                player=x.replace("闲:","").replace(";","");
                pair=h.replace("和:","").replace(";","");
                bankerPair=zd.replace("庄对:","").replace(";","");
                playerPair=xd.replace("闲对:","").replace(";","");
                bpPair=zxd.replace("庄闲对:","").replace(";","");
                sanBao=sb.replace("三宝:","").replace(";","");
                String msg=z+h+x+zd+xd+zxd+sb;
                msg = msg.substring(0, msg.length()-1);
                String[] split = msg.split("android.support");
                sendTextMessage(split[0]);
                xiazhu();
                toupiaoPopupWindow.dismiss();
                toupiaoPopupWindow.delectAll();

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

    }


    private  void checkGroupId(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    group = EMClient.getInstance().groupManager().getGroupFromServer(userId);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            titleBar.setTitle(group.getGroupName());
                        }
                    });
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
    private void xiazhu(){
        Map<String, String> map = new HashMap<>();
        map.put("userName", user.userName);
        map.put("managerId", managerId);
        map.put("banker", banker);//庄
        map.put("player", player);//闲
        map.put("pair", pair);//和
        map.put("bankerPair", bankerPair);//庄对
        map.put("playerPair", playerPair);//闲对
        map.put("bpPair", bpPair);//庄闲对
        map.put("sanBao", sanBao);//三宝
        Log.e("map",map.toString());
        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .TouZhu(map)
                .enqueue(new Callback<TouZhuBean>() {
                    @Override
                    public void onResponse(Call<TouZhuBean> call, Response<TouZhuBean> response) {
                        TouZhuBean body = response.body();
                         if (body!=null){
                             Toast.makeText(getActivity(),body.info,Toast.LENGTH_LONG).show();
                             if (!TextUtils.isEmpty(body.balance)){
                                 toupiaoPopupWindow.tvYue.setText(body.balance);
                             }
                         }
                    }

                    @Override
                    public void onFailure(Call<TouZhuBean> call, Throwable t) {
                            Log.e("ss",t.toString());
                    }
                });

    }


}
