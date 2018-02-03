package com.lewis.cp.view.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lewis.cp.MainActivity;
import com.lewis.cp.R;
import com.lewis.cp.base.BaseActivity;
import com.lewis.cp.http.APIService;
import com.lewis.cp.http.RetrofitManager;
import com.lewis.cp.model.BaseCallModel;
import com.lewis.cp.model.CurdUserBean;
import com.lewis.cp.utils.ImageToBase;
import com.lewis.cp.widget.SelectPicPopupWindow;
import com.lewis.cp.widget.SelectSexWindow;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

import butterknife.OnClick;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;


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
    TextView tvSex;

    private SelectPicPopupWindow menuWindow;
    private ArrayList<ImageItem> images;
    private String nickName;
    private String sex="0";
    private SelectSexWindow sexMenu;

    @Override
    protected void initData() {
        requestData();
    }

    @Override
    protected void initView() {
        tvTitle.setText("基本资料");
        tvRight.setText("保存");

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


    @OnClick({R.id.iv_back, R.id.tv_right, R.id.iv_head,R.id.rl_sex})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
                case R.id.rl_sex:
                    sexMenu = new SelectSexWindow(UserInfoAct.this, itemsOnClick);
                    //显示窗口
                    sexMenu.showAtLocation(UserInfoAct.this.findViewById(R.id.root), Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0);
                    break;
            case R.id.tv_right:
                nickName=tvName.getText().toString().trim();
                if ("男".equals(tvSex.getText().toString().trim())){
                    sex="0";
                }else {
                    sex="1";
                }
                if (TextUtils.isEmpty(nickName)) {
                    showToast("请输入昵称");
                    return;
                }

                save();
                break;
            case R.id.iv_head:
                //实例化SelectPicPopupWindow
                menuWindow = new SelectPicPopupWindow(UserInfoAct.this, itemsOnClick);
                //显示窗口
                menuWindow.showAtLocation(UserInfoAct.this.findViewById(R.id.root), Gravity.BOTTOM| Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
                break;
        }
    }

    private void save() {
        Map<String, String> map = new HashMap<>();
        map.put("userName", user.userName);
        map.put("nickName",nickName);
        map.put("sex",sex);
        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .saveUserInfo(map)
                .enqueue(new Callback<BaseCallModel>() {
                    @Override
                    public void onResponse(Call<BaseCallModel> call, Response<BaseCallModel> response) {
                        BaseCallModel body = response.body();
                        if (body != null) {
                            showToast(response.body().info);

                        }

                    }

                    @Override
                    public void onFailure(Call<BaseCallModel> call, Throwable t) {

                    }
                });
    }

    private int IMAGE_PICKER=111;//相册
    private int REQUEST_CODE_SELECT=112;//相机

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {

            Intent intent = new Intent(UserInfoAct.this, ImageGridActivity.class);
            switch (v.getId()) {
                case R.id.btn_take_photo:

                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
                    startActivityForResult(intent, REQUEST_CODE_SELECT);
                    menuWindow.dismiss();
                    break;
                case R.id.btn_pick_photo:

                    startActivityForResult(intent, IMAGE_PICKER);
                    menuWindow.dismiss();
                    break;
                case R.id.bt_nan:
                    tvSex.setText("男");
                    sexMenu.dismiss();
                    break;
                    case R.id.bt_nv:
                        tvSex.setText("女");
                        sexMenu.dismiss();
                    break;
            }


        }

    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Glide.with(getApplicationContext()).load(new File(images.get(0).path)).placeholder(R.mipmap.head_default).into(ivHead);

                compress(images.get(0).path);
            } else {
                Toast.makeText(this, "没有数据", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void compress(String path) {
        Luban.with(this)
                .load(path)                     //传人要压缩的图片
                .ignoreBy(200)        //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }
                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        uploadPic(file.getPath());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过去出现问题时调用
                    }
                }).launch();    //启动压缩
//    //  File newFile = new CompressHelper.Builder(this)
////                .setMaxWidth(720)  // 默认最大宽度为720
////                .setMaxHeight(960) // 默认最大高度为960
////                .setQuality(80)    // 默认压缩质量为80
////                .setFileName(yourFileName) // 设置你需要修改的文件名
////                .setCompressFormat(Bitmap.CompressFormat.JPEG) // 设置默认压缩为jpg格式
////                .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
////                        Environment.DIRECTORY_PICTURES).getAbsolutePath())
////                .build()
////                .compressToFile(oldFile);
    }
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");
    private void uploadPic(String p){
//        Map<String, String> map = new HashMap<>();
//        map.put("userName", user.userName);
//        map.put("imgStr", ImageToBase.getImgStr(images.get(0).path));
        String json="{\"userName\":\""+user.userName+"\",\"imgStr\":\""+ImageToBase.getImgStr(p)+"\"}";
        RequestBody body = RequestBody.create(JSON,json);
        RetrofitManager.getInstance()
                .createReq(APIService.class)
                .unLoadPic(body)
                .enqueue(new Callback<BaseCallModel>() {
                    @Override
                    public void onResponse(Call<BaseCallModel> call, Response<BaseCallModel> response) {
                        BaseCallModel body = response.body();
                        if (body != null) {
                            showToast(response.body().info);

                        }

                    }

                    @Override
                    public void onFailure(Call<BaseCallModel> call, Throwable t) {

                    }
                });

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
                           runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   Glide.with(UserInfoAct.this).load(body.headImg).placeholder(R.mipmap.head_default).into(ivHead);
                                   if (body.sex==1){
                                       tvSex.setText("女");
                                   }else {
                                       tvSex.setText("男");
                                   }
                                   tvName.setText(body.nickName);
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
