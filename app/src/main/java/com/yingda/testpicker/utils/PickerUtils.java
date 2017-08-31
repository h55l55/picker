package com.yingda.testpicker.utils;

import android.app.Activity;

import com.yingda.picker.model.PickerModel;
import com.yingda.testpicker.listener.OnGradeSelectedListener;
import com.yingda.testpicker.listener.OnTimeSelectedListener;
import com.yingda.testpicker.model.Grade;
import com.yingda.picker.picker.ThereWheelPicker;

import java.util.List;

/**
 * Created by wenyong.hu on 2017/8/30.
 */

public class PickerUtils {
    private PickerUtils() {
    }

    public static void showTimePicker(Activity activity, OnTimeSelectedListener listener) {
        PickerTimeUtils.showTimePicker(activity, listener, "2000", "1", "1");
    }

    public static void showGradePicker(Activity activity, OnGradeSelectedListener listener,
                                       Grade grade) {
        PickerGradeUtils.showGradePicker(activity, listener, grade);
    }

    static ThereWheelPicker getWheelPicker(List<PickerModel> pickerModels,
                                           String title, Activity activity) {
        ThereWheelPicker wheelPicker = new ThereWheelPicker.Builder(activity, pickerModels)
                .firstWheelCyclic(false)
                .secondWheelCyclic(false)
                .thirdWheelCyclic(false)
                .visibleItemsCount(5)
                .itemPadding(40)
                .title(title)
                .build();
        return wheelPicker;
    }

    static ThereWheelPicker getWheelPicker(List<PickerModel> pickerModels,
                                           String title, Activity activity, String firstName,
                                           String secondName, String thirdName) {
        ThereWheelPicker wheelPicker = new ThereWheelPicker.Builder(activity, pickerModels)
                .firstWheelCyclic(false)
                .secondWheelCyclic(false)
                .thirdWheelCyclic(false)//对应的滚轮是否可以循环滚动
                .visibleItemsCount(5)//默认可见的item数量
                .itemPadding(40)//每个item的间距
                .title(title)//标题
                .firstWheelData(firstName)
                .secondWheelData(secondName)
                .thirdWheelData(thirdName)//三个滚轮的默认值
                .build();
        return wheelPicker;
    }
}
