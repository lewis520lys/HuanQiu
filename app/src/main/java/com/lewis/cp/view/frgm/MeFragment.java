package com.lewis.cp.view.frgm;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMClient;
import com.lewis.cp.R;
import com.lewis.cp.base.AppConfig;
import com.lewis.cp.base.BaseApplication;
import com.lewis.cp.base.BaseFragment;
import com.lewis.cp.http.APIService;
import com.lewis.cp.http.RetrofitManager;
import com.lewis.cp.model.CurdUserBean;
import com.lewis.cp.model.UserModel;
import com.lewis.cp.view.act.ComWebAct;
import com.lewis.cp.view.act.ForgetPwdAct;
import com.lewis.cp.view.act.JianyiAct;
import com.lewis.cp.view.act.LoginAct;
import com.lewis.cp.view.act.UserInfoAct;
import com.lewis.cp.widget.ACache;
import com.lewis.cp.widget.CircleImageView;


import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/1/23.
 */

public class MeFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_id)
    TextView tvId;

    private ACache cache;
    private UserModel.UserBean user;


    @Override
    protected void handleBundle(Bundle bundle) {

    }


    @Override
    protected void initView(View view) {
        ivBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("我的");
        cache = ACache.get(BaseApplication.getContext());
        user = (UserModel.UserBean) cache.getAsObject("user");
        requestData();
    }

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    @Override
    protected int getLayout() {
        return R.layout.frg_me;
    }


    @OnClick({R.id.rl_yufen, R.id.rl_liuxhui, R.id.rl_shangxiafen, R.id.rl_xiugaipass, R.id.rl_jianyi, R.id.tv_copy, R.id.bt_exit,R.id.rl_head})
    public void onViewClicked(View view) {
        Intent intent =new Intent(getActivity(), ComWebAct.class);
        Bundle budle=new Bundle();
        switch (view.getId()) {
            case R.id.rl_yufen:

                budle.putString("title","我的余分");
                budle.putString("url", AppConfig.BASE_URL+"businessUser/mybalance?userName="+user.userName);
                intent.putExtras(budle);
                startActivity(intent);
                break;
            case R.id.rl_liuxhui:

                budle.putString("title","我的流水");
                budle.putString("url", AppConfig.BASE_URL+"businessUser/mybet?userName="+user.userName);
                intent.putExtras(budle);
                startActivity(intent);
                break;
            case R.id.rl_shangxiafen:
                budle.putString("title","我的上下分");
                budle.putString("url", AppConfig.BASE_URL+"businessUser/outMoneyRecord?userName="+user.userName);
                intent.putExtras(budle);
                startActivity(intent);
                break;
            case R.id.rl_xiugaipass:
                Intent intent1 =new Intent(getActivity(), ForgetPwdAct.class);
                budle.putString("type","0");//1忘记密码
                intent1.putExtras(budle);
                startActivity(intent1);
                break;
            case R.id.rl_jianyi:
                Intent intent2 =new Intent(getActivity(), JianyiAct.class);
                startActivity(intent2);
                break;
            case R.id.tv_copy:
                break;
                case R.id.rl_head:
                    startActivity(new Intent(getActivity(),UserInfoAct.class));
                break;
            case R.id.bt_exit:
                BaseApplication.getInstance().finishAllActivities();
                Intent intent3=new Intent(getActivity(), LoginAct.class);
                startActivity(intent3);
                EMClient.getInstance().logout(true);
                cache.clear();

                break;
        }
    }
    private void requestData(){
        Map<String, String> map = new HashMap<>();
        map.put("userName", user.userName);

        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .requestUserInfo(map)
                .enqueue(new Callback<CurdUserBean>() {
                    @Override
                    public void onResponse(Call<CurdUserBean> call, Response<CurdUserBean> response) {
                        final CurdUserBean body = response.body();
                        if (body != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Glide.with(getActivity()).load(body.headImg).placeholder(R.mipmap.head_default).into(ivHead);
                                    tvName.setText(body.nickName);
                                    tvId.setText(body.userId+"");
                                }
                            });

                        }

                    }

                    @Override
                    public void onFailure(Call<CurdUserBean> call, Throwable t) {

                    }
                });

    }
}
