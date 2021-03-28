package com.example.startandroid_course_rxjava2;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

import java.util.List;


public interface WebApi {
    @GET("messages{page}.json")
    Observable<List<Message>> messages(@Path("page") int page);
}
