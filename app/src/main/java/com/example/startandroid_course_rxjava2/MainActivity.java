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
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/*
Урок 10. Backpressure

 */

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "=###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(30)
                .subscribeOn(Schedulers.computation())
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.d(TAG, "post: " + aLong);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(along -> {
                            try {
                                TimeUnit.MILLISECONDS.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            Log.d(TAG, "onNext: " + along.toString());
                        }
                        , throwable -> throwable.printStackTrace()
                        , () -> Log.d(TAG, "onComplete : "));




    }
}


