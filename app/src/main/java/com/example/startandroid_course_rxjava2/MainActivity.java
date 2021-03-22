package com.example.startandroid_course_rxjava2;

import android.util.Log;
import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName() + "=###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<Long> observable = Observable.interval(500,TimeUnit.MILLISECONDS);

        Observer<Long> observer = new Observer() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Object o) {
                Log.d(TAG, "onNext: "+ o);
                Log.d(TAG,Thread.currentThread().getName());

            }
        };

        observable.subscribe(observer);

    }
}