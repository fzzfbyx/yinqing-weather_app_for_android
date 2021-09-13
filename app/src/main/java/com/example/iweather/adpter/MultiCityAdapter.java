package com.example.iweather.adpter;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import com.chad.library.adapter.base.BaseItemDraggableAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.iweather.Bean.NewWeatherBean;
import com.example.iweather.R;
import com.example.iweather.util.MultiWeatherBgSelector;
import com.example.iweather.util.WeatherIconSelector;
import java.util.List;
/**
 * 多城市适配器
 */
public class MultiCityAdapter extends BaseItemDraggableAdapter<NewWeatherBean, BaseViewHolder>  {
    private Context context;    //场景
    public MultiCityAdapter(int layoutResId, @Nullable List data,Context context) {
        super(layoutResId, data);
        this.context = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, NewWeatherBean item) {
        try {
            String IconName = WeatherIconSelector.WeatherIconName(item.getHeWeather6().get(0).getNow().getCond_code());
            //根据名字查找资源id
            int Icon = context.getResources().getIdentifier(IconName,"mipmap", context.getApplicationContext().getPackageName());
            helper.setImageResource(R.id.item_multi_icon,Icon);
            helper.setText(R.id.item_multi_city,item.getHeWeather6().get(0).getBasic().getLocation());
            helper.setText(R.id.item_multi_cond,item.getHeWeather6().get(0).getNow().getCond_txt());
            helper.setText(R.id.item_multi_temperature,item.getHeWeather6().get(0).getNow().getTmp()+"℃");
            String BgName = MultiWeatherBgSelector.MultiWeatherBgName(item.getHeWeather6().get(0).getNow().getCond_code());
            //根据名字查找资源id
            int Bg = context.getResources().getIdentifier(BgName,"mipmap", context.getApplicationContext().getPackageName());
            helper.setBackgroundRes(R.id.item_multi_bg,Bg);
            Log.d("cc", "convert: "+BgName+"----"+item.getHeWeather6().get(0).getNow().getCond_code());
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
