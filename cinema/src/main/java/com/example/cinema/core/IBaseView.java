package com.example.cinema.core;

import com.example.cinema.bean.CinemaBean;
import com.example.cinema.bean.Result;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IBaseView {
    @GET("movieApi/cinema/v1/findRecommendCinemas")
    Observable<Result<List<CinemaBean>>> Cinema(@Header("userId")int userId,
                                                @Header("sessionId")String sessionId,
                                                @Query("page") int page,
                                                @Query("count") int count);
}
