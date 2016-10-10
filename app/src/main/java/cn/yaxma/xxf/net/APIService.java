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

    @GET("/showapi_open_bus/showapi_joke/joke_text")
    Call<Joke> loadJoke(@Header("apikey") String apikey, @Query("page") String page);

}
