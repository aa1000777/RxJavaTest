package com.example.cjc.myapplication.test2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cjc.myapplication.R;
import com.example.cjc.myapplication.test2.okhttp.Android;
import com.example.cjc.myapplication.test2.okhttp.ApiConstants;
import com.example.cjc.myapplication.test2.okhttp.ApiDataRepository;
import com.example.cjc.myapplication.test2.okhttp.GetAndroid;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Main2Activity extends AppCompatActivity {
    ListView listView;
    List<Android.ResultsBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = (ListView) findViewById(R.id.listview);
//        initData1();
        ApiDataRepository.getAndroid(this, "/7/22")
                .subscribe(android -> upList(android));
    }

    private void upList(Android android) {
        list = android.getResults();
        listView.setAdapter(new AndroidAdapter());
    }

    private void initData1() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        GetAndroid getcity = retrofit.create(GetAndroid.class);

        Observable<Android> cityCall = getcity.getAndroid("/7/22");

        cityCall.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Android>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Android android) {
                        list = android.getResults();
                        listView.setAdapter(new AndroidAdapter());
                    }
                });
    }

    class AndroidAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item, null);
            TextView textView = (TextView) view.findViewById(R.id.tv);
            textView.setText(list.get(position).getDesc());
            return view;
        }
    }
}
