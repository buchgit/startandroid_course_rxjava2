package com.example.startandroid_course_rxjava2;

import android.util.Log;
import androidx.annotation.LongDef;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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
Урок 9. Retrofit 2. Retrolambda.

Подключение
// retrofit
compile 'com.squareup.retrofit2:retrofit:2.2.0'
compile 'com.squareup.retrofit2:converter-gson:2.2.0'
compile 'com.squareup.retrofit2:adapter-rxjava:2.2.0'

// rxjava
compile 'io.reactivex:rxjava:1.2.10'
compile 'io.reactivex:rxandroid:1.2.1'

Первая строка - непосредственно Retrofit.
Вторая - способность Retrofit конвертировать json в объекты.
Третья - способность Retrofit работать с RxJava.
Четвертая и пятая строки - это RxJava
 */

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "=###";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://rawgit.com/startandroid/data/master/messages/")
                .build();

        WebApi webApi = retrofit.create(WebApi.class);

        Call<List<Message>> call = webApi.messages(2);

        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if (response.isSuccessful()){
                    assert response.body()!=null;
                    Log.d(TAG, "onResponse, message count: " + response.body().size());
                }else{
                    Log.d(TAG, "onResponse error: "+ response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.d(TAG, "onFailure: "+ t.getMessage());
            }
        });
    }
}
/*
Синхронный запуск
Если вы не в UI-потоке и вам надо выполнить запрос синхронно,
то вместо enqueue необходимо вызывать метод execute
Этот вызов блокирует ваш текущий поток и, в итоге, вернет результат или свалится с ошибкой

Response<List<Message>> response = null;
try {
   response = call.execute();
} catch (IOException e) {
   e.printStackTrace();
}


 */