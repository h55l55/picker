package com.yingda.testpicker.utils;

import android.app.Activity;

import com.yingda.picker.model.PickerModel;
import com.yingda.testpicker.listener.OnTimeSelectedListener;
import com.yingda.picker.picker.ThereWheelPicker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wenyong.hu on 2017/8/30.
 */

final class PickerTimeUtils {
    private PickerTimeUtils() {

    }

    static void showTimePicker(final Activity activity, final OnTimeSelectedListener listener,
                               String defaultYear, String defaultMonth, String defaultDay) {
        ThereWheelPicker cityPicker = PickerUtils.getWheelPicker(getTimeModelList(),
                "请选择日期", activity, defaultYear, defaultMonth, defaultDay);

        cityPicker.show();
        cityPicker.setOnWheelItemClickListener(new ThereWheelPicker.OnWheelItemClickListener() {

            @Override
            public void onSelected(PickerModel firstValue, PickerModel secondValue, PickerModel thirdValue) {
                listener.onTimeSelect(firstValue.getName(),
                        secondValue.getName(),
                        thirdValue.getName());
            }

            @Override
            public void onCancel() {
                //空方法
            }
        });

    }

    private static List<PickerModel> getTimeModelList() {
        List<PickerModel> models = new ArrayList<>();
        long nowTime = System.currentTimeMillis();
        String year = getTimeByPattern(nowTime, "yyyy");
        int nowYear = Integer.valueOf(year);
        PickerModel yearModel;
        for (int i = 1950; i <= nowYear; i++) {
            yearModel = new PickerModel();
            yearModel.setName(i + "年");
            yearModel.setModels(getMouthModel(i));
            models.add(yearModel);
        }
        return models;
    }

    public static List<PickerModel> getMouthModel(int year) {
        List<PickerModel> mouthModels = new ArrayList<>();
        PickerModel mouthModel;
        for (int i = 1; i <= 12; i++) {
            mouthModel = new PickerModel();
            mouthModel.setName(i + "月");
            mouthModel.setModels(getDayModels(year, i));
            mouthModels.add(mouthModel);
        }
        return mouthModels;
    }

    private static List<PickerModel> getDayModels(int year, int mouth) {
        List<PickerModel> dayModels = new ArrayList<>();
        PickerModel dayModel;
        int dayOfMouth = getDayOfMouth(year, mouth);
        for (int i = 1; i <= dayOfMouth; i++) {
            dayModel = new PickerModel();
            dayModel.setName(i + "日");
            dayModels.add(dayModel);
        }
        return dayModels;
    }

    private static int getDayOfMouth(int year, int mouth) {
        int days;
        switch (mouth) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                days = 31;
                break;
            case 2:
                if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                    days = 29;
                } else {
                    days = 28;
                }
                break;
            default:
                days = 30;
                break;
        }
        return days;
    }

    public static String getTimeByPattern(long timeLong, String pattern) {
        Date date = new Date(timeLong);
        SimpleDateFormat ft = new SimpleDateFormat(pattern);
        return ft.format(date);
    }
}