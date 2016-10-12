package cn.yaxma.xxf.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.yaxma.xxf.R;
import cn.yaxma.xxf.adapter.JokeAdapter;
import cn.yaxma.xxf.bean.Joke;
import cn.yaxma.xxf.net.APIService;
import cn.yaxma.xxf.net.Constant;
import cn.yaxma.xxf.net.RetrofitWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 笑话
 * Created by Administrator on 2016/10/10.
 */
public class JokeFragment extends BaseFragment implements XRecyclerView.LoadingListener {

    @BindView(R.id.recycler_view) XRecyclerView mRecyclerView;

    private List<Joke.ResultBean.DataBean> mJokeList = new ArrayList<>();

    private JokeAdapter mAdapter;

    private int page = 1;
    private static final int REFRESH = 0;
    private static final int LOADMORE = 1;

    @Override protected int getContentViewId() {
        return R.layout.fragment_joke;
    }

    @Override protected void onCreateInit(Bundle savedInstanceState) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallBeat);
        mRecyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        mRecyclerView.setLoadingListener(this);
        mAdapter = new JokeAdapter(mContext);
        mRecyclerView.setAdapter(mAdapter);
        loadJoke(REFRESH);
    }

    private void loadJoke(final int refresh_type) {
        String time = String.valueOf(System.currentTimeMillis()).toString().substring(0, 10);
        Call<Joke> call = RetrofitWrapper.getInstance().create(APIService.class)
                .loadJoke(Constant.KEY, page, 20, "desc", time);
        call.enqueue(new Callback<Joke>() {
            @Override public void onResponse(Call<Joke> call, Response<Joke> response) {
                if (response.isSuccessful()) {
                    Joke result = response.body();
                    if (result != null) {
                        List<Joke.ResultBean.DataBean> list = result.getResult().getData();
                        if (refresh_type == REFRESH) {
                            mJokeList = list;
                        } else {
                            mJokeList.addAll(list);
                        }
                        mAdapter.updateData(mJokeList);
                    }
                }
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
            }

            @Override public void onFailure(Call<Joke> call, Throwable t) {
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
            }
        });
    }

    @Override public void onRefresh() {
        page = 1;
        loadJoke(REFRESH);
    }

    @Override public void onLoadMore() {
        page += 1;
        loadJoke(LOADMORE);
    }
}
