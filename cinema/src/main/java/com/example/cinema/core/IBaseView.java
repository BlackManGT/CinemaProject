package com.example.cinema.core;

import com.example.cinema.bean.CinemaBean;
import com.example.cinema.bean.MoiveBean;
import com.example.cinema.bean.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IBaseView {


    //热门电影列表
    @GET("movieApi/movie/v1/findHotMovieList")
    Observable<Result<List<MoiveBean>>> Popular(@Header("userId")int userId,
                                                @Header("sessionId")String sessionId,
                                                @Query("page") int page,
                                                @Query("count") int count);
    //正在上映电影列表
    @GET("movieApi/movie/v1/findReleaseMovieList")
    Observable<Result<List<MoiveBean>>> Being(@Header("userId")int userId,
                                                       @Header("sessionId")String sessionId,
                                                       @Query("page") int page,
                                                       @Query("count") int count);

    //推荐影院列表
    @GET("movieApi/cinema/v1/findRecommendCinemas")
    Observable<Result<List<CinemaBean>>> Cinema(@Header("userId")int userId,
                                                @Header("sessionId")String sessionId,
                                                @Query("page") int page,
                                                @Query("count") int count);
    //附近影院列表
    @GET("movieApi/cinema/v1/findNearbyCinemas")
    Observable<Result<List<CinemaBean>>> Nearby(@Header("userId")int userId,
                                                @Header("sessionId")String sessionId,
                                                @Query("longitude") String longitude,
                                                @Query("latitude") String latitude,
                                                @Query("page") int page,
                                                @Query("count") int count);
    //登录
    @POST("movieApi/user/v1/login")
    @FormUrlEncoded
    Observable<Result> login(@Query("phone")String phone,
                                               @Query("pwd")String pwd);

}
