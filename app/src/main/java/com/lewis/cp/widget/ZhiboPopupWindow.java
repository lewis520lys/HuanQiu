package com.lewis.cp.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.lewis.cp.R;
import com.lewis.cp.widget.tv.danmaku.ijk.media.widget.media.IjkVideoView;

import tv.danmaku.ijk.media.player.IjkMediaPlayer;


public class ZhiboPopupWindow extends PopupWindow {
    private View mPopView;
    private IjkVideoView videoView;
    private ImageView iv_close;


    public ZhiboPopupWindow(Activity context,String URL,int witdh) {
        super(context);
        // TODO Auto-generated constructor stub
        init(context,URL);
        setPopupWindow(witdh);
    }

    /**
     * 初始化
     *
     * @param
     */
    private void init(Activity activity,String URL) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(activity);
        //绑定布局
        mPopView = inflater.inflate(R.layout.layout_zhibo, null);

        videoView = (IjkVideoView) mPopView.findViewById(R.id.videoView);
        iv_close = (ImageView) mPopView.findViewById(R.id.iv_close);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ZhiboPopupWindow.this.dismiss();
            }
        });
// init player
        IjkMediaPlayer.loadLibrariesOnce(null);
        IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        videoView.setVideoPath(URL);

        videoView.start();
    /*会有几秒延时*/
        //mVideoView.setVideoPath();
    }

    /**
     * 设置窗口的相关属性
     */

    private void setPopupWindow(int witdh) {
        this.setContentView(mPopView);// 设置View
        this.setWidth(witdh);// 设置弹出窗口的宽
        this.setHeight(500);// 设置弹出窗口的高
        this.setFocusable(false);// 设置弹出窗口可
        this.setOutsideTouchable(false);
        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
//        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁
//
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                // TODO Auto-generated method stub
//
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y > 5800) {
//                        dismiss();
//                    }
//                }
//                return true;
//            }
//        });
   }


}