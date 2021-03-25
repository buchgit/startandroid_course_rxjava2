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

/*
onErrorReturn
onErrorResumeNext
onExceptionResumeNext не поймает как onErrorResumeNext ошибки Throwable и Error, но только Exception
retry()
 */

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName() + "=###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable<String> stringData = Observable.just("1", "2", "a", "4", "5");

        Observable<Long> observable = stringData
                .map(Long::parseLong)
//                .onErrorReturn(throwable -> {   //перехватили ошибку и вернули 0L.
//                                                // В зависимости от ошибки можно возвращать разные значения
//                    Log.d(TAG, throwable.getMessage());
//                    return 0L;//здесь будет вызван onCompleted
//                });
                //.onErrorResumeNext(Observable.just(5L, 10L, 15L));  //можно вернуть другой Observable,
                                                                    // то есть несколько значений

            .onErrorResumeNext(new Func1<Throwable, Observable<? extends Long>>() { //еще одна реализация
                                                                                    // этого метода
                @Override
                public Observable<? extends Long> call(Throwable throwable) {
                    Log.d(TAG,"onErrorResumeNext " + throwable);
                    return Observable.just(8L, 9L, 10L);
                }
            })
            .retry(3);//переподписка на обсервебл возможна, если что-то пошло не так, 3-количество переподписок

//        observable.subscribe(aLong -> Log.d(TAG, "call: " + aLong)
//                , throwable -> Log.d(TAG, throwable.getMessage()));
        observable.subscribe(aLong->Log.d(TAG,"call: " + aLong));//вывод на onNext

    }
}