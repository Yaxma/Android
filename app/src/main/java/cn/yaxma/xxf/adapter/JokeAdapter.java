package cn.yaxma.xxf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.yaxma.xxf.R;
import cn.yaxma.xxf.bean.Joke;

/**
 * Created by Administrator on 2016/10/10.
 */
public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {

    private List<Joke.ShowapiResBodyBean.ContentlistBean> mJokeList;
    private Context mContext;

    public JokeAdapter(Context context) {
        mContext = context;
    }

    public void addAll(List<Joke.ShowapiResBodyBean.ContentlistBean> list) {
        if (mJokeList == null) {
            mJokeList = list;
        } else {
            mJokeList.addAll(list);
        }
        notifyDataSetChanged();
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_joke, parent, false));
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        holder.contentTv.setText(Html.fromHtml(mJokeList.get(position).getText()));
    }

    @Override public int getItemCount() {
        return mJokeList == null ? 0 : mJokeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView contentTv;

        public ViewHolder(View itemView) {
            super(itemView);
            contentTv = (TextView) itemView.findViewById(R.id.content_tv);
        }
    }

}
