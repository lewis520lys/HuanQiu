package com.lewis.cp.view.frgm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lewis.cp.R;
import com.lewis.cp.base.BaseApplication;
import com.lewis.cp.base.BaseFragment;
import com.lewis.cp.http.APIService;
import com.lewis.cp.http.RetrofitManager;
import com.lewis.cp.model.BaseCallModel;
import com.lewis.cp.model.HomeBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018/1/23.
 */

public class HomeFragment extends BaseFragment {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.iv_right)
    ImageView ivRight;
    @BindView(R.id.banner)
    Banner banner;
    List<HomeBean.ListBean> list;
    List<String> images;


    @Override
    protected void handleBundle(Bundle bundle) {

    }


    @Override
    protected void initView(View view) {
        ivBack.setVisibility(View.INVISIBLE);
        tvTitle.setText("首页");
        images=new ArrayList<>();
        list=new ArrayList<>();

        banner.setImageLoader(new com.youth.banner.loader.ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                //此处可以自行选择，我直接用的Picasso
                Glide.with(BaseApplication.getContext()).load((String) path).into(imageView);
            }
        });
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置圆形指示器与标题
        banner.setIndicatorGravity(BannerConfig.CENTER);//设置指示器位置
        banner.setDelayTime(2000);//设置轮播时间
        getHomeData();
    }


    @Override
    protected int getLayout() {
        return R.layout.frg_home;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        banner.stopAutoPlay();
    }
    private void  getHomeData(){

        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .getHomeData()
                .enqueue(new Callback<HomeBean>() {
                    @Override
                    public void onResponse(Call<HomeBean> call, final Response<HomeBean> response) {
                        HomeBean body = response.body();
                        if (body!=null){
                            if ("N".equals(response.body().getHasException())){
                                list.addAll(response.body().getList());
                                for (HomeBean.ListBean bean:list
                                        ) {
                                    images.add(bean.getImageUrl());
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        banner.setImages(images);
                                        banner.start();
                                        tv_content.setText(response.body().getAdverInfo());
                                    }
                                });
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<HomeBean> call, Throwable t) {

                    }
                });




    }

}
