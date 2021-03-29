package com.example.startandroid_course_rxjava2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

/*
Урок 11. RxJava 2

Источников данных в RxJava :
Observable и Flowable, Single, Completable, Maybe

Observable не поддерживает backpressure

Action1 переименован в Consumer, а Action0 в Action

Используйте Observable, если
1. Мало данных и, накапливаясь, они не смогут привести к OutOfMemoryError.

2. Вы работаете с данными, которые не поддерживают backpressure, например:
touch-события.

Используйте Flowable, если
1. Данные поддерживают backpressure, например: чтение с диска.

//***************************** Observable ****************

public interface Observer<T> {
    void onSubscribe(@NonNull Disposable d);//новый метод в RxJava2
    void onNext(@NonNull T t);
    void onError(@NonNull Throwable e);
    void onComplete();
}
public interface Disposable { //новый тип объекта
    void dispose();
    boolean isDisposed();
}

//новый тип объекта (обертка) вместо(для) Observer
DisposableObserver disposableObserver = new DisposableObserver<Integer>() {
    @Override
    public void onNext(@NonNull Integer integer) {

    }

    @Override
    public void onError(@NonNull Throwable e) {

    }

    @Override
    public void onComplete() {

    }
};

//***************************** Flowable ****************
public interface Subscriber<T> {
    public void onSubscribe(Subscription s);
    public void onNext(T t);
    public void onError(Throwable t);
    public void onComplete();
}

public interface Subscription {
    public void request(long n);
    public void cancel();
}


//********************* Single *************************************
Этот источник предоставляет либо один элемент в onNext, либо ошибку
в onError, после чего передача данных будет считаться завершенной.
onComplete не возвращает

подписчик:
public interface SingleObserver  {
    void onSubscribe(@NonNull Disposable d);
    void onSuccess(@NonNull Long aLong);
    void onError(@NonNull Throwable e);
}


//********************* Completable *************************************
Этот источник предоставит вам либо onComplete, либо ошибку в onError. Никаких данных в onNext он не передаст

//********************* Maybe *************************************
Его можно описать как Single OR Completable. Т.е. вам придет либо одно значение в onNext,
 либо onCompleted, но не оба. Также может прийти onError

Для Maybe, аналогично с Single и Completable, есть специальные подписчики:
MaybeObserver - аналог обычного Observer, но с методом onSuccess вместо onNext
DisposableMaybeObserver - аналог DisposableObserver, но с методом onSuccess вместо onNext

//******************* Action, Function ***********************
Action, Function

Cписок, как были переименованы (или удалены) Action и Function

Action0 -> Action
Action1 -> Consumer
Action2 -> BiConsumer
ActionN -> Consumer<Object[]>
Action3 - Action9 -> удалены

Func0 -> Callable
Func1 -> Function
Func2 -> BiFunction
Func3 - Func9 -> Function3 - Function9

 */




public class MainActivity extends AppCompatActivity {

    private final static String TAG = "=###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Flowable<Integer> flowable = Flowable.range(1, 10);

        flowable.subscribe(new Subscriber<Integer>() {//Subscriber - нужно делать request
                                                        //в onSubscribe
            @Override
            public void onSubscribe(Subscription s) {
                s.request(Long.MAX_VALUE);  //нужно делать запрос, иначе данные не будут
                                            //отправляться из Flowable
            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });

        //Если использовать DisposableSubscriber вместо Subscriber , не нужен request
        Disposable disposable = flowable.subscribeWith(new DisposableSubscriber<Integer>() {
            @Override
            public void onNext(Integer integer) {
            }
            @Override
            public void onError(Throwable t) {
            }
            @Override
            public void onComplete() {
            }
        });
        // ...
        disposable.dispose();

        //Single

        //для Single свой тип подписчика SingleObserver
        single.subscribe(new SingleObserver<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onSuccess(@NonNull Long aLong) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }
        });

    }
}


