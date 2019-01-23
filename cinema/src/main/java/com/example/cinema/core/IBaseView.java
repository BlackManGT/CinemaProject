package com.example.cinema.core;

import com.example.cinema.bean.CinemaBean;
import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IBaseView {
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
    Observable<Result<LoginBean>> login(@Field("phone") String phone,
                                        @Field("pwd") String pwd);
    @POST("movieApi/user/v1/registerUser")
    @FormUrlEncoded
    Observable<Result> regiest(@Field("nickName") String nickName,
                                        @Field("phone") String phone,
                                       @Field("pwd") String pwd,
                                       @Field("pwd2") String pwd2,
                                       @Field("sex") int sex,
                                       @Field("birthday") String birthday,
                                       @Field("imei") String imei,
                                       @Field("ua") String ua,
                                       @Field("screenSize") String screenSize,
                                       @Field("os") String os,
                                       @Field("email") String email);

}
