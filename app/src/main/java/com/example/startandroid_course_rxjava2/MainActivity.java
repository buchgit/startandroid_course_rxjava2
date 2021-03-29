package com.example.startandroid_course_rxjava2;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.TestObserver;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.subscribers.TestSubscriber;


import java.util.concurrent.TimeUnit;


/*
Урок 13. Тестирование. RxJavaPlugins.
TestObserver/TestSubscriber -класс представляет собой тестовый подписчик
 */

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "=###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //for Flowable//
        TestSubscriber<Integer> testSubscriber = new TestSubscriber<>();
        Flowable.just(1, 2, 3, 4, 5).subscribe((FlowableSubscriber<? super Integer>) testSubscriber);
        testSubscriber.assertValueCount(5);




        TestObserver<Integer> testObserver = new TestObserver<>();
        Observable.just(1, 2, 3, 4, 5).subscribe((Observer<? super Integer>) testObserver);
        testObserver.assertValues(1, 2, 3, 4, 5);

        Observable.interval(100, TimeUnit.MILLISECONDS)
                .take(10)
                .subscribe((Consumer<? super Long>) testObserver);

        try {
            testObserver.await(); //ждем окончания отправки данных обсервеблом
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            testObserver.await(100,TimeUnit.MILLISECONDS);//ждем с таймаутом
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        testObserver.assertComplete(); //проверяем на законченность

        //Оператором test вы также можете создать TestObserver:
        Observable.just("1", "2", "3", "4", "5")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        return Integer.parseInt(s);
                    }
                })
                .test()
                .assertValues(1, 2, 3, 4, 5);

        //глобальный обработчик ошибок, если в обсерверах нет своих
        RxJavaPlugins.setErrorHandler(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d(TAG,"error " + throwable.getCause());
            }
        });

    }
}


