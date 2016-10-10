package cn.yaxma.xxf.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

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

    private List<Joke.ShowapiResBodyBean.ContentlistBean> mJokeList = new ArrayList<>();

    private JokeAdapter mAdapter;

    private int page = 1;

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
        loadJoke();
    }

    private void loadJoke() {
        Call<Joke> call = RetrofitWrapper.getInstance().create(APIService.class)
                .loadJoke(Constant.APIKEY, String.valueOf(page));
        call.enqueue(new Callback<Joke>() {
            @Override public void onResponse(Call<Joke> call, Response<Joke> response) {
                if (response.isSuccessful()) {
                    Joke result = response.body();
                    if (result != null) {
                        mJokeList = result.getShowapi_res_body().getContentlist();
                        mAdapter.addAll(mJokeList);
                    }
                }
                mRecyclerView.refreshComplete();
                mRecyclerView.loadMoreComplete();
            }

            @Override public void onFailure(Call<Joke> call, Throwable t) {
                Log.e("JokeFragment", "onFailure: " + t.toString());
            }
        });
    }

    @Override public void onRefresh() {
        page = 1;
        loadJoke();
    }

    @Override public void onLoadMore() {
        page += 1;
        loadJoke();
    }
}
