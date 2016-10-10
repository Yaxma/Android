package cn.yaxma.xxf;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.yaxma.xxf.activity.BaseActivity;
import cn.yaxma.xxf.fragment.JokeFragment;
import cn.yaxma.xxf.fragment.VideoFragment;

public class MainActivity extends BaseActivity {

    private static final String FRAGMENT_TAG_JOKE = "JokeFragment";
    private static final String FRAGMENT_TAG_VIDEO = "VideoFragment";
    @BindView(R.id.drawer_layout) DrawerLayout drawerLayout;

    private JokeFragment mJokeFragment;
    private VideoFragment mVideoFragment;
    private FragmentManager mFragmentManager;
    private Fragment mFragment;

    @Override protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override protected void onCreateInit(Bundle savedInstanceState) {
        mFragmentManager = getFragmentManager();
        if (savedInstanceState != null) {
            mJokeFragment = (JokeFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG_JOKE);
            mVideoFragment = (VideoFragment) mFragmentManager.findFragmentByTag(FRAGMENT_TAG_VIDEO);
        } else {
            mJokeFragment = new JokeFragment();
            mFragment = mJokeFragment;
            FragmentTransaction fg = mFragmentManager.beginTransaction();
            fg.add(R.id.content_frame, mFragment, FRAGMENT_TAG_JOKE);
            fg.commit();
        }
    }

    @OnClick(R.id.joke_tv) void selectJoke() {
        if (mJokeFragment == null) {
            mJokeFragment = new JokeFragment();
        }
        switchFragment(mJokeFragment, FRAGMENT_TAG_JOKE);
    }

    @OnClick(R.id.video_tv) void selectVideo() {
        if (mVideoFragment == null) {
            mVideoFragment = new VideoFragment();
        }
        switchFragment(mVideoFragment, FRAGMENT_TAG_VIDEO);
    }

    @OnClick(R.id.menu_iv) void toggleDrawer() {
        if (drawerLayout != null) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawers();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }
    }

    private void switchFragment(Fragment toFragment, String tag) {
        FragmentTransaction fg = mFragmentManager.beginTransaction();
        if (toFragment.isAdded()) {
            if (mFragment != null) {
                fg.hide(mFragment);
            }
            fg.show(toFragment);
        } else {
            if (mFragment != null) {
                fg.hide(mFragment);
            }
            fg.add(R.id.content_frame, toFragment, tag);
        }
        fg.commit();
        mFragment = toFragment;
        // close drawer
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        }
    }

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }
}
