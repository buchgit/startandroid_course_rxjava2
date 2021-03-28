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
Урок 9. Retrolambda.

Подключение к проекту
Чтобы иметь возможность использовать лямбда, необходимо выполнить несколько шагов:

1) Скачать и использовать, как основной, JDK 8.

2) Добавить в gradle-файл проекта строки

buildscript {
  repositories {
     mavenCentral()
  }

  dependencies {
     classpath 'me.tatarka:gradle-retrolambda:3.2.3'
  }
}

3) Добавить в gradle-файл app-модуля строки
apply plugin: 'me.tatarka.retrolambda'

android {
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

ЛЯМБДЫ РАБОТАЮТ И БЕЗ ЭТИХ НАСТРОЕК 28.03.21

 */

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "=###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Observable.just("1", "2", "3", "4", "5")
                .map(s -> Integer.parseInt(s))
                .subscribe(
                        s -> Log.d(TAG,"onNext " + s),
                        throwable -> Log.d(TAG,"onError " + throwable),
                        () -> Log.d(TAG,"onCompleted")
                );


    }
}


