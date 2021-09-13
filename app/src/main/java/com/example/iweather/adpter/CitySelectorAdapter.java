package com.example.iweather.adpter;
import android.support.annotation.Nullable;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.iweather.R;
import java.util.List;
/**
 * 城市选择器
 */
public class CitySelectorAdapter extends BaseQuickAdapter<String,BaseViewHolder>{
    public CitySelectorAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }
    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.item_select_cityName,item);
    }
}
