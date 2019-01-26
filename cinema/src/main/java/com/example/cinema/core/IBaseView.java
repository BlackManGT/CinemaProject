package com.example.cinema.core;

import com.example.cinema.bean.CinemaBean;
import com.example.cinema.bean.CinemaDetalisBean;
import com.example.cinema.bean.IDMoiveDetalisOne;
import com.example.cinema.bean.IDMoiveDetalisTwo;
import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.MoiveBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.TicketBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface IBaseView {
    //热门影院列表
    @GET("movieApi/movie/v1/findHotMovieList")
    Observable<Result<List<MoiveBean>>> Popular(@Header("userId")int userId,
                                               @Header("sessionId")String sessionId,
                                               @Query("page") int page,
                                               @Query("count") int count);
    //正在上映列表
    @GET("movieApi/movie/v1/findReleaseMovieList")
    Observable<Result<List<MoiveBean>>> Being(@Header("userId")int userId,
                                                @Header("sessionId")String sessionId,
                                                @Query("page") int page,
                                                @Query("count") int count);
    //即将上映列表
    @GET("movieApi/movie/v1/findComingSoonMovieList")
    Observable<Result<List<MoiveBean>>> Soon(@Header("userId")int userId,
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
    //上传用户头像
    @POST("movieApi/user/v1/verify/uploadHeadPic")
    @FormUrlEncoded
    Observable<Result> uploadHeadPic(@Header("userId") String userId,
                                     @Header("sessionId") String sessionId,
                                     @Field("image") String image);

    //注册
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
    //根据电影id查询电影信息
    @GET("movieApi/movie/v1/findMoviesById")
    Observable<Result<IDMoiveDetalisOne>> IDMoivedetalisOne(@Header("userId")int userId,
                                                         @Header("sessionId")String sessionId,
                                                         @Query("movieId") int movieId);
    //登录
    @POST("movieApi/user/v1/login")
    @FormUrlEncoded
    Observable<Result<LoginBean>> login(@Field("phone") String phone,
                                        @Field("pwd") String pwd);
    //购票记录
    @GET("movieApi/user/v1/verify/findUserBuyTicketRecordList")
    Observable<Result<List<TicketBean>>> findTicket(@Query("userId") int userId,
                                                    @Query("sessionId") String sessionId,
                                                    @Query("page") int page,
                                                    @Query("count")int count,
                                                    @Query("status")int status);

    //根据电影Id查询电影信息2
    @GET("movieApi/movie/v1/findMoviesDetail")
    Observable<Result<IDMoiveDetalisTwo>> IDMoivedetalisTwo(@Header("userId")int userId,
                                                            @Header("sessionId")String sessionId,
                                                            @Query("movieId") int movieId);

    //查询电影信息明细
    @GET("movieApi/cinema/v1/findCinemaInfo")
    Observable<Result<CinemaDetalisBean>> CinemaDetalis(@Header("userId")int userId,
                                                        @Header("sessionId")String sessionId,
                                                        @Query("cinemaId") int cinemaId);

}
