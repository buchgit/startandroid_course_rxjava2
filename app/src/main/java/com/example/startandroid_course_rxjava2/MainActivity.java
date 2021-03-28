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
Урок 10. SyncOnSubscribe
если все-таки необходимо создать свой Observable,
то RxJava предоставляет backpressure обертку - SyncOnSubscribe

На вход нам необходимо передать две функции.

Первая - должна возвращать начальное состояние. Мы возвращаем 1.
Далее это начальное состояние пойдет во вторую функцию как первый параметр.

Вторая функция берет значение (текущее состояние),
и либо отправляет его подписчику (если <= 20), либо завершает последовательность.
Вернуть вторая функция должна новое состояние.
В нашем случае мы просто увеличиваем число на 1 и получаем это новое состояние,
которое снова придет к нам в следующем вызове функции.
Т.е. каждый следующий вызов функции будет получать значение,
которое вернул предыдущий вызов.

backpressure проявляется,если Observable и Observer работают в разных потоках.
Если в одном, то Observable будет синхронно ждать более медленного Observer-a.
 */

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "=###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Метод createStateful создает OnSubscribe,
        // который будет поддерживать backpressure
        Observable.OnSubscribe<Integer>
                onSubscribe = SyncOnSubscribe.createStateful(
                new Func0<Integer>() {
                    @Override
                    public Integer call() {
                        return 1;
                    }
                },
                new Func2<Integer, Observer<? super Integer>, Integer>() {
                    @Override
                    public Integer call(Integer integer, Observer<? super Integer> observer) {
                        Log.d(TAG,"sync call " + integer);
                        if (integer <= 20) {
                            observer.onNext(integer);
                        } else {
                            observer.onCompleted();
                        }
                        return integer + 1;
                    }
                }
        );

        Observable.create(onSubscribe)
                .subscribeOn(Schedulers.computation())
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.d(TAG, "post: "+integer);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNext(Integer integer) {
                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onNext: "+ integer);
            }
        });


    }
}


