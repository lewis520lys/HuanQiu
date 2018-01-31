package com.lewis.cp.base;


import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.lewis.cp.utils.ForbadClick;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment implements View.OnClickListener, View.OnLongClickListener,AdapterView.OnItemClickListener,TextView.OnEditorActionListener {

    protected Resources res;
    protected BaseApplication baseApp;
    protected BaseActivity holdActivity;
    private Unbinder unbinder;
   @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.holdActivity = (BaseActivity) context;

        res = holdActivity.getApplicationContext().getResources();
        baseApp = (BaseApplication) holdActivity.getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);

        if (null != getArguments()) {
            handleBundle(getArguments());
        }

        unbinder = ButterKnife.bind(this, view);


        initView(view);

        return view;
    }

    @Override
    public void onResume() {
        //统计页面

        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    @Override
    public void onDetach() {
        unbinder.unbind();

        super.onDetach();
    }

    /**
     * 处理bundle,第一个被调用,不要做关于view的操作
     *
     * @param bundle 从宿主传过来的bundle,不必非空判断
     */
    protected abstract void handleBundle(Bundle bundle);



    /**
     * 获取布局下的view
     *
     * @param view 当前fragment的布局view对象
     */
    protected abstract void initView(View view);


    /**
     * 设置当前fragment的layout
     *
     * @return 当前界面的布局id
     */
    protected abstract int getLayout();

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return holdActivity;
    }

    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (null != fragment) {
            getHoldingActivity().addFragment(fragment);
        }
    }

    //移除fragment
    protected void removeFragment() {
        getHoldingActivity().removeFragment();
    }



    @Override
    public void onClick(View v) {
        if(ForbadClick.isFastDoubleClick(v)){return;}
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    }



    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            onEditorActionSearch();
            return true;
        }
        return false;
    }

    public void onEditorActionSearch() {
    }


}