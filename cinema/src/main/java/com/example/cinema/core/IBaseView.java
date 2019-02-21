package com.example.cinema.core;

import com.example.cinema.bean.CDBean;
import com.example.cinema.bean.CPBean;
import com.example.cinema.bean.CinemaBean;
import com.example.cinema.bean.CinemaById;
import com.example.cinema.bean.CinemaDetalisBean;
import com.example.cinema.bean.CinemaRecy;
import com.example.cinema.bean.FilmReviewBean;
import com.example.cinema.bean.IDMoiveDetalisOne;
import com.example.cinema.bean.IDMoiveDetalisTwo;
import com.example.cinema.bean.LoginBean;
import com.example.cinema.bean.MoiveBean;
import com.example.cinema.bean.MyIsFollowListBean;
import com.example.cinema.bean.MyIsFollowListTwoBean;
import com.example.cinema.bean.PurchaseBean;
import com.example.cinema.bean.Result;
import com.example.cinema.bean.SigninStatusBean;
import com.example.cinema.bean.SysMsgListBean;
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
    Observable<Result<List<TicketBean>>> findTicket(@Header("userId") String userId,
                                                    @Header("sessionId") String sessionId,
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
    //查看影院影片
    @GET("movieApi/movie/v1/findMovieListByCinemaId")
    Observable<Result<List<CinemaById>>> cinemaById(@Query("cinemaId")int cinemaId);
    //根据电影id和影院id查看影院排期
    @GET("movieApi/movie/v1/findMovieScheduleList")
    Observable<Result<List<CinemaRecy>>> cinemaRecy(@Query("cinemasId")int cinemasId,
                                                    @Query("movieId") int movieId);


    //查询电影影评
    @GET("movieApi/movie/v1/findAllMovieComment")
    Observable<Result<List<FilmReviewBean>>> FilmReview(@Header("userId")int userId,
                                                        @Header("sessionId")String sessionId,
                                                        @Query("movieId") int movieId,
                                                        @Query("page") int page,
                                                        @Query("count") int count);

    //根据电影ID查询当前排片该电影的影院列表
    @GET("movieApi/movie/v1/findCinemasListByMovieId")
    Observable<Result<List<PurchaseBean>>> Purchase(@Query("movieId") int movieId);


    //重置密码
    @POST("movieApi/user/v1/verify/modifyUserPwd")
    @FormUrlEncoded
    Observable<Result> ResetPwd(@Header("userId")int userId,
                                                 @Header("sessionId")String sessionId,
                                                 @Field("oldPwd") String oldPwd,
                                                 @Field("newPwd") String newPwd,
                                                 @Field("newPwd2") String newPwd2);

    //意见反馈
    @POST("movieApi/tool/v1/verify/recordFeedBack")
    @FormUrlEncoded
    Observable<Result> Feedback(@Header("userId")int userId,
                             @Header("sessionId")String sessionId,
                             @Field("content") String content);

    //关注电影
    @GET("movieApi/movie/v1/verify/followMovie")
    Observable<Result> isFollow(@Header("userId")int userId,
                                                        @Header("sessionId")String sessionId,
                                                        @Query("movieId") int movieId);
    //取消关注电影
    @GET("movieApi/movie/v1/verify/cancelFollowMovie")
    Observable<Result> NoFilmFollow(@Header("userId")int userId,
                                @Header("sessionId")String sessionId,
                                @Query("movieId") int movieId);
    //影片关注列表
    @GET("movieApi/movie/v1/verify/findMoviePageList")
    Observable<Result<List<MyIsFollowListBean>>> MyisFollowList(@Header("userId")int userId,
                                                                @Header("sessionId")String sessionId,
                                                                @Query("page") int page,
                                                                @Query("count") int count);

    //对影片评论
    @POST("movieApi/movie/v1/verify/movieComment")
    @FormUrlEncoded
    Observable<Result> addFilmComment(@Header("userId")int userId,
                                @Header("sessionId")String sessionId,
                                @Field("movieId") int movieId,
                                      @Field("commentContent") String commentContent);

    //用户签到
    @GET("movieApi/user/v1/verify/userSignIn")
    Observable<Result> Signin(@Header("userId")int userId,
                              @Header("sessionId")String sessionId);

    //下单
    @POST("movieApi/movie/v1/verify/buyMovieTicket")
    @FormUrlEncoded
    Observable<Result> buyMovieTicket(@Header("userId")String userId,
                                      @Header("sessionId")String sessionId,
                                      @Field("scheduleId") int scheduleId,
                                      @Field("amount")int amount,
                                      @Field("sign")String sign);
    @POST("movieApi/movie/v1/verify/pay")
    @FormUrlEncoded
    Observable<Result> pay(@Header("userId")String userId,
                                      @Header("sessionId")String sessionId,
                                      @Field("payType") int payType,
                                      @Field("orderId")String orderId);
    //微信登录
    @POST("movieApi/user/v1/weChatBindingLogin")
    @FormUrlEncoded
    Observable<Result<LoginBean>> Wxlogin(@Field("code")String code);


    //添加用户对评论的回复
    @POST("movieApi/movie/v1/verify/commentReply")
    @FormUrlEncoded
    Observable<Result> CommentReply(@Header("userId")String userId,
                           @Header("sessionId")String sessionId,
                           @Field("commentId") int payType,
                           @Field("replyContent")String orderId);

    //查询用户关注的影院信息
    @GET("movieApi/cinema/v1/verify/findCinemaPageList")
    Observable<Result<List<MyIsFollowListTwoBean>>> CinemaAttention(@Header("userId")int userId,
                                                                         @Header("sessionId")String sessionId,
                                                                         @Query("page") int page,
                                                                         @Query("count") int count);
    //取消关注用户影院
    @GET("movieApi/cinema/v1/verify/cancelFollowCinema")
    Observable<Result> CancelCinema(@Header("userId")int userId,
                                   @Header("sessionId")String sessionId,
                                   @Query("cinemaId") int cinemaId);

    //关注影院
    @GET("movieApi/cinema/v1/verify/followCinema")
    Observable<Result> MyisFollowListTCinema(@Header("userId")int userId,
                                       @Header("sessionId")String sessionId,
                                       @Query("cinemaId") int cinemaId);
    //点赞
    @POST("movieApi/movie/v1/verify/movieCommentGreat")
    @FormUrlEncoded
    Observable<Result> commentGreat(@Header("userId")String userId,
                                    @Header("sessionId")String sessionId,
                                    @Field("commentId")int commentId);

    //修改用户信息
    @POST("movieApi/user/v1/verify/modifyUserInfo")
    @FormUrlEncoded
    Observable<Result> Update(@Header("userId")int userId,
                              @Header("sessionId")String sessionId,
                           @Field("nickName")String nickName,
                           @Field("sex") int sex,
                           @Field("email")String email);

    //系统信息
    @GET("movieApi/tool/v1/verify/findAllSysMsgList")
    Observable<Result<List<SysMsgListBean>>> SystemMessage(@Header("userId")int userId,
                                                           @Header("sessionId")String sessionId,
                                                           @Query("page") int page,
                                                           @Query("count") int count);
    //影院详情
    @GET("movieApi/cinema/v1/findCinemaInfo")
    Observable<Result<CDBean>> CDBeans(@Header("userId")int userId,
                                      @Header("sessionId")String sessionId,
                                      @Query("cinemaId") int cinemaId);
    //查询影院用户评论列表
    @GET("movieApi/cinema/v1/findAllCinemaComment")
    Observable<Result<List<CPBean>>> CPBeans(@Header("userId")int userId,
                                             @Header("sessionId")String sessionId,
                                             @Query("cinemaId") int cinemaId,
                                             @Query("page") int page,
                                             @Query("count") int count);


    //签到状态
    @GET("movieApi/user/v1/verify/findUserHomeInfo")
    Observable<Result<SigninStatusBean>> SigninStatus(@Header("userId")int userId,
                                                      @Header("sessionId")String sessionId);

    //版本更新
    @GET("movieApi/tool/v1/findNewVersion")
    Observable<Result> Update(@Header("userId")int userId,
                              @Header("sessionId")String sessionId,
                              @Header("ak")String ak);

}
