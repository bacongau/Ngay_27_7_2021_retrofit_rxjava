package com.example.ngay_27_7_2021;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.ngay_27_7_2021.api.ApiService;
import com.example.ngay_27_7_2021.model.User;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Button btn, btn2;
    ArrayList<User> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button2);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApi();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (arrayList != null) {
                    Observable<User> listObservable = getNotesObservable();

                    Observer<User> listObserver = getNotesObserver();

                    listObservable.observeOn(Schedulers.io())
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(listObserver);
                } else {
                    Toast.makeText(MainActivity.this, "Phai call API de nhan du lieu truoc", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private Observer<User> getNotesObserver() {
        return new Observer<User>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d("Observer", "onSubscribe");
            }

            @Override
            public void onNext(@NonNull User user) {
                Log.d("Observer", "onNext" + user.toString());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.e("Observer", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.d("Observer", "onComplete");
            }
        };
    }

    private Observable<User> getNotesObservable() {
        final ArrayList<User> list = arrayList;

        return Observable.create(new ObservableOnSubscribe<User>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<User> emitter) throws Throwable {
                for (User user : list) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(user);
                    }
                }

                // all user are emitted
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        });
    }

    private void callApi() {
        ApiService.apiService.layPost()
                .enqueue(new Callback<ArrayList<User>>() {
                    @Override
                    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                        Toast.makeText(MainActivity.this, "Call API thanh cong", Toast.LENGTH_SHORT).show();

                        arrayList = response.body();

//                        for (User user : arrayList) {
//                            Log.d("data",user.toString());
//                        }

                    }

                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Call API that bai", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}