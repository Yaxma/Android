package cn.yaxma.xxf.activity;

import android.app.Activity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.yaxma.xxf.utils.AppBarUtil;

/**
 * 基类
 * Created by Administrator on 2016/10/6.
 */
public abstract class BaseActivity extends Activity {

    public Unbinder bind;

    /**
     * 布局ID
     */
    protected abstract int getContentViewId();

    /**
     * onCreate设置布局后初始化
     */
    protected abstract void onCreateInit(Bundle savedInstanceState);

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppBarUtil.setTranslucentBar(this);
        setContentView(getContentViewId());
        bind = ButterKnife.bind(this);
        onCreateInit(savedInstanceState);
    }

    @Override protected void onResume() {
        super.onResume();
    }

    @Override protected void onPause() {
        super.onPause();
    }

    @Override protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
