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
import rx.functions.Func2;
import rx.schedulers.Schedulers;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/*
Урок 8. Операторы объединения
merge - объединяет, обсервелы идут параллельно
concat - параллельность только для одного обсервебла, то есть последовательно
amb - ждет, который из абсервеблов будет первым, того и будет выводить
zip - склеивает два обсервебла по функции Func2(in,in,out), ждет более медленный обсервебл для пары
combineLatest - как zip склеивает два обсервебла, но пара берется всякий раз,
                когда приходит значение любого из двух обсервеблов, при этом из второго
                берется последнее значение
withLatestFrom - на каждого значение основного обсервебла берется последнее значение
                второго обсервебла. Можно использовать более чем на двух обсервеблах.
flatMap - ...  в стримах разворачивает по-элементно все коллекции. Тут нечто похожее.




 */

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "=###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable observable1 = Observable.interval(1,TimeUnit.MILLISECONDS).take(300);
        Observable observable2 = Observable.range(100,200).timeInterval();


        observable1.mergeWith(observable2).subscribe(new Action1() {
            @Override
            public void call(Object o) {
                Log.d(TAG, "call: "+ o);
            }
        });

        //Observable.merge(observableArray, n) //n - ограничение по количеству observable

        //observable1.concatWith(observable2)

        //Observable.amb(observable1, observable2)

        //observable1
        //        .zipWith(observable2, new Func2<Long, Long, String>() {
        //            @Override
        //            public String call(Long aLong, Long aLong2) {
        //                return String.format("%s and %s", aLong, aLong2);
        //            }
        //        })

        //Observable
        //        .combineLatest(observable1, observable2, new Func2<Long, Long, String>(){...})

        //observable1
        //        .withLatestFrom(observable2, new Func2<Long, Long, String>() {...}
//        observable1
//                .withLatestFrom(observable2, observable3, new Func3<Long, Long, Long, String>() {

    }
}