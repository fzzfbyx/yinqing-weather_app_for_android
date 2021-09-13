package com.example.iweather.adpter;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.iweather.Bean.MultipleItem;
import com.example.iweather.R;
import com.example.iweather.activity.Main2Activity;
import com.example.iweather.util.MySharedpreference;
import com.example.iweather.util.WeatherIconSelector;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.baidu.mapapi.BMapManager.getContext;

/**
 * 多项目快速适配器
 */
public class MultipleItemQuickAdapter extends BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with some initialization data.
     * 与QuickAdapter相同的#QuickAdapter(Context,int)，但是有一些初始化数据。
     *
     * @param data 从这个列表中创建一个新列表以避免可变列表
     */
    private String tmp1;
    private String tmp2;
    private String tmp3;
    private String tmp4;
    private String tmp5;
    String [] tmp_max = new String [7];
    String [] tmp_min = new String [7];
    private Context context;
    SharedPreferences.Editor editor;
    public MultipleItemQuickAdapter(List<MultipleItem> data, Context context) {
        super(data);
        addItemType(MultipleItem.NowWeatherView, R.layout.item_temperature);
        addItemType(MultipleItem.HoursWeatherView, R.layout.item_hour_info);
        addItemType(MultipleItem.DetailsAirInfo, R.layout.item_air_info);
        addItemType(MultipleItem.ForecastView, R.layout.item_forecast);
        addItemType(MultipleItem.SuggestionView, R.layout.item_suggestion);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, MultipleItem item) {
        if (item.IsLoadInfoSuccess) {
            switch (helper.getItemViewType()) {
                case MultipleItem.NowWeatherView:
                    try {
                        String IconName = WeatherIconSelector.WeatherIconName(item
                                .mNewWeatherBean.getHeWeather6().get(0).getNow().getCond_code());
                        //根据名字查找资源id
                        int Icon = context.getResources().getIdentifier(IconName, "mipmap",
                                context.getApplicationContext().getPackageName());
                        helper.setImageResource(R.id.T_weather_icon, Icon);
                        helper.setText(R.id.T_weather, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getNow().getCond_txt());
                        tmp1 =item.mNewWeatherBean.getHeWeather6()
                                .get(0).getNow().getTmp();
                        helper.setText(R.id.T_temperature, tmp1 + "℃");
                        tmp2 =item.mNewWeatherBean.getHeWeather6()
                                .get(0).getDaily_forecast().get(0).getTmp_max();
                        helper.setText(R.id.T_max_temperature, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getDaily_forecast().get(0).getTmp_max() + "℃");
                        tmp3 =item.mNewWeatherBean.getHeWeather6()
                                .get(0).getDaily_forecast().get(1).getTmp_min();
                        helper.setText(R.id.T_min_temperature, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getDaily_forecast().get(0).getTmp_min() + "℃");
                        helper.setText(R.id.T_pm25, "PM2.5:" + item.mAirBean.getHeWeather6()
                                .get(0).getAir_now_city().getPm25());
                        helper.setText(R.id.T_air, "空气质量:" + item.mAirBean.getHeWeather6()
                                .get(0).getAir_now_city().getQlty());
                        for (int i = 1; i < 8; i++) {
                            tmp_max[i-1]=item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getDaily_forecast().get(i-1).getTmp_max();
                            tmp_min[i-1]=item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getDaily_forecast().get(i-1).getTmp_min();
                        }
                        SharedPreferences sp = mContext.getSharedPreferences("data", MODE_PRIVATE);
//获取编辑器
                        SharedPreferences.Editor editor = sp.edit();
//存入String型数据
                        editor.putString("tmp_max0", tmp_max[0]);
                        editor.putString("tmp_max1", tmp_max[1]);
                        editor.putString("tmp_max2", tmp_max[2]);
                        editor.putString("tmp_max3", tmp_max[3]);
                        editor.putString("tmp_max4", tmp_max[4]);
                        editor.putString("tmp_max5", tmp_max[5]);
                        editor.putString("tmp_max6", tmp_max[6]);
                        editor.putString("tmp_min0", tmp_min[0]);
                        editor.putString("tmp_min1", tmp_min[1]);
                        editor.putString("tmp_min2", tmp_min[2]);
                        editor.putString("tmp_min3", tmp_min[3]);
                        editor.putString("tmp_min4", tmp_min[4]);
                        editor.putString("tmp_min5", tmp_min[5]);
                        editor.putString("tmp_min6", tmp_min[6]);

//提交修改，否则不生效
                        editor.commit();

                    } catch (Exception e1) {
                    }
                    break;
                case MultipleItem.HoursWeatherView:
                    try {
                        for (int i = 1; i < 8; i++) {
                            // 根据名称查找控件id数值
                            int one_clock = context.getResources().getIdentifier("one_clock_" + i,
                                    "id", context.getApplicationContext().getPackageName());
                            String time = item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getHourly().get(i - 1).getTime();
                            helper.setText(one_clock, time.substring(time.lastIndexOf(" "), time.length()));
                            // 根据名称查找控件id数值
                            int one_humidity = context.getResources().getIdentifier("one_humidity_" + i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setText(one_humidity, item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getHourly().get(i - 1).getHum() + "%");
                            // 根据名称查找控件id数值
                            int one_temp = context.getResources().getIdentifier("one_temp_" + i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setText(one_temp, item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getHourly().get(i - 1).getTmp() + "℃");
                            // 根据名称查找控件id数值
                            int one_wind = context.getResources().getIdentifier("one_wind_" + i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setText(one_wind, item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getHourly().get(i - 1).getWind_sc());
                        }
                    } catch (Exception e) {
                    }
                    break;
                case MultipleItem.DetailsAirInfo://空气细节信息
                    try {
                        helper.setText(R.id.air_aiq_tv, "空气指数:  " + item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getAqi());
                        helper.setText(R.id.air_qlty_tv, "空气质量:  " + item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getQlty());
                        helper.setText(R.id.air_main_pollutant_tv, "主要污染物:  " + item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getMain());
                        helper.setText(R.id.air_pm25_tv, "PM2.5指数:  " + item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getPm25());
                        helper.setText(R.id.air_no2_tv, "二氧化氮指数:  " + item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getNo2());
                        helper.setText(R.id.air_so2_tv, "二氧化硫指数:  " + item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getSo2());
                        helper.setText(R.id.air_co_tv, "一氧化碳指数:  " + item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getCo());
                        helper.setText(R.id.air_o3_tv, "臭氧指数:  " + item.mAirBean
                                .getHeWeather6().get(0).getAir_now_city().getO3());
                    } catch (Exception e) {
                    }
                    break;
                case MultipleItem.ForecastView://预测的观点
                    try {
                        for (int i = 1; i < 8; i++) {

                            String IconName_forecast = WeatherIconSelector.WeatherIconName(item.mNewWeatherBean
                                    .getHeWeather6().get(0).getDaily_forecast().get(i - 1).getCond_code_d());
                            //根据名字查找资源id
                            int Icon_forecast_id = context.getResources().getIdentifier(IconName_forecast,
                                    "mipmap", context.getApplicationContext().getPackageName());
                            //根据名称查找控件id数值
                            int forecast_icon = context.getResources().getIdentifier("forecast_icon_" + i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setImageResource(forecast_icon, Icon_forecast_id);
                            //根据名称查找控件id数值
                            int forecast_date = context.getResources().getIdentifier("forecast_date_" + i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setText(forecast_date, item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getDaily_forecast().get(i - 1).getDate());
                            //根据名称查找控件id数值
                            int forecast_temp = context.getResources().getIdentifier("forecast_temp_" + i,
                                    "id", context.getApplicationContext().getPackageName());
                            helper.setText(forecast_temp, "温度:" + item.mNewWeatherBean.getHeWeather6()
                                    .get(0).getDaily_forecast().get(i - 1).getTmp_min() + "℃" + "/" + item
                                    .mNewWeatherBean.getHeWeather6()
                                    .get(0).getDaily_forecast().get(i - 1).getTmp_max() + "℃");
                            //根据名称查找控件id数值
                            int forecast_txt = context.getResources().getIdentifier("forecast_txt_" + i,
                                    "id", context.getApplicationContext().getPackageName());
                            String txt = item.mNewWeatherBean.getHeWeather6().get(0).getDaily_forecast()
                                    .get(i - 1).getCond_txt_d() + "  最高温度" + item.mNewWeatherBean
                                    .getHeWeather6().get(0).getDaily_forecast()
                                    .get(i - 1).getTmp_max() + "℃  " + item.mNewWeatherBean
                                    .getHeWeather6().get(0).getDaily_forecast()
                                    .get(i - 1).getWind_dir() + "  风力" + item.mNewWeatherBean
                                    .getHeWeather6().get(0).getDaily_forecast()
                                    .get(i - 1).getWind_sc();
                            helper.setText(forecast_txt, txt);
                        }
                    } catch (Exception e) {
                    }
                    break;
                case MultipleItem.SuggestionView:
                    try {
                        helper.setText(R.id.comf_brief, "舒适指数---" + item.mNewWeatherBean
                                .getHeWeather6().get(0).getLifestyle().get(0).getBrf());
                        helper.setText(R.id.comf_txt, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getLifestyle().get(0).getTxt());
                        helper.setText(R.id.cloth_brief, "穿衣指数---" + item.mNewWeatherBean
                                .getHeWeather6().get(0).getLifestyle().get(1).getBrf());
                        helper.setText(R.id.cloth_txt, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getLifestyle().get(1).getTxt());
                        helper.setText(R.id.flu_brief, "感冒指数---" + item.mNewWeatherBean
                                .getHeWeather6().get(0).getLifestyle().get(2).getBrf());
                        helper.setText(R.id.flu_txt, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getLifestyle().get(2).getTxt());
                        helper.setText(R.id.sport_brief, "运动指数---" + item.mNewWeatherBean
                                .getHeWeather6().get(0).getLifestyle().get(3).getBrf());
                        helper.setText(R.id.sport_txt, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getLifestyle().get(3).getTxt());
                        helper.setText(R.id.travel_brief, "旅游指数---" + item.mNewWeatherBean
                                .getHeWeather6().get(0).getLifestyle().get(4).getBrf());
                        helper.setText(R.id.travel_txt, item.mNewWeatherBean.getHeWeather6()
                                .get(0).getLifestyle().get(4).getTxt());
                    } catch (Exception e) {
                    }
                    break;
            }
        }
    }
}

