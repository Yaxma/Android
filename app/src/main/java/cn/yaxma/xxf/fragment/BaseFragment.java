package cn.yaxma.xxf.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 基类
 * Created by Administrator on 2016/10/10.
 */
public abstract class BaseFragment extends Fragment {

    public Unbinder bind;
    protected Context mContext;

    /**
     * 布局ID
     */
    protected abstract int getContentViewId();

    /**
     * onCreate设置布局后初始化
     */
    protected abstract void onCreateInit(Bundle savedInstanceState);

    @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(getContentViewId(), container, false);
        bind = ButterKnife.bind(this, rootView);
        mContext = getActivity();
        onCreateInit(savedInstanceState);
        return rootView;
    }

    @Override public void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
