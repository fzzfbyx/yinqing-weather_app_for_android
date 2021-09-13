package com.example.iweather.activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.iweather.Bean.NewWeatherBean;
import com.example.iweather.R;
import com.example.iweather.net.CallBack.IError;
import com.example.iweather.net.CallBack.ISuccess;
import com.example.iweather.net.RestClient;
import com.example.iweather.util.MySharedpreference;
import com.example.iweather.widget.PinChart;
import com.example.iweather.widget.WeatherItem;
import com.example.iweather.widget.WeatnerChartView;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.iweather.Bean.MultipleItem;
import com.example.iweather.R;
import com.example.iweather.util.WeatherIconSelector;
import java.util.List;
import com.example.iweather.Bean.NewWeatherBean;
import com.google.gson.Gson;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import java.util.ArrayList;

import static com.baidu.mapapi.BMapManager.getContext;


/****
 * 饼状图和天气折线图
 */
public class Main2Activity extends AppCompatActivity {
    private SharedPreferences.Editor  editor;       //共享参数
    private WeatnerChartView chart1;
    private WeatnerChartView chart2;
    private PinChart pinChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }
    private void initView()
    {
        chart1= findViewById(R.id.weather_char1);
        chart2=  findViewById(R.id.weather_char2);
        pinChart= findViewById(R.id.pinchart);
        initData();
    }


    private void initData()
    {
        editor = MySharedpreference.getInstance(Main2Activity.this);
        ArrayList<WeatherItem> list= new ArrayList<WeatherItem>();
        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        list.add(new WeatherItem("1", Float.parseFloat(sp.getString("tmp_max0", ""))));
        list.add(new WeatherItem("2",Float.parseFloat(sp.getString("tmp_max1", ""))));
        list.add(new WeatherItem("3", Float.parseFloat(sp.getString("tmp_max2", ""))));
        list.add(new WeatherItem("4",Float.parseFloat(sp.getString("tmp_max3", ""))));
        list.add(new WeatherItem("5",Float.parseFloat(sp.getString("tmp_max4", ""))));
        list.add(new WeatherItem("6",Float.parseFloat(sp.getString("tmp_max5", ""))));
        list.add(new WeatherItem("7", Float.parseFloat(sp.getString("tmp_max6", ""))));
        chart1.SetTuView(list, "最高温度：");//单位: 摄氏度
    chart1.invalidate();
        ArrayList<WeatherItem> list1= new ArrayList<WeatherItem>();
        list1.add(new WeatherItem("",Float.parseFloat(sp.getString("tmp_min0", ""))));
        list1.add(new WeatherItem("",Float.parseFloat(sp.getString("tmp_min1", ""))));
        list1.add(new WeatherItem("", Float.parseFloat(sp.getString("tmp_min2", ""))));
        list1.add(new WeatherItem("",Float.parseFloat(sp.getString("tmp_min3", ""))));
        list1.add(new WeatherItem("", Float.parseFloat(sp.getString("tmp_min4", ""))));
        list1.add(new WeatherItem("",Float.parseFloat(sp.getString("tmp_min5", ""))));
        list1.add(new WeatherItem("",Float.parseFloat(sp.getString("tmp_min6", ""))));
       chart2.SetTuView(list1, "最低温度：");
      chart2.invalidate();
    }

}
