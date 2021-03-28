package com.example.startandroid_course_rxjava2;

import android.util.Log;
import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.SyncOnSubscribe;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
Урок 10. How to request data from Observable

использовать
Subscriber.class

new Subscriber<Integer>() {
    @Override
    public void onStart() {
        super.onStart();
        request(1);
    }

 */

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "=###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.range(1, 10)
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onStart() {
                        super.onStart();
                        request(1);//количество запрашиваемых данных
                    }

                    @Override
                    public void onCompleted() {
                        Log.d(TAG,"onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG,"onError " + e);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        Log.d(TAG,"onNext " + integer);
                        request(1);//запрос новых данных после каждого получения
                        //таким образом можно выбрать все данные, не указывая их количество
                        //в методе onStart()
                    }
                });


    }
}


