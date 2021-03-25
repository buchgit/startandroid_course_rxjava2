package com.example.startandroid_course_rxjava2;

import android.util.Log;
import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName() + "=###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> stringData = Observable.just("1", "2", "a", "4", "5");

        Observable<Long> observable = stringData
                .map(Long::parseLong);

        observable.subscribe(aLong -> Log.d(TAG, "call: " + aLong)
                , throwable -> Log.d(TAG, throwable.getMessage()));

    }
}