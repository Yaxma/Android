package cn.yaxma.xxf.net;

import cn.yaxma.xxf.bean.Joke;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/10/10.
 */
public interface APIService {

    @GET("/joke/content/list.from")
    Call<Joke> loadJoke(@Query("key") String key, @Query("page") int page,
                        @Query("pagesize") int pageSize, @Query("sort") String sort,
                        @Query("time") String time);

}
