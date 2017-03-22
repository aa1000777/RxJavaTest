package com.example.cjc.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cjc.myapplication.test2.Main2Activity;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tv;
    List<String> list;
    String[] strs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tv);
        findViewById(R.id.btn_submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
            }
        });
        list = Arrays.asList("Java", "Android", "Ios", "Python");
        strs = new String[]{"Java", "Android", "Ios", "Python"};
        //create 方法运用
        rxTest1();
        //create + Action1 运用
        rxTest2();
        //just + Action1
        rxTest3();
        //lambda + just
        rxTest4();
        //lambda + just + map
        rxTest5();
        //lambda + just + map
        rxTest6();
        //lambda + create + map
        rxTest7();
        //lambda + just + map + map
        rxTest8();
        //lambda + just + map + map
        rxTest9();
        //lambda + just + map + map
        rxTest10();
        //from + Action1
        rxTest11();
        //lambda + from
        rxTest12();
        //lambda + from
        rxTest13();
        //just + flatMap + take + doOnNext
        rxTest14();
        //lambda + just + flatMap + take + doOnNext
        rxTest15();
        rxTest16();

    }


///////////////////////////////////////////////////////////////////////////
// lambda   Lambda 表达式
// create   创建Observable对象
// just     内部创建了Observable对象
// from     迭代器  内部创建了Observable对象 传入数组、集合 （相当于for）
// Map      一般用于对原始的参数进行加工处理，返回值还是基本的类型，可以在subscribe中使用(适用)的类型。
// flatMap  一般用于输出一个Observable，而其随后的subscribe中的参数也跟Observable中的参数一样，
//          注意不是Observable，一般用于对原始数据返回一个Observable,这个Observable中数据类型可以是原来的，也可以是其他的
// take     执行次数限制
// doOnNext 插入其它操作
///////////////////////////////////////////////////////////////////////////

    private void rxTest16() {
        Observable.just(list)
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, "call: " + s);
                    }
                });
    }

    /**
     * lambda + just + flatMap + take + doOnNext
     */
    private void rxTest15() {
        Observable.just(list)
                .flatMap(s -> Observable.from(s))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .take(3)
                .doOnNext(s -> Log.i(TAG, "doOnNext: " + s))
                .subscribe(s -> Log.i(TAG, "subscribe: " + s));
    }

    /**
     * just + flatMap + take + doOnNext
     */
    private void rxTest14() {
        Observable.just(list)
                .flatMap(new Func1<List<String>, Observable<String>>() {
                    @Override
                    public Observable<String> call(List<String> strings) {
                        return Observable.from(strings);
                    }
                })
                .take(3)
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("doonnext:" + s);
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        System.out.println("filter:" + s);
                    }
                });
    }

    /**
     * lambda + from
     */
    private void rxTest13() {
        Observable.from(strs)
                .subscribe(s -> Log.i(TAG, "数组：" + s));
    }

    /**
     * lambda + from
     */
    private void rxTest12() {
        Observable.from(list)
                .subscribe(s -> Log.i(TAG, "List：" + s));
    }

    /**
     * from + Action1
     */
    private void rxTest11() {
        Observable.from(list).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "List: " + s);
            }
        });
    }

    /**
     * lambda + just + map + map
     */
    private void rxTest10() {
        Observable.just("hello")
                .map(s -> 2012)
                .map(s -> String.valueOf(s))
                .subscribe(s -> tv.setText(s));
    }

    /**
     * lambda + just + map + map
     */
    private void rxTest9() {
        Observable.just("hello")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return 2011;
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return String.valueOf(integer);
                    }
                })
                .subscribe(s -> tv.setText(s));
    }

    /**
     * lambda + just + map + map
     */
    private void rxTest8() {
        Observable.just("hello 8888")
                .map(s -> s + "这是要加的数据")
                .map(s -> s + "我还要加数据")
                .subscribe(s -> tv.setText(s));
    }

    /**
     * lambda + create + map
     */
    private void rxTest7() {
        Observable.create(subscriber -> subscriber.onNext("hello 11111"))
                .map(s -> s + "这是我要加的数据")
                .subscribe(s -> tv.setText(s.toString()));
    }

    /**
     * lambda + just + map
     */
    private void rxTest6() {
        Observable.just("hello")
                .map(s1 -> s1.hashCode())
                .subscribe(s -> tv.setText(s.toString()));
    }

    /**
     * lambda + just + map
     */
    private void rxTest5() {
        Observable.just("hello 5555")
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        return s + " 再加数据：555";
                    }
                })
                .subscribe(s -> tv.setText(s));
    }

    /**
     * lambda + just
     */
    private void rxTest4() {
        Observable.just("hello 44444")
                .subscribe(s -> tv.setText(s));
    }

    /**
     * just + Action1
     */
    private void rxTest3() {
        Observable.just("Hello 3333")
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        tv.setText(s);
                    }
                });
    }

    /**
     * create + Action1 运用
     */
    private void rxTest2() {
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello 2222");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                tv.setText(s);
            }
        });
    }

    /**
     * create 方法运用
     */
    private void rxTest1() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("hello 11111");
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(String s) {
                tv.setText(s);
            }
        });
    }


}
