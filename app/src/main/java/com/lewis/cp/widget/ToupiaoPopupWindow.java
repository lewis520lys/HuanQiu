package com.lewis.cp.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lewis.cp.R;

import butterknife.BindView;
import butterknife.OnClick;

public class ToupiaoPopupWindow extends PopupWindow implements NumberKeyboardView.OnNumberClickListener, View.OnClickListener {

    public String sendMsg="";

    public TextView tv_zhuang;


    public TextView tvYue;

    NumberKeyboardView nkvKeyboard;

    Button btZhuang;

    Button btHe;

    Button btXian;

    Button btZhuangdui;

    Button btXiandui;

    Button btZhuangxiandui;

    Button btSanbao;

    public Button btCommit;

    LinearLayout idPopLayout;
    private View mPopView;
    private NumberKeyboardView nkv_keyboard;
    private String str = "";

    private int type1 = 0;
    private int type2 = 0;
    private int type3 = 0;
    private int type4 = 0;
    private int type5 = 0;
    private int type6 = 0;
    private int type7 = 0;
    public TextView tv_xian;
    public TextView tv_he;
    public TextView tv_zhuangdui;
    public TextView tv_xiandui;
    public TextView tv_zhuangxiandui;
    public TextView tv_sanbao;
    private TextView tv;


    public ToupiaoPopupWindow(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context);
        setPopupWindow();

    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.keybord_popup_window, null);
        nkv_keyboard = mPopView.findViewById(R.id.nkv_keyboard);
        tv_zhuang = mPopView.findViewById(R.id.tv_zhuang);
        tv_xian = mPopView.findViewById(R.id.tv_xian);
        tv_he = mPopView.findViewById(R.id.tv_he);
        tv_zhuangdui = mPopView.findViewById(R.id.tv_zhuangdui);
        tv_xiandui = mPopView.findViewById(R.id.tv_xiandui);
        tv_zhuangxiandui = mPopView.findViewById(R.id.tv_zhuangxiandui);
        tv = mPopView.findViewById(R.id.tv);
        tv_sanbao = mPopView.findViewById(R.id.tv_sanbao);
        tvYue = mPopView.findViewById(R.id.tv_yue);
        btZhuang = mPopView.findViewById(R.id.bt_zhuang);
        btHe = mPopView.findViewById(R.id.bt_he);
        btXian = mPopView.findViewById(R.id.bt_xian);
        btZhuangdui = mPopView.findViewById(R.id.bt_zhuangdui);
        btZhuangxiandui = mPopView.findViewById(R.id.bt_zhuangxiandui);
        btXiandui = mPopView.findViewById(R.id.bt_xiandui);
        btSanbao = mPopView.findViewById(R.id.bt_sanbao);
        btCommit = mPopView.findViewById(R.id.bt_commit);

        nkv_keyboard.setOnNumberClickListener(this);
        btZhuang.setOnClickListener(this);
        btHe.setOnClickListener(this);
        btXian.setOnClickListener(this);
        btZhuangdui.setOnClickListener(this);
        btZhuangxiandui.setOnClickListener(this);
        btXiandui.setOnClickListener(this);
        btSanbao.setOnClickListener(this);

    }

    /**
     * 设置窗口的相关属性
     */

    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                int height = mPopView.findViewById(R.id.id_pop_layout).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });
    }


    @Override
    public void onNumberReturn(String number) {

        str += number;
//
        if (type1==1){

            tv_zhuang.setText("庄:"+str+";");
        }
        if (type2==1){
            tv_he.setText("和:"+str+";");
        }
        if (type3==1){
            tv_xian.setText("闲:"+str+";");
        }
        if (type4==1){
            tv_zhuangdui.setText("庄对:"+str+";");
        }
        if (type5==1){
            tv_xiandui.setText("闲对:"+str+";");
        }
        if (type6==1){
            tv_zhuangxiandui.setText("庄闲对:"+str+";");
        }
        if (type7==1){
            tv_sanbao.setText("三宝:"+str+";");
        }

        tv.setText("");
    }

    @Override
    public void onNumberDelete() {
        if (str.length() <= 1) {
            str = "";
        } else {
            str = str.substring(0, str.length() - 1);
        }
        delectAll();
    }
    public void delectAll(){
        type1 = 0;
        type2 = 0;
        type3 = 0;
        type4 = 0;
        type5 = 0;
        type6 = 0;
        type7 = 0;
        tv_zhuang.setText("");
        tv_he.setText("");
        tv_xian.setText("");
        tv_zhuangxiandui.setText("");
        tv_zhuangdui.setText("");
        tv_xiandui.setText("");
        tv_sanbao.setText("");
        tv.setText("请下注");
           }
    private void setType() {
        type1 = 0;
        type2 = 0;
        type3 = 0;
        type4 = 0;
        type5 = 0;
        type6 = 0;
        type7 = 0;
        btZhuang.setBackgroundColor(Color.parseColor("#ffffff"));
        btXian.setBackgroundColor(Color.parseColor("#ffffff"));
        btHe.setBackgroundColor(Color.parseColor("#ffffff"));
        btXiandui.setBackgroundColor(Color.parseColor("#ffffff"));
        btZhuangxiandui.setBackgroundColor(Color.parseColor("#ffffff"));
        btZhuangdui.setBackgroundColor(Color.parseColor("#ffffff"));
        btSanbao.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void onNumberHide() {
        this.dismiss();
    }


    @Override
    public void onClick(View view) {
        str="";
        switch (view.getId()) {

            case R.id.bt_zhuang:
                //tv_zhuang.setText("");
                if (type1 == 0) {
                    setType();
                    type1 = 1;
                    btZhuang.setBackgroundColor(Color.parseColor("#C7C9C8"));

                } else {
                    type1 = 0;
                    btZhuang.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                break;
            case R.id.bt_he:
                //tv_he.setText("");
                if (type2 == 0) {
                    setType();
                    type2 = 1;
                    btHe.setBackgroundColor(Color.parseColor("#C7C9C8"));
                } else {
                    type2 = 0;
                    btHe.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                break;
            case R.id.bt_xian:
                if (type3== 0) {
                    setType();
                    type3 = 1;
                    btXian.setBackgroundColor(Color.parseColor("#C7C9C8"));
                } else {
                    type3 = 0;
                    btXian.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                break;
            case R.id.bt_zhuangdui:
                if (type4== 0) {
                    setType();
                    type4 = 1;
                    btZhuangdui.setBackgroundColor(Color.parseColor("#C7C9C8"));
                } else {
                    type4 = 0;
                    btZhuangdui.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                break;
            case R.id.bt_xiandui:
                if (type5== 0) {
                    setType();
                    type5 = 1;
                    btXiandui.setBackgroundColor(Color.parseColor("#C7C9C8"));
                } else {
                    type5 = 0;
                    btXiandui.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                break;
            case R.id.bt_zhuangxiandui:
                if (type6== 0) {
                    setType();
                    type6 = 1;
                    btZhuangxiandui.setBackgroundColor(Color.parseColor("#C7C9C8"));
                } else {
                    type6= 0;
                    btZhuangxiandui.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                break;
            case R.id.bt_sanbao:
                if (type7== 0) {
                    setType();
                    type7 = 1;
                    btSanbao.setBackgroundColor(Color.parseColor("#C7C9C8"));
                } else {
                    type7 = 0;
                    btSanbao.setBackgroundColor(Color.parseColor("#ffffff"));
                }
                break;

        }
    }

}