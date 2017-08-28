package com.yingda.picker.picker;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yingda.picker.R;
import com.yingda.picker.model.PickerModel;
import com.yingda.picker.wheel.adapters.AbstractWheelAdapter;

import java.util.List;

/**
 * Created by wenyong.hu on 2017/8/3.
 */

public class WheelPickerAdapter extends AbstractWheelAdapter {
    private List<PickerModel> pickerModels;
    private Context context;

    public WheelPickerAdapter(List<PickerModel> pickerModels, Context context) {
        this.pickerModels = pickerModels;
        this.context = context;
    }

    @Override
    public int getItemsCount() {
        return pickerModels.size();
    }

    @Override
    public View getEmptyItem(View convertView, ViewGroup parent) {
        return super.getEmptyItem(convertView, parent);
    }

    @Override
    public View getItem(int index, View convertView, ViewGroup parent, int currentPosition) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_wheel_picker, null, false);
            holder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.text.setText(pickerModels.get(index).getName());
        holder.text.setTextColor(getTextColor(index, currentPosition));
        return convertView;
    }

    /**
     * 设置颜色渐变效果
     */
    private int getTextColor(int index, int currentPosition) {
        int alpha;
        int num;
        num = Math.abs(index - currentPosition);
        if (index == currentPosition) {
            alpha = 255;
        } else {
            alpha = (int) (255 * Math.pow(0.5, num));
        }
        int color = Color.argb(alpha, 58, 58, 58);
        return color;
    }

    private class ViewHolder {
        private TextView text;
    }
}
